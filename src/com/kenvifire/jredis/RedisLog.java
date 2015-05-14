package com.kenvifire.jredis;


/**
 * Created by hannahzhang on 15/5/13.
 */
public class RedisLog {
    public static void redisLog(int level,String msg){
       System.out.println(msg);
    }
    public static void redisLog(int level,String msg,Throwable e){
        System.out.println(msg);
        System.out.println(e.getMessage());
    }
}
