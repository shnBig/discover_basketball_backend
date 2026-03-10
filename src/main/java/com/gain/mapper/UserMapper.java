package com.gain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gain.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    @Select("select id,username,password,salt,nickname,status,avatar_url from users where username = #{username}")
    User getByUsername(String username);
    void updateLoginInfo(@Param("userId") Long userId, @Param("token") String token);

    @Select("select id,username,password,salt,nickname,status,avatar_url from users where id = #{userId}")
    User getById(Long userId);
}
