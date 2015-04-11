package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/4/11.
 */
public class RedisRuntimeException extends RuntimeException{
    public RedisRuntimeException(String message){
        super(message);
    }
}
