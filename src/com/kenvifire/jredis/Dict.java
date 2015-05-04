package com.kenvifire.jredis;

import sun.jvm.hotspot.utilities.Assert;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class Dict<K,V> {
    public static boolean dict_can_resize = true;
    public static int dict_force_resize_ratio = 5;

    public static final int DICT_OK = 0;
    public static final int DICT_ERR = 1;

    public static final int DICT_HT_INITIAL_SIZE = 4;

    private DictType type;
    private Object privdata;
    private DictHt<K,V>[] ht = new DictHt[2];
    private long rehashidx;
    private int iterators;

    private Dict(){

    }
    public static Dict dictCreate(DictType type, Object privdata){
        Dict d = new Dict();
        d._dictInit(d, type, privdata);
        return d;
    }



    /* -------------------------- private prototypes ---------------------------- */

    private int _dictExpandIfNeeded(Dict ht){
        //TODO
        return 0;
    }


    private int _dictKeyIndex(Dict ht,Object key){
        //TODO _dictKeyIndex
        return 0;
    }

    private void _dictInit(Dict d,DictType type, Object privData){
        d.ht[0]._dictReset();
        d.ht[1]._dictReset();
        d.type = type;
        d.privdata = privData;
        d.rehashidx = -1;
        d.iterators = 0;
    }

    private int dictResize(){
        long minimal;

        if(!dict_can_resize || dictIsRehashing()) return DICT_ERR;

        minimal = ht[0].used;
        if(minimal < DICT_HT_INITIAL_SIZE){
            minimal = DICT_HT_INITIAL_SIZE;
        }
        return dictExpand(minimal);


    }

    private int dictExpand(long size){
        DictHt n = new DictHt();

        long realsize = _dictNextPower(size);

        if(dictIsRehashing() || ht[0].used > size){
           return DICT_ERR;
        }

        n.size = realsize;
        n.sizemask = realsize - 1;
        n.table = new DictEntry[(int)realsize];
        n.used = 0;

        if(ht[0].table == null){
            ht[0] = n;
            return DICT_OK;
        }

        ht[1] = n;
        rehashidx = 0;
        return DICT_OK;


    }

    public int dictRehash(int n){
        DictEntry de,nextde;

        if(!dictIsRehashing()) return 0;

        while(n-->0) {
            if (ht[0].used == 0) {
                ht[0].table = null;
                ht[1]._dictReset();
                rehashidx = -1;
                return 0;
            }


            Assert.that(ht[0].size > (long) rehashidx, "rehashidx can't overflow");

            while (ht[0].table[(int) rehashidx] == null) rehashidx++;

            de = ht[0].table[(int) rehashidx];

            while (de != null) {
                int h;

                nextde = de.next;

                h = (int) (type.hashFunction(de.key) & ht[1].sizemask);
                de.next = ht[1].table[h];
                ht[1].table[h] = de;
                ht[0].used--;
                ht[1].used++;
                de = nextde;
            }

            ht[0].table[(int) rehashidx] = null;
            rehashidx++;
        }
        return 1;
    }

    private boolean dictIsRehashing(){
        return this.rehashidx != -1;
    }

    private long _dictNextPower(long size){
        long i = DICT_HT_INITIAL_SIZE;

        if(size > Long.MAX_VALUE) return Long.MAX_VALUE;

        while(true){
            if(i >= size)
                return i;
            i *= 2;
        }

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





    static class DictEntry<K,V> {
        K key;
        V v;

        DictEntry next;

    }

    static class DictHt<K,V> {
        DictEntry<K,V>[] table;
        long size;
        long sizemask;
        long used;

        public void _dictReset(){
            this.table = null;
            this.size = 0;
            this.sizemask = 0;
            this.used = 0;
        }
    }


}
