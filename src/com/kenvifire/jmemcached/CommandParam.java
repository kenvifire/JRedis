package com.kenvifire.jmemcached;

import java.util.List;

/**
 * Created by hannahzhang on 15/6/20.
 */
public class CommandParam {
    private String key;
    private int flags;
    private long expTime;
    private int bytes;
    private boolean noReply;
    private List<String> keys;
    private long value;
    private long casUnique;
    private byte[] data;



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public long getExpTime() {
        return expTime;
    }

    public void setExpTime(long expTime) {
        this.expTime = expTime;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public boolean isNoReply() {
        return noReply;
    }

    public void setNoReply(boolean noReply) {
        this.noReply = noReply;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getCasUnique() {
        return casUnique;
    }

    public void setCasUnique(long casUnique) {
        this.casUnique = casUnique;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
