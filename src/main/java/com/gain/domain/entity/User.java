package com.gain.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户基础信息表
 * @TableName users
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一ID (自增)
     * 如果后期改用雪花算法，可将 IdType.AUTO 改为 IdType.ASSIGN_ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户名/账号
     */
    @TableField("username")
    private String username;

    /**
     * 加密后的登录密码
     * @JsonIgnore 作用：在使用 Jackson 序列化（转JSON）时忽略该字段，增加安全性
     */
    @JsonIgnore
    @TableField("password")
    private String password;

    /**
     * 手机号，核心登录凭证
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 国际区号
     */
    @TableField("country_code")
    private String countryCode;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 头像URL
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 运营商类型 (1:移动, 2:联通, 3:电信)
     */
    @TableField("carrier_type")
    private Integer carrierType;

    /**
     * 运营商返回的匿名标识符，用于二次验证
     */
    @TableField("openid")
    private String openid;

    /**
     * 最近一次登录的Token
     */
    @TableField("last_login_token")
    private String lastLoginToken;

    /**
     * 账号状态 (1:正常, 0:禁用, 2:待激活)
     */
    @TableField("status")
    private Integer status;

    /**
     * 密码盐值
     */
    @JsonIgnore
    @TableField("salt")
    private String salt;

    /**
     * 注册时的IP地址
     */
    @TableField("reg_ip")
    private String regIp;

    /**
     * 最后登录时间
     */
    @TableField("last_login_at")
    private LocalDateTime lastLoginAt;

    /**
     * 创建时间
     * fill = FieldFill.INSERT 表示插入时自动填充`
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     * fill = FieldFill.INSERT_UPDATE 表示插入和更新时自动填充
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}