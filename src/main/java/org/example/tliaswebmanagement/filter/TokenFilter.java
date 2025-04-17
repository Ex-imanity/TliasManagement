package org.example.tliaswebmanagement.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.tliaswebmanagement.utils.CurrentHolder;
import org.example.tliaswebmanagement.utils.JwtUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(filterName = "tokenFilter", urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1.获取请求路径
        String requestURI = request.getRequestURI();
        // 2.判断请求路径是否是登录请求
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行");
            filterChain.doFilter(request, response);
            return;
        }
        // 3.获取请求头中的token
        String token = request.getHeader("token");
        // 5.如果token为空，未登录，返回错误信息
        if (token == null || token.isEmpty()) {
            log.info("令牌为空，请先登录");
            response.setStatus(401);
            return;
        }
        // 6.如果token不为空，则判断token是否正确，如果正确，则放行；如果错误，返回错误信息
        try {
            Claims claims = JwtUtils.parseJWT(token);
            log.info("令牌校验通过，放行");
            // 将当前登录员工id存入ThreadLocal
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);
            log.info("当前登录员工id：{}", empId);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.info("令牌校验不通过，请重新登录");
            response.setStatus(401);
            return;
        }
        // 7.删除ThreadLocal中的数据
        CurrentHolder.remove();
    }
}
