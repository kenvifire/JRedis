package com.kenvifire.jredis;

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

    private void dictExpand(long size){
        DictHt n;

        long realsize = _dictNextPower(size);

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
        DictEntry<K,V> head;
        long size;
        long sizemask;
        long used;

        public void _dictReset(){
            this.head = null;
            this.size = 0;
            this.sizemask = 0;
            this.used = 0;
        }
    }


}
