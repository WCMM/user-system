package com.example.demo.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.demo.dto.Constants.Constants;
import com.example.demo.dto.Emum.ComEnum;
import com.example.demo.dto.OrderDto;
import com.example.demo.entity.User;
import com.example.demo.exception.MyRuntimeException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.excel.ExcelUtils;
import com.example.demo.utils.result.Result;
import com.example.demo.utils.result.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author wangnan
 * @Date 2019/3/19/019 2019-03 14:10
 * @Param []
 * @return
 **/
@Service
public class UserServiceImpl implements UserService {

    public List<List<OrderDto>> groupList = new ArrayList<>();

    //日志声明
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("${template.user_info_template}")
    private String userInfoTemplate;

    @Override
    public Result addUser(User user) {
        for (int i = 0; i < 1000; i++) {
            user.setUserName("用户" + i);
            userMapper.insertSelective(user);
        }
        return ResultUtil.success();
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    @Override
    public Result findAllUser(Integer pageNum, Integer pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectAllUser();
        PageInfo<User> pageInfo = new PageInfo<User>(userList);
        return ResultUtil.success(pageInfo);
    }

    @Override
    public Result getById(Integer id) {
        String key = Constants.USER + id;
        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) { // 从缓存中取
            User user = redisTemplate.opsForValue().get(key);
            log.info("从缓存中获取了用户！");
            return ResultUtil.success(user);
        }
        // 从数据库取，并存回缓存
        User user = userMapper.selectByPrimaryKey(id);
        // 放入缓存，并设置缓存时间
        redisTemplate.opsForValue().set(key, user, 600, TimeUnit.SECONDS);
        return ResultUtil.success(user);
    }

    @Override
    public Result update(User user) {
        log.info("更新用户start...");
        int result = userMapper.updateByPrimaryKey(user);
        int userId = user.getUserId();
        // 缓存存在，删除缓存
        String key = Constants.USER + userId;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);
            log.info("更新用户时候，从缓存中删除用户 >> " + userId);
        }
        return ResultUtil.success(result);
    }

    @Override
    public Result delete(Integer userId) {
        log.info("删除用户start...");
        int result = userMapper.deleteByPrimaryKey(userId);

        // 缓存存在，删除缓存
        String key = "user" + userId;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);
            log.info("删除用户时候，从缓存中删除用户 >> " + userId);
        }
        return ResultUtil.success(result);
    }

    @Override
    public Result exportUserList() {

        List<User> userList = userMapper.selectAllUser();

        String fileName = ComEnum.REPORT_TYPE.COURT_REPROT.getValue();
        try {
            ExcelUtils.userInfoExport(httpServletResponse, fileName, userList, userInfoTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    @Override
    public Result error() {
        String password = SecureUtil.md5("123456");
        String uuid = IdUtil.randomUUID();
        System.out.println(password);
        System.out.println(uuid);
        throw new MyRuntimeException("抛了一个小异常");
    }


    public List<List<OrderDto>> getGroupList(List<OrderDto> list) {

        //应完成时间集合
        List<Date> list1 = new ArrayList<>();
        for (OrderDto orderDto : list) {
            list1.add(orderDto.getDeliveryDate());
        }

        //取出最小的应完成时间
        Date minDate = Collections.min(list1);//获取最小值

        //符合规则的集合
        List<OrderDto> addList = new ArrayList<>();
        //不符合规则的集合
        List<OrderDto> emainingList = new ArrayList<>();

        //迭代
        Iterator<OrderDto> iter = list.iterator();
        Date endDate = addDate(minDate, 7L);
        while (iter.hasNext()) {
            OrderDto dto = iter.next();
            if (dto.getDeliveryDate().compareTo(endDate) < 0) {
                addList.add(dto);
                iter.remove();
            } else {
                emainingList.add(dto);
            }
        }

        groupList.add(addList);

        //递归
        if (emainingList.size() > 0) {
            getGroupList(emainingList);
        }
        return groupList;
    }

    public static Date addDate(Date date, long day) {
        // 得到指定日期的毫秒数
        long time = date.getTime();

        // 要加上的天数转换成毫秒数
        day = day * 24 * 60 * 60 * 1000;

        // 相加得到新的毫秒数
        time += day;

        // 将毫秒数转换成日期
        return new Date(time);
    }
}
