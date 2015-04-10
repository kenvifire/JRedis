package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class Constants {
    public static final int REDIS_RUN_ID_SIZE = 40;
    public static final int REDIS_BINDADDR_MAX = 16;

    public static final int REDIS_METRIC_SAMPLES = 16;
    public static final int REDIS_METRIC_COUNT = 3;

    public static final int REDIS_CLIENT_TYPE_COUNT = 3;

    /*Static server configuration */
    public static final int REDIS_DEFAULT_HZ  = 10;
    public static final int REDIS_MIN_HZ = 1;
    public static final int REDIS_MAX_HZ = 500;
    public static final int REDIS_SERVERPORT = 6379;
    public static final int REDIS_TCP_BACKLOG = 511;
    public static final int REDIS_MAXIDLETIME = 0;
    public static final int REDIS_DEFAULT_DBNUM = 16;

    public static final int REDIS_DEFAULT_UNIX_SOCKET_PERM  = 0;

}
