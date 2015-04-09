package com.kenvifire.jredis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by hannahzhang on 15/4/9.
 */
public class Utils {
    public static boolean seed_initialized = false;
    public static String seed;
    public static long counter =0;

    public static String getRandHexChars(int len){
        if(!seed_initialized){
            FileInputStream file = null;
            try {
                file = new FileInputStream(new File("/dev/urandom"));
                byte[] buff = new byte[len];
                file.read(buff);
                seed = new String(buff);
                seed_initialized = true;
            }catch (IOException e){
                System.out.println("error:"+e.getMessage());
            }finally {
                IOUtils.closeQuietly(file);
            }

        }

        if(seed_initialized){


        }
        return null;
    }
}
