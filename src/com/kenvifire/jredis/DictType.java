package com.kenvifire.jredis;


/**
 * Created by hannahzhang on 15/4/11.
 */
public interface DictType {
    public int hashFunction(Object key);
    public void keyDup(Object privdata, final Object key);
    public void valDup(Object privdata, final Object obj);
    int keyCompare(Object privdata, final Object key1 ,final Object key2);
    void keyDestructor (Object privdata, Object key);
    void valDestructor (Object privdata, Object obj);
}
