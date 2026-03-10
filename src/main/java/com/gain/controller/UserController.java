package com.gain.controller;

import com.gain.constant.JwtClaimsConstant;
import com.gain.constant.MessageConstant;
import com.gain.context.BaseContext;
import com.gain.domain.configPo.JwtProperties;
import com.gain.domain.dto.UserLoginDTO;
import com.gain.domain.dto.UserRegisterDTO;
import com.gain.domain.entity.User;
import com.gain.domain.vo.UserVO;
import com.gain.mapper.UserMapper;
import com.gain.result.Result;
import com.gain.service.UserService;
import com.gain.utils.JwtUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gain.utils.PasswordEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@Builder
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserLoginDTO userLoginDTO) {

        log.info("用户登录：{}", userLoginDTO);
        UserVO userVO = userService.login(userLoginDTO);
        log.info("用户登录成功：{}", userVO);
        return Result.success(userVO, MessageConstant.LOGIN_SUCCESS);
    }
    /**
     * 用户注册
     * @param registerDTO
     * @return
     */
    @PostMapping("/register")
    public Result<UserVO> register(@RequestBody UserRegisterDTO registerDTO) {
        log.info("用户注册：{}", registerDTO);

        // 生成随机盐值
        String salt = passwordEncoder.generateSalt();

        // 加密密码
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword(), salt);

        // 构建用户实体
        User user = User.builder()
                .username(registerDTO.getUsername())
                .password(encodedPassword)
                .salt(salt)
                .nickname(registerDTO.getNickname())
                .mobile(registerDTO.getMobile())
                .countryCode(registerDTO.getCountryCode())
                .status(1) // 正常状态
                .build();

        // 保存到数据库
        userMapper.insert(user);

        // 自动登录
        UserLoginDTO loginDTO = UserLoginDTO.builder()
                .username(registerDTO.getUsername())
                .password(registerDTO.getPassword())
                .build();

        UserVO userVO = userService.login(loginDTO);

        log.info("用户注册成功：{}", userVO);
        return Result.success(userVO, "注册成功");
    }
    /**
     * 获取用户信息
     * @return UserVO
     */
    @GetMapping("/info")
    public Result<UserVO> getUserInfo(){
        log.info("获取当前用户信息");
        Long userId = BaseContext.getCurrentId();
        UserVO userVO = userService.getUserInfo(userId);
        return Result.success(userVO,MessageConstant.GET_USER_INFO_SUCCESS);
    }
}
