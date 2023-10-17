package com.safe.study.base;

import java.util.Collection;

public class CollectionUtils {
    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.size() <= 0;
    }
}
