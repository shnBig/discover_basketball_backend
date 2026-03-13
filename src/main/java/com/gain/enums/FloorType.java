package com.gain.enums;

public enum FloorType {
    WOOD(1, "木地板"),
    RUBBER(2, "塑胶"),
    CEMENT(3, "水泥"),
    OTHER(4, "其他");

    private final int code;
    private final String description;

    // 构造函数
    FloorType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() { return code; }
    public String getDescription() { return description; }

    // 根据数字获取枚举对象 (用于数据库查询结果转换)
    public static FloorType fromCode(int code) {
        for (FloorType type : FloorType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return OTHER; // 默认值
    }
}
