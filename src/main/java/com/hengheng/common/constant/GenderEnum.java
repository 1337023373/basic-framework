package com.hengheng.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lyw
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {
    /**
     * 停用
     */
    MAN(0, "男"),
    /**
     * 正常
     */
    WOMAN(1, "女"),
    UNKNOWN(2, "未知");


    private final int code;
    private final String value;

    /**
     * 根据 code 获取枚举
     */
    public static GenderEnum fromCode(Integer code) {
        if (code == null) {
            return UNKNOWN;
        }
        for (GenderEnum gender : values()) {
            if (gender.code == code) {
                return gender;
            }
        }
        return UNKNOWN;
    }

    /**
     * 根据 code 获取中文值
     */
    public static String getValueByCode(Integer code) {
        return fromCode(code).getValue();
    }
}
