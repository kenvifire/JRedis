package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class ClientBufferLimitsConfig {
    long hard_limit_bytes;
    long soft_limit_bytes;
    long soft_limit_seconds;

    public ClientBufferLimitsConfig(long hard_limit_bytes, long soft_limit_bytes, long soft_limit_seconds) {
        this.hard_limit_bytes = hard_limit_bytes;
        this.soft_limit_bytes = soft_limit_bytes;
        this.soft_limit_seconds = soft_limit_seconds;
    }
}
