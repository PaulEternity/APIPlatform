package com.paul.apiPlatform.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum InterfaceInfoStatusEnum {
    ONLINE("上线",0),
    OFFLINE("下线",1);

    private final String text;
    private final int value;

    private InterfaceInfoStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
