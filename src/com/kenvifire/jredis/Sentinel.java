package com.kenvifire.jredis;

import java.util.ArrayList;
import java.util.List;
import static com.kenvifire.jredis.RedisCommandProc.*;

/**
 * Created by hannahzhang on 15/5/9.
 */
public class Sentinel {
    public static final int REDIS_SENTINEL_PORT = 26379;

    public static List<RedisCommand> sentinelcmds = new ArrayList<RedisCommand>();

    static {
        sentinelcmds.add(new RedisCommand("ping",pingCommand,1,"",0,null,0,0,0,0,0));
        sentinelcmds.add(new RedisCommand("sentinel",sentinelCommand,-2,"",0,null,0,0,0,0,0));
        sentinelcmds.add(new RedisCommand("subscribe",subscribeCommand,-2,"",0,null,0,0,0,0,0));
        sentinelcmds.add(new RedisCommand("unsubscribe",unsubscribeCommand,-1,"",0,null,0,0,0,0,0));
        sentinelcmds.add(new RedisCommand("psubscribe",psubscribeCommand,-2,"",0,null,0,0,0,0,0));
        sentinelcmds.add(new RedisCommand("punsubscribe",punsubscribeCommand,-1,"",0,null,0,0,0,0,0));
        sentinelcmds.add(new RedisCommand("publish",sentinelPublishCommand,3,"",0,null,0,0,0,0,0));
        sentinelcmds.add(new RedisCommand("info",sentinelInfoCommand,-1,"",0,null,0,0,0,0,0));
        sentinelcmds.add(new RedisCommand("role",sentinelRoleCommand,1,"l",0,null,0,0,0,0,0));
        sentinelcmds.add(new RedisCommand("shutdown",shutdownCommand,-1,"",0,null,0,0,0,0,0));
    }
}
