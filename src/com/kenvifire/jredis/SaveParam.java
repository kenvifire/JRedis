package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/4/9.
 */
public class SaveParam {
    private long seconds;
    private int changes;

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public int getChanges() {
        return changes;
    }

    public void setChanges(int changes) {
        this.changes = changes;
    }
}
