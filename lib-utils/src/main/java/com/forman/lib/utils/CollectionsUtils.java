/*
 * Copyright 2014 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.forman.lib.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * 收藏相关工具类
 */
@SuppressWarnings("JavaDoc")
public class CollectionsUtils {

    /**
     * 判断是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 获取size
     *
     * @param collection
     * @return
     */
    public static int size(Collection<?> collection) {
        return collection != null ? collection.size() : 0;
    }

    /**
     * 取出两个列表中，相同的数据
     *
     * @param list1
     * @param l2
     * @param <T>
     * @return
     */
    public static <T> List<T> distinct(List<T> list1, List<T> l2) {
        HashSet<T> set = new HashSet<>();
        List<T> result = new ArrayList<>();
        set.addAll(list1);
        for (T t : l2) {
            boolean b = set.add(t);
            if (!b) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * 取出第二个列表中，不存在于第一个列表的数据，即第二个列表中的新数据
     *
     * @param list1
     * @param l2
     * @param <T>
     * @return
     */
    public static <T> List<T> getNewList(List<T> list1, List<T> l2) {
        List<T> result = new ArrayList<>();
        for (T t : l2) {
            if (list1.contains(t))
                continue;
            result.add(t);
        }
        return result;
    }
}
