package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author wangnan
 * @Date 2020/9/9/009 2020-09 20:42
 * @Param []
 * @return
 **/
@Controller
@CrossOrigin
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "hello";
    }

    @RequestMapping("/success")
    public String success(HttpServletRequest request) {

        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("SESSION_TOKEN"));

        return "factManage";
    }

}