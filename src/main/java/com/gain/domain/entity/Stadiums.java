package com.gain.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 球场信息表
 * @TableName stadiums
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("stadiums")
public class Stadiums implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 球场唯一 ID (自增)
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 球场名称
     */
    @TableField("name")
    private String name;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 场地电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 创建时间
     * fill = FieldFill.INSERT 表示插入时自动填充
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     * fill = FieldFill.INSERT_UPDATE 表示插入和更新时自动填充
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    // 用 String 接收 POINT 类型的字符串
    /**
     * 经纬度坐标 (数据库存储为 POINT 类型)
     */
    @TableField("location")
    private String location;

    // 解析经纬度的方法（业务层调用）
    public Double getLng() {
        if (location == null || !location.startsWith("POINT(")) {
            return null;
        }
        // 截取 POINT(116.397228 39.908419) 中的经度
        String[] coords = location.replace("POINT(", "").replace(")", "").split(" ");
        return Double.parseDouble(coords[0]);
    }

    public Double getLat() {
        if (location == null || !location.startsWith("POINT(")) {
            return null;
        }
        // 截取纬度
        String[] coords = location.replace("POINT(", "").replace(")", "").split(" ");
        return Double.parseDouble(coords[1]);
    }

    // 设置经纬度的方法（写入数据库）
    public void setCoords(Double lng, Double lat) {
        if (lng == null || lat == null) {
            this.location = null;
            return;
        }
        // 构造 POINT 字符串，数据库可直接识别
        this.location = String.format("POINT(%s %s)", lng, lat);
    }
}

