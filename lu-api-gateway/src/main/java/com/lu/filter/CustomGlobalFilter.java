package com.lu.filter;

import com.lu.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: 鹿又笑
 * @Create: 2024/7/2 16:28
 * @description:
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    // ip白名单
    public static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1 用户发送请求到 API 网关
        // 2 请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识：{}", request.getId());
        log.info("请求路径：{}", request.getPath());
        log.info("请求路径：{}", request.getPath().value());
        log.info("请求方法：{}", request.getMethod());
        log.info("请求参数：{}", request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址：{}", sourceAddress);
        log.info("请求来源地址：{}", request.getRemoteAddress());
        // 拿到响应对象
        ServerHttpResponse response = exchange.getResponse();
        // 3（黑白名单）
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            return handleNoAuth(response);
        }
        // 4 用户鉴权（判断 ak、sk 是否合法）
        // 从请求头中获取参数
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String body = headers.getFirst("body");
        String sign = headers.getFirst("sign");
        // TODO 实际情况是要去数据库中查是否分配给用户
        if (!"1f211489e0afa33bd33d1cf2958ccb89".equals(accessKey)) {
            return handleNoAuth(response);
        }
        // 直接校验如果随机数大于10000
        if (Long.parseLong(nonce) > 10000) {
            return handleNoAuth(response);
        }
        // 时间和当前时间不能超过5分钟
        Long currentTime = System.currentTimeMillis() / 1000;
        final Long FIVE_MINUTES = 60 * 5L;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            // 如果时间戳与当前时间相差五分钟或以上，调用handleNoAuth(response)方法进行处理
            return handleNoAuth(response);
        }
        // TODO 要从数据库中根据ak查找sk
        String serverSign = SignUtils.genSign(body, "10cfbfe19d338711167114c2dbcae0fa");
        if (!sign.equals(serverSign)) {
            throw new RuntimeException("无权限");
        }
        // 5 请求的模拟接口是否存在？
        // TODO 要从数据库中查找模拟接口是否存在

        // 6 请求转发，调用模拟接口
//        Mono<Void> filter = chain.filter(exchange);
        // 7 响应日志
        log.info("响应码：{}", response.getStatusCode());
        return handleResponse(exchange, chain);
    }

    // 自定义响应处理
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            // 获取原始的响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取相应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();
            // 判断状态码是否为200 todo 按道理说现在还没调用是拿不到响应码的。。。
            if(statusCode == HttpStatus.OK){
                // 创建一个装饰后的响应对象
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        //log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 返回一个处理后的响应体
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 8 TODO 调用成功，接口调用次数 + 1

                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                // 响应结果
                                log.info("响应结果：{}", data);
                                // 将处理后的内容重新包装成DataBuffer并返回
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 对于 200 的请求，将装饰后的响应对象传递给下一个过滤器链，并继续处理
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 对于非 200 的请求直接返回
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }

    // 抛出403
    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
