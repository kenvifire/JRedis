package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/5/6.
 */
public class Release {
    public static String REDIS_GIT_SHA1 = "2d4b58c4";
    public static String REDIS_GIT_DIRTY = "    3947";
    public static String REDIS_BUILD_ID = "HannahdeMacBook-Pro.local-1427151274";

    public static String redisGitSHA1(){
        return REDIS_GIT_SHA1;
    }

    public static String redisGitDirty(){
        return REDIS_GIT_DIRTY;
    }

    public long redisBuildId(){
        String buildid = RedisVersion.REDIS_VERSION + REDIS_BUILD_ID + REDIS_GIT_DIRTY + REDIS_GIT_SHA1;
        return CRC64.digest(buildid.getBytes());
    }
}
