package com.lu;

import com.lu.client.LuApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 鹿又笑
 * @Create: 2024/6/26 17:47
 * @description:
 */
// 标记为配置类
@Configuration
// 能够读取application.yml的配置，读取到配置后，把这个读到的配置设置到我们这里的属性中。这里是给所有配置加上前缀为"lu.api.client"
@ConfigurationProperties("lu.api.client")
@Data
@ComponentScan
public class LuApiClientConfig {

    public String accessKey;

    public String secretKey;

    @Bean
    public LuApiClient luApiClient() {
        return new LuApiClient(accessKey, secretKey);
    }

}
