package com.kenvifire.jredis;

import java.util.List;

/**
 * Created by hannahzhang on 15/5/7.
 */
public class SentinelState {
    public long current_epoch;
    public Dict masters;

    public int tilt;
    public int running_scripts;
    public long titl_start_time;
    public long previous_time;
    public List scripts_queue;
    public String announce_ip;
    public int announce_port;

    private SentinelState() {

    }

    private static SentinelState sentinel = new SentinelState();

    public static SentinelState getSentinelState(){
        return sentinel;
    }



}
