package com.kenvifire.jredis;

import java.util.Hashtable;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class Dict {
    private DictType dictType;
    private Hashtable[] ht = new Hashtable[2];
    private long rehashidx;
    private Object privdata;
    private int iterators;

    public static Dict createDict(DictType type, Object privdata){
        Dict dict = new Dict();
        dict.ht[0] = new Hashtable();
        dict.ht[1] = new Hashtable();
        dict.dictType = type;
        dict.rehashidx = -1;
        dict.iterators = 0;
        return dict;
    }
}
