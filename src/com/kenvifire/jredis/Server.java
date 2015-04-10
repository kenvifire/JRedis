package com.kenvifire.jredis;

import static com.kenvifire.jredis.Constants.*;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class Server {
    public static void main(String [] args){
        RedisServer server = RedisServer.getInstance();

        server.sentinel_mode = checkForSentinelMode(args);
        initServerConfig(server);



    }

    public static boolean checkForSentinelMode(String[] args){
        if(args[0].indexOf("redis-sentinel") > 0) return true;

        for(int j=1; j < args.length; j++){
            if("--sentinel".equals(args[j])) return true;
        }
        return false;
    }

    public static void initServerConfig(RedisServer server){
        server.runid = Utils.getRandHexChars(REDIS_RUN_ID_SIZE);
        server.configfile = null;
        server.hz = REDIS_DEFAULT_HZ;
        server.arch_bit = 64;
        server.port = REDIS_SERVERPORT;
        server.tcp_backlog = REDIS_TCP_BACKLOG;
        server.bindaddr_count = 1;
        server.unixsocket = null;
        server.unixsocketperm = REDIS_DEFAULT_UNIX_SOCKET_PERM;
        server.ipfd_count = 0;
        server.sofd = -1;
        server.dbnum = REDIS_DEFAULT_DBNUM;



    }


}
