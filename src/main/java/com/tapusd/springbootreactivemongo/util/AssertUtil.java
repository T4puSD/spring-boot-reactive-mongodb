package com.tapusd.springbootreactivemongo.util;

import org.springframework.util.ObjectUtils;

import java.util.Objects;

public class AssertUtil {
    private AssertUtil() {
    }

    public static void assertNotNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertNotBlank(String value, String variblaeName) {
        if (ObjectUtils.isEmpty(value)) {
            throw new IllegalArgumentException(String.format("%s can not be blank", variblaeName));
        }
    }
}
