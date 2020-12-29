package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wangnan
 * @Date 2019/4/2/002 2019-04 09:35
 * @Param []
 * @return
 **/
@RestController
@RequestMapping(value = "export")
public class ExportController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "userList", method = RequestMethod.GET)
    public Result userList(){
        return userService.exportUserList();
    }

}
