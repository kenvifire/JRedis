package com.kenvifire.jredis;

import sun.jvm.hotspot.utilities.Assert;

import java.util.Calendar;

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

    private int _dictExpandIfNeeded(){
        if(dictIsRehashing()) return DICT_OK;

        if(ht[0].size == 0) return dictExpand(DICT_HT_INITIAL_SIZE);

        if(ht[0].used >= ht[0].size &&
                (dict_can_resize ||
                ht[0].used/ht[0].size > dict_force_resize_ratio)){
            return dictExpand(ht[0].used * 2);
        }

        return DICT_OK;
    }


    private int _dictKeyIndex(Object key){
        int h, idx=0, table;
        DictEntry he;

        if(_dictExpandIfNeeded() == DICT_ERR)
            return -1;

        h = type.hashFunction(key);

        for(table = 0; table <= 1; table++){
            idx = h & (int)ht[table].sizemask;

            he = ht[table].table[idx];

            while (he != null){
                if(type.keyCompare(privdata,key,he.key) == 0)
                    return -1;

                he = he.next;
            }
            if(!dictIsRehashing()) break;

        }

        return idx;
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

    private int dictRehashMilliseconds(int ms){
        long start = System.currentTimeMillis();
        int rehashes = 0;

        while(dictRehash(100) > 0){
            rehashes += 100;
            if(System.currentTimeMillis() - start > ms) break;
        }

        return rehashes;
    }

    public int dictAdd(K key, V val){
       DictEntry<K,V>  entry = dictAddRaw(key);
        if(entry == null) return DICT_ERR;

        dictSetVal(entry,val);
        return DICT_OK;
    }

    private DictEntry dictAddRaw(Object key){
        int index;
        DictEntry entry;
        DictHt ht;

        if(dictIsRehashing()) _dictRehashStep();

        if((index = _dictKeyIndex(key)) == -1)
            return null;

        ht = dictIsRehashing() ? this.ht[1] : this.ht[0];
        entry = new DictEntry();
        entry.next = ht.table[index];
        ht.table[index] = entry;
        ht.used++;

        dictSetKey(entry,key);
        return entry;



    }

    private void dictSetKey(DictEntry entry,Object key){
        entry.key = type.keyDup(privdata,key);

    }

    private void dictSetVal(DictEntry entry,Object val){
        entry.v = type.valDup(privdata,val);
    }

    public void _dictRehashStep(){
        if( this.iterators == 0) dictRehash(1);
    }

    public Object dictFetchValue(Object key){
        DictEntry he;
        he = dictFind(key);

        return he != null ? he.v : null;
    }

    private DictEntry dictFind(Object key){
        DictEntry he;

        int h,idx,table;

        if(ht[0].size == 0) return null;
        if(dictIsRehashing()) _dictRehashStep();
        h = type.hashFunction(key);

        for(table = 0; table <= 1;table++){
            idx = h & (int) ht[table].sizemask;
            he = ht[table].table[idx];

            while (he != null){
                if(type.keyCompare(privdata,key,he.key) == 0 )
                    return he;
                he = he.next;
            }

            if(!dictIsRehashing()) return null;
        }
        return null;
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
