package com.yupi.project.controller;

import com.yupi.project.model.User;
import com.yupi.project.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 鹿又笑
 * @Create: 2024/6/26 9:39
 * @description:
 */

@RestController
@RequestMapping("name")
public class NameController {

    @GetMapping("/")
    public String getNameByGet(String name) {
        return "GET 你的名字是" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是" + name;
    }

    @PostMapping("/user/")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        // 从请求头中获取名为 "accessKey" 的值
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String body = request.getHeader("body");
        String sign = request.getHeader("sign");
        // TODO 实际情况是要去数据库中查是否分配给用户
        if (!"lu".equals(accessKey)) {
            throw new RuntimeException("无权限");
        }
        // 直接校验如果随机数大于10000
        if (nonce.length() > 10000) {
            throw new RuntimeException("无权限");
        }
        // TODO 时间和当前时间不能超过5分钟

        // TODO 要从数据库中根据ak查找sk
        String serverSign = SignUtils.genSign(body, "youxiao");
        if (!sign.equals(serverSign)) {
            throw new RuntimeException("无权限");
        }

        // 如果权限校验通过，返回 "POST 用户名字是" + 用户名
        return "POST 用户名字是" + user.getUsername();
    }
}
