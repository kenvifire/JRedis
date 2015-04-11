package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class RedisCommand {
    private String command;
    private IRedisCommandProc proc;
    private int arity;
    private String sflags;

    private int flag;

    private int firstKey;
    private int lastKey;
    private int keystep;
    private long microseconds, calls;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
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
