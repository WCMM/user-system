package com.example.demo.controller;

import com.example.demo.utils.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangnan
 * @Date 2020/9/9/009 2020-09 20:33
 * @Param []
 * @return
 **/
@Slf4j
@RestController
public class LoginController {

    @GetMapping(value = "/loginUser")
    public Map<String,Object> getLogin(String loginName, String password, HttpServletRequest request){

        System.out.println("调用了login方法");

        String zhangxing = Token.genetateToken();

        HttpSession session = request.getSession();
        session.setAttribute("SESSION_TOKEN",zhangxing);

        boolean login = false;
        //储存token
        String pwd = "123456";
        Map<String,Object> map = new HashMap<String, Object>();
        if(pwd.equals(password)){
            login = true;
        }
        map.put("login",login);
        map.put("token",zhangxing);
        return map;
    }
}
