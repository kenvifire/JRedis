package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/4/11.
 */
public class MigrateCacheDictType implements DictType{
    @Override
    public int hashFunction(Object key) {
        return 0;
    }

    @Override
    public void keyDup(Object privdata, Object key) {

    }

    @Override
    public void valDup(Object privdata, Object obj) {

    }

    @Override
    public int keyCompare(Object privdata, Object key1, Object key2) {
        return 0;
    }

    @Override
    public void keyDestructor(Object privdata, Object key) {

    }

    @Override
    public void valDestructor(Object privdata, Object obj) {

    }
}
