package com.gain.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 场地明细表
 * @TableName courts
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("courts")
public class Courts implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 场地唯一 ID (自增)
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属球场 ID
     */
    @TableField("stadium_id")
    private Long stadiumId;

    /**
     * 是否室内：1-是，0-否
     */
    @TableField("is_indoor")
    private Integer isIndoor;

    /**
     * 场地规格：1-全场，2-半场
     */
    @TableField("court_type")
    private Integer courtType;

    /**
     * 地板类型：1-木地板，2-塑胶，3-水泥，4-其他
     */
    @TableField("floor_type")
    private Integer floorType;

    /**
     * 是否有灯：1-有，0-无
     */
    @TableField("has_light")
    private Integer hasLight;

    /**
     * 场地数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 默认每小时单价
     */
    @TableField("base_price")
    private BigDecimal basePrice;

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
}
