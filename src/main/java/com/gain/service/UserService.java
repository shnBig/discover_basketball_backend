package com.gain.service;

import com.gain.domain.dto.UserLoginDTO;
import com.gain.domain.entity.Menu;
import com.gain.domain.entity.Role;
import com.gain.domain.vo.UserVO;

import java.util.List;

public interface UserService {

    UserVO login(UserLoginDTO userLoginDTO);

    List<Role> getUserRoles(Long userId);

    List<Menu> getUserMenus(Long userId);

    boolean hasPermission(Long userId, String permission);

    UserVO getUserInfo(Long userId);
}
