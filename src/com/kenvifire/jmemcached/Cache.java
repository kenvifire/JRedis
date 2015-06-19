package com.kenvifire.jmemcached;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hannahzhang on 15/6/19.
 */
public class Cache {
    private static  Map<String,CacheItem> store = new HashMap<>();

    public static void set(CacheItem item){
        store.put(item.getKey(),item);
    }


}
