package com.example.demo.service.impl;

import com.example.demo.dto.OrderDto;
import com.example.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangnan
 * @Description TODO
 * @Date 2020/4/1/00116:40
 * @Param
 * @return
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Test
    public void error() {
        userService.error();
    }

    @Test
    public void groupTest() throws ParseException {
        List<OrderDto> dtoList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        OrderDto o1 = new OrderDto();
        o1.setDeliveryDate(sdf.parse("2020-01-01"));
        dtoList.add(o1);

        OrderDto o2 = new OrderDto();
        o2.setDeliveryDate(sdf.parse("2020-01-02"));
        dtoList.add(o2);

        OrderDto o3 = new OrderDto();
        o3.setDeliveryDate(sdf.parse("2020-01-03"));
        dtoList.add(o3);

        OrderDto o4 = new OrderDto();
        o4.setDeliveryDate(sdf.parse("2020-01-09"));
        dtoList.add(o4);

        OrderDto o5 = new OrderDto();
        o5.setDeliveryDate(sdf.parse("2020-01-15"));
        dtoList.add(o5);

        OrderDto o6 = new OrderDto();
        o6.setDeliveryDate(sdf.parse("2020-01-19"));
        dtoList.add(o6);

        OrderDto o7 = new OrderDto();
        o7.setDeliveryDate(sdf.parse("2020-01-20"));
        dtoList.add(o7);

        List<List<OrderDto>> lists = userServiceImpl.getGroupList(dtoList);
        System.out.println(lists.toString());
    }


}