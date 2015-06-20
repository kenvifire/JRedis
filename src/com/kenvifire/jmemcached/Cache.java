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

    public static boolean add(CacheItem item){
        CacheItem old = store.get(item.getKey());

        if(old != null){
            return false;
        }

        store.put(item.getKey(),item);
        return true;
    }


    public static boolean replace(CacheItem item){
        CacheItem old = store.get(item.getKey());

        if(old == null){
            return false;
        }

        return store.replace(item.getKey(),old,item);
    }

    private static boolean addData(CacheItem item, boolean append){
        CacheItem old = store.get(item.getKey());

        if(old == null){
            return false;
        }
        int totalLen = item.getBytes() + old.getBytes();
        byte[] buf = new byte[totalLen];

        if(append) {
            System.arraycopy(old.getData(), 0, buf, 0, old.getData().length);
            System.arraycopy(item.getData(), 0, buf, old.getBytes() - 1, item.getBytes());
        }else{
            System.arraycopy(item.getData(), 0, buf, 0, item.getBytes());
            System.arraycopy(old.getData(), 0, buf, item.getBytes() - 1, old.getBytes());
        }

        item.setBytes(totalLen);
        item.setData(buf);

        return true;
    }


    public static boolean append(CacheItem item){
        return addData(item, true);
    }

    public static boolean prepend(CacheItem item){
        return addData(item, false);
    }


    public static CacheItem get(String key){
        return  store.get(key);
    }



}
