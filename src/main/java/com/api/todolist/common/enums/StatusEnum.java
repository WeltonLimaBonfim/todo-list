package com.api.todolist.common.enums;


import java.util.Arrays;

public enum StatusEnum {
    PENDING, COMPLETED;

    public static StatusEnum getStatusName(String status) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(status))
                .findFirst()
                .orElse(null);
    }
}
