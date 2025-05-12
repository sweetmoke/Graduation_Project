package com.example.springboot.common.config;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springboot.common.ResultCode;
import com.example.springboot.entity.Account;
import com.example.springboot.exception.CustomException;
import com.example.springboot.service.AdminService;
import com.example.springboot.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//jwt拦截器
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 从http请求头中获取 token
        String token = request.getHeader("token");

        // 如果头部没有，再尝试从参数中获取
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }

        // 2. 校验 token 是否为空，执行校验
        if (StrUtil.isBlank(token)) {
            throw new CustomException(ResultCode.TOKEN_INVALID_ERROR);
        }

        Account user = null;

        try {
            // 解析 token 获取用户 ID 和角色（格式：id-role）
            String userRole = JWT.decode(token).getAudience().get(0);
            String userId = userRole.split("-")[0];
            String role = userRole.split("-")[1];

            // 根据角色从对应的表中查询用户,根据token中的userid查询数据库
            if ("1".equals(role)) {
                user = adminService.findById(Integer.valueOf(userId));
            }
            if ("2".equals(role)) {
                user = userService.findById(Integer.valueOf(userId));
            }

            // 5. 验证 token（使用用户密码作为密钥）
//            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
//            jwtVerifier.verify(token);

        } catch (Exception e) {
            log.error("JWT 验证失败：{}", e.getMessage());
            throw new CustomException(ResultCode.TOKEN_CHECK_ERROR);
        }
        if (user == null) {
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        try {
            // 使用用户密码加签验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            jwtVerifier.verify(token); // 验证 token
        } catch (JWTVerificationException e) {
            throw new CustomException(ResultCode.TOKEN_CHECK_ERROR); // 抛出自定义异常
        }


        return true; // 验证成功，放行
    }
}
