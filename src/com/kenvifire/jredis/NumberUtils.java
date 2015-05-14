package com.kenvifire.jredis;

/**
 * Created by hannahzhang on 15/5/14.
 */
public class NumberUtils {
    public static  Integer parseInt(String str){
        try{
          return  Integer.valueOf(str);
        }catch (NumberFormatException e){
            return null;
        }
    }

    public static  Long parseLong(String str){
        try{
          return  Long.valueOf(str);
        }catch (NumberFormatException e){
            return null;
        }
    }


}
