package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class RedisDB {
    Dict dict;
    Dict expires;
    Dict blocking_keys;
    Dict ready_keys;
    Dict watched_keys;
    EvictionPoolEntry eviction_pool;
}
