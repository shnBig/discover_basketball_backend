package com.gain.service.Impl;

import com.gain.constant.JwtClaimsConstant;
import com.gain.domain.dto.UserLoginDTO;
import com.gain.domain.entity.Menu;
import com.gain.domain.entity.Role;
import com.gain.domain.entity.User;
import com.gain.domain.vo.UserVO;
import com.gain.exception.AccountNotFoundException;
import com.gain.exception.LoginFailedException;
import com.gain.mapper.UserMapper;
import com.gain.mapper.UserRoleMapper;
import com.gain.mapper.RoleMapper;
import com.gain.mapper.MenuMapper;
import com.gain.result.Result;
import com.gain.service.UserService;
import com.gain.utils.JwtUtil;
import com.gain.utils.PasswordEncoder;
import com.gain.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {



    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserVO login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String rawPassword = userLoginDTO.getPassword();

        User user = userMapper.getByUsername(username);
        if (user == null) {
            throw new AccountNotFoundException("账户未找到: " + username);
        }

        // 使用盐值验证密码
        if (!passwordEncoder.matches(rawPassword, user.getSalt(), user.getPassword())) {
            throw new LoginFailedException("密码错误");
        }

        if (user.getStatus() == 0) {
            throw new LoginFailedException("账户被锁定");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        claims.put(JwtClaimsConstant.USERNAME, user.getUsername());
        String token = jwtUtil.createUserToken(claims);

        userMapper.updateLoginInfo(user.getId(), token);

        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setNickname(user.getNickname());
        userVO.setAvatarUrl(user.getAvatarUrl());
        userVO.setToken(token);

        List<Role> roles = getUserRoles(user.getId());
        userVO.setRoles(roles.stream().map(Role::getRoleCode).collect(Collectors.toList()));
        // 获取用户权限菜单
        List<Menu> menus = getUserMenus(user.getId());
        userVO.setPermissions(
                menus.stream()
                        .map(Menu::getPerms)
                        .filter(perms -> perms != null && !perms.isEmpty())
                        .distinct()
                        .collect(Collectors.toList())
        );

        return userVO;
    }


    @Override
    public List<Role> getUserRoles(Long userId) {
        return roleMapper.selectByUserId(userId);
    }

    @Override
    public List<Menu> getUserMenus(Long userId) {
        return menuMapper.selectByUserId(userId);
    }

    @Override
    public boolean hasPermission(Long userId, String permission) {
        List<Menu> menus = getUserMenus(userId);
        return menus.stream()
                .anyMatch(menu -> menu.getPerms() != null && menu.getPerms().contains(permission));
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        User user = userMapper.getById(userId);
        if (user == null) {
            throw new AccountNotFoundException("账户未找到");
        }
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setNickname(user.getNickname());
        userVO.setAvatarUrl(user.getAvatarUrl());
        List<Role> roles = getUserRoles(user.getId());
        userVO.setRoles(roles.stream().map(Role::getRoleCode).collect(Collectors.toList()));
        // 获取用户权限菜单
        List<Menu> menus = getUserMenus(user.getId());
        userVO.setPermissions(
                menus.stream()
                        .map(Menu::getPerms)
                        .filter(perms -> perms != null && !perms.isEmpty())
                        .distinct()
                        .collect(Collectors.toList())
        );
        return userVO;
    }
}
