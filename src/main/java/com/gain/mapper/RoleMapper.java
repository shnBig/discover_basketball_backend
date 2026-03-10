package com.gain.mapper;

import com.gain.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<Role> selectByUserId(@Param("userId") Long userId);

    List<Role> selectAll();

    int insert(Role role);

    int updateById(Role role);

    int deleteById(@Param("id") Long id);

    Role selectById(@Param("id") Long id);

    Role selectByCode(@Param("roleCode") String roleCode);
}
