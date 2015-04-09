package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class Server {
    public static void main(String [] args){
        RedisServer server = RedisServer.getInstance();

        server.sentinel_mode = checkForSentinelMode(args);

    }

    public static boolean checkForSentinelMode(String[] args){
        if(args[0].indexOf("redis-sentinel") > 0) return true;

        for(int j=1; j < args.length; j++){
            if("--sentinel".equals(args[j])) return true;
        }
        return false;
    }

    public void initServerConfig(){

    }

    public void getRandomHexChars(String str, int len){
        String charset = "0123456789abcdef";

    }

}
