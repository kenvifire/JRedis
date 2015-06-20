package com.kenvifire.jmemcached;

/**
 * Created by hannahzhang on 15/6/19.
 */
public class CacheItem {
    private String key;
    private long expTime;
    private int flags;
    private int bytes;
    private long casUnique;
    private byte[] data;

    public long getExpTime() {
        return expTime;
    }

    public void setExpTime(long expTime) {
        this.expTime = expTime;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
