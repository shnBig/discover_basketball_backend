package com.gain.interceptor;

import com.gain.annotation.RequirePermission;
import com.gain.context.BaseContext;
import com.gain.domain.entity.Menu;
import com.gain.exception.UserNotLoginException;
import com.gain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);
        if (requirePermission == null) {
            requirePermission = handlerMethod.getBeanType().getAnnotation(RequirePermission.class);
        }

        if (requirePermission != null) {
            Long userId = BaseContext.getCurrentId();
            if (userId == null) {
                throw new UserNotLoginException("User not logged in");
            }

            String permission = requirePermission.value();
            if (!userService.hasPermission(userId, permission)) {
                throw new UserNotLoginException("No permission: " + permission);
            }
        }

        return true;
    }
}
