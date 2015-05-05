package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/5/5.
 */
public class Config {

    public static ClientBufferLimitsConfig[] clientBufferLimitsDefaults =
            new ClientBufferLimitsConfig[Constants.REDIS_CLIENT_TYPE_COUNT];

    static {
        clientBufferLimitsDefaults[0] = new ClientBufferLimitsConfig(0,0,0);
        clientBufferLimitsDefaults[1] = new ClientBufferLimitsConfig(1024*1024*256,1024*1024*64,60);
        clientBufferLimitsDefaults[2] = new ClientBufferLimitsConfig(1024*1024*32,1024*1024*8,60);
    }
}
