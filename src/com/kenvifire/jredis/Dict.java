package com.kenvifire.jredis;

import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;

import java.util.Hashtable;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class Dict<K,V> {
    private DictType dictType;
    private DictHt<K,V>[] ht = new DictHt[2];
    private long rehashidx;
    private Object privdata;
    private int iterators;

    public static Dict createDict(DictType type, Object privdata){
        Dict dict = new Dict();
        dict.ht[0] = new DictHt();
        dict.ht[1] = new DictHt();
        dict.dictType = type;
        dict.rehashidx = -1;
        dict.iterators = 0;
        return dict;
    }



    /* -------------------------- private prototypes ---------------------------- */

    private int _dictExpandIfNeeded(Dict ht){
        //TODO
        return 0;
    }

    private long _dictNextPower(long size){
        //TODO next power
        return 0;
    }

    private int _dictKeyIndex(Dict ht,Object key){
        //TODO _dictKeyIndex
        return 0;
    }

    private int _dictInit(Dict ht,DictType type, Object privData){
        //TODO _dictInit
        return 0;
    }

    /* -------------------------- hash functions -------------------------------- */

    public int dictIntHashFunction(int key){
        key += ~(key << 15);
        key ^=  (key >> 10);
        key +=  (key << 3);
        key ^=  (key >> 6);
        key += ~(key << 11);
        key ^=  (key >> 16);
        return key;
    }

    public static int dict_hash_function_seed = 5381;

    private static void dictSetHashFunctionSeed(int seed){
        dict_hash_function_seed = seed;
    }

    public static int dictGetHashFunctionSeed(){
        return dict_hash_function_seed;
    }


    /* ----------------------------- API implementation ------------------------- */

    public static void _dictReset(DictHt ht){
        ht.head = null;
        ht.size = 0;
        ht.sizemask = 0;
        ht.used = 0;
    }



    static class DictEntry<K,V> {
        K key;
        V v;

        DictEntry next;

    }

    static class DictHt<K,V> {
        DictEntry<K,V> head;
        long size;
        long sizemask;
        long used;
    }


}
