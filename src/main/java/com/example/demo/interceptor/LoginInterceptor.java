package com.example.demo.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author wangnan
 * @Date 2020/9/9/009 2020-09 20:39
 * @Param []
 * @return
 **/
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userToken = (String) request.getSession().getAttribute("SESSION_TOKEN");

        // 初始化拦截器，设置不拦截路径
        String noMatchPath = ".*/(login).*";
        String path = request.getRequestURI();

        System.out.println("资源请求路径：" + path);
        if (path.matches(noMatchPath)) {
            // 授权路径，不拦截
            return true;
        } else if (null == userToken || "".equals(userToken)) {
            // 找不到用户Token，重定位到登录
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        } else {
            // 设置扩展
            return true;
        }
    }

}