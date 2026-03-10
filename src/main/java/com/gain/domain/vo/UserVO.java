package com.gain.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String nickname;

//    private String mobile;
//
//    private String countryCode;

    private String avatarUrl;

//    private Integer carrierType;
//
//    private LocalDateTime lastLoginAt;
//
//    private LocalDateTime createdAt;

    private String token;

    private List<String> roles;

    private List<String> permissions;
}
