package com.gain.mapper;

import com.gain.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<Menu> selectByRoleId(@Param("roleId") Long roleId);

    List<Menu> selectByUserId(@Param("userId") Long userId);

    List<Menu> selectList(@Param("parentId") Long parentId);

    int insert(Menu menu);

    int updateById(Menu menu);

    int deleteById(@Param("id") Long id);

    Menu selectById(@Param("id") Long id);

    List<Menu> selectAll();
}
