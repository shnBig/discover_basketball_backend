package com.gain.mapper;

import com.gain.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    int insert(UserRole userRole);

    int deleteByUserId(@Param("userId") Long userId);

    int deleteByRoleId(@Param("roleId") Long roleId);

    List<UserRole> selectByUserId(@Param("userId") Long userId);

    List<UserRole> selectByRoleId(@Param("roleId") Long roleId);
}
