package com.gain.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gain.result.Result;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void renderError(HttpServletResponse response, String message) {
        try {
            // 虽然是业务错误，但在拦截器层面，我们依然建议把 HTTP 状态码设为 401
            // 这样前端的 uni.request 才能最快感知到
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");

            // 使用你自己的 Result 类
            Result<Object> result = Result.error(0,message);
            // 明确告知前端：这是身份问题
            response.getWriter().write(objectMapper.writeValueAsString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
