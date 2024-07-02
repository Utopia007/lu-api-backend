package com.lu.project.controller;

import com.lu.model.User;
import com.lu.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 鹿又笑
 * @Create: 2024/6/26 9:39
 * @description:
 */

@RestController
@RequestMapping("/")
public class NameController {

    @GetMapping("/name")
    public String getNameByGet(String name, HttpServletRequest request) {
        System.out.println("request = " + request.getHeader("lu"));
        return "GET 你的名字是" + name;
    }

    @PostMapping("/name")
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是" + name;
    }

    @PostMapping("/name/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        // 如果权限校验通过，返回 "POST 用户名字是" + 用户名
        return "POST 用户名字是" + user.getUsername();
    }
}
