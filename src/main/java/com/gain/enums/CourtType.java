package com.gain.enums;

public enum CourtType {
    FULL(1, "全场"),
    HALF(2, "半场");

    private final int code;
    private final String desc;

    CourtType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() { return code; }
    public String getDesc() { return desc; }

    public static CourtType fromCode(int code) {
        for (CourtType type : CourtType.values()) {
            if (type.getCode() == code) return type;
        }
        throw new IllegalArgumentException("无效的场地规格代码: " + code);
    }
}
