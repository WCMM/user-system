package com.example.demo.service;/**
 * @Author wangnan
 * @Description TODO
 * @Date 2019/3/19/01914:10
 * @Param
 * @return
 **/

import com.example.demo.entity.User;
import com.example.demo.utils.result.Result;

/**
 * @Author wangnan
 * @Date 2019/3/19/019 2019-03-19 14:10
 * @Param []
 * @return
 **/
public interface UserService {

    Result addUser(User user);

    Result findAllUser(Integer pageNum, Integer pageSize);

    Result getById(Integer id);

    Result update(User user);

    Result delete(Integer userId);

    Result exportUserList();

    Result error();
}
