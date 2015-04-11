package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/4/11.
 */
public interface IRedisGetKeysProc {
    int apply(RedisCommand command,RedisObject[] args,int argc, int[] numberkeys);
}
