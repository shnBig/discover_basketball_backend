package com.gain.interceptor;

import com.gain.constant.JwtClaimsConstant;
import com.gain.context.BaseContext;
import com.gain.domain.configPo.JwtProperties;
import com.gain.utils.JwtUtil;
import com.gain.utils.ResponseUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;
    private final JwtUtil jwtUtil;

    /**
     * 校验 jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("=== 拦截到请求：{} ===", requestURI);
        // 【核心】如果是跨域预检请求，直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        // 【核心】手动放行登录和注册接口
//        if ("/user/login".equals(requestURI) || "/user/register".equals(requestURI)
//                || "/user/user/login".equals(requestURI)) {
//            log.info("登录/注册接口，直接放行");
//            return true;
//        }

        //判断当前拦截到的是 Controller 的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String authHeader = request.getHeader(jwtProperties.getUserTokenName());
        String token = (authHeader != null && authHeader.startsWith("Bearer "))
                ? authHeader.substring(7) : null;
        System.out.printf("jwt:", token);
        //2、校验令牌
        try {
            log.info("jwt 校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            //未抛出异常就是校验成功
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("当前用户 id：", userId);
            //将用户的 id 存储到线程中，方便程序在任何时候调用用户 id
            BaseContext.setCurrentId(userId);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            log.warn("JWT 校验失败原因：{}", ex.getMessage());

            // 【核心改变】：使用工具类统一返回
            ResponseUtils.renderError(response, "登录已失效，请重新登录");

            return false; // 拦截请求
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        // 请求处理完毕，清理线程变量
        BaseContext.removeCurrentId();
        log.info("已清理线程变量，防止内存泄漏");
    }

}
