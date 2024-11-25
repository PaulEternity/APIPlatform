package com.paul.paulapicommon.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户状态枚举
 */
public enum UserAccountStatusEnum {

    NORMAL("正常", 0),

    BAN("封禁", 1);

    private final String text;
    private final int value;

    UserAccountStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }


    public static List<Integer> getValues(){
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }


    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

}
