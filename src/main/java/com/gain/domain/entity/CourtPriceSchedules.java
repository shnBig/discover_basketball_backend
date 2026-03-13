package com.gain.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 价格策略表
 * @TableName court_price_schedules
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("court_price_schedules")
public class CourtPriceSchedules implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 价格策略唯一 ID (自增)
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属场地 ID
     */
    @TableField("court_id")
    private Long courtId;

    /**
     * 日期类型：1-工作日，2-周末，3-节假日，4-全部通用
     */
    @TableField("day_type")
    private Integer dayType;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalTime endTime;

    /**
     * 时段价格
     */
    @TableField("price")
    private BigDecimal price;

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
