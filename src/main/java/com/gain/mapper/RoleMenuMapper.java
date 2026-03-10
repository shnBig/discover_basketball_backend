package com.gain.mapper;

import com.gain.domain.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMenuMapper {

    int insert(RoleMenu roleMenu);

    int deleteByRoleId(@Param("roleId") Long roleId);

    int deleteByMenuId(@Param("menuId") Long menuId);

    List<RoleMenu> selectByRoleId(@Param("roleId") Long roleId);

    List<RoleMenu> selectByMenuId(@Param("menuId") Long menuId);
}
