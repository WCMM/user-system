package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author wangnan
 * @Date 2019/3/19/019 2019-03 14:10
 * @Param []
 * @return
 **/
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result addUser(@Valid User user) {
        return userService.addUser(user);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result findAllUser(Integer pageNum, Integer pageSize) {
        return userService.findAllUser(pageNum, pageSize);
    }

    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public Result getById(Integer id) {
        return userService.getById(id);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody User user) {
        return userService.update(user);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Result delete(Integer userId) {
        return userService.delete(userId);
    }

    @RequestMapping(value = "error", method = RequestMethod.GET)
    public Result error() {
        return userService.error();
    }





}