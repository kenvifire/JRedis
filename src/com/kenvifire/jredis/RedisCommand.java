package com.kenvifire.jredis;

import sun.plugin2.main.server.ResultID;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class RedisCommand {
    private String name;
    private IRedisCommandProc proc;
    private Integer arity;
    private String sflags;

    private int flag;

    private IRedisGetKeysProc getKeys_proc;
    private Integer firstKey;
    private Integer lastKey;
    private int keystep;
    private long microseconds, calls;

    public RedisCommand(String name,IRedisCommandProc proc,Integer arity,String sflags,
                        Integer flag,IRedisGetKeysProc getKeys_proc,Integer firstKey,
                        Integer lastKey, int keystep,long microseconds,long calls){
        this.name = name;
        this.proc = proc;
        this.arity = arity;
        this.sflags = sflags;
        this.flag = flag;
        this.getKeys_proc = getKeys_proc;
        this.firstKey = firstKey;
        this.lastKey = lastKey;
        this.keystep = keystep;
        this.microseconds = microseconds;
        this.calls = calls;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IRedisCommandProc getProc() {
        return proc;
    }

    public void setProc(IRedisCommandProc proc) {
        this.proc = proc;
    }

    public int getArity() {
        return arity;
    }

    public void setArity(int arity) {
        this.arity = arity;
    }

    public String getSflags() {
        return sflags;
    }

    public void setSflags(String sflags) {
        this.sflags = sflags;
    }

    public int getFirstKey() {
        return firstKey;
    }

    public void setFirstKey(int firstKey) {
        this.firstKey = firstKey;
    }

    public int getLastKey() {
        return lastKey;
    }

    public void setLastKey(int lastKey) {
        this.lastKey = lastKey;
    }

    public int getKeystep() {
        return keystep;
    }

    public void setKeystep(int keystep) {
        this.keystep = keystep;
    }

    public long getMicroseconds() {
        return microseconds;
    }

    public void setMicroseconds(long microseconds) {
        this.microseconds = microseconds;
    }

    public long getCalls() {
        return calls;
    }

    public void setCalls(long calls) {
        this.calls = calls;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
