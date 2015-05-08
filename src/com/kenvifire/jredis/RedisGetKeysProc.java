package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/5/9.
 */
public class RedisGetKeysProc {
    public static IRedisGetKeysProc zunionInterGetKeys = new IRedisGetKeysProc() {
        @Override
        public int apply(RedisCommand command, RedisObject[] args, int argc, int[] numberkeys) {
            return 0;
        }
    };

    public static IRedisGetKeysProc evalGetKeys = new IRedisGetKeysProc() {
        @Override
        public int apply(RedisCommand command, RedisObject[] args, int argc, int[] numberkeys) {
            return 0;
        }
    };

    public static IRedisGetKeysProc sortGetKeys = new IRedisGetKeysProc() {
        @Override
        public int apply(RedisCommand command, RedisObject[] args, int argc, int[] numberkeys) {
            return 0;
        }
    };
}
