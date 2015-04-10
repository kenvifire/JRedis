package com.kenvifire.jredis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
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
            SHA1_CTX.encode(seed).substring(0, len);
        }else{
            StringBuilder sb = new StringBuilder();
            byte[] buf = new byte[len];
            Integer pid = getProcessID();

            Calendar today = Calendar.getInstance();

            int pos = 0;
            fillBytes(buf,pos,pid);
            fillBytes(buf,pos+4,today);
            Random random = new Random();
            for(int j =0; j < len; j++){
                buf[j] ^= random.nextInt();
                sb.append(SHA1_CTX.HEX_DIGITS[buf[j] & 0x0F]);
            }

            return sb.toString();
        }

        return null;
    }

    public static void fillBytes(byte[] bytes,int pos,int value){
        bytes[pos] =(byte)(value & 0xff);
        bytes[pos+1] = (byte)((value >> 8) & 0xff) ;
        bytes[pos+2] = (byte)((value >> 16) & 0xff);
        bytes[pos+3] = (byte)((value >> 24) & 0xff);
    }

    public static void fillBytes(byte[] bytes, int pos,Calendar value){
        int start = pos;
        start += 4;
        fillBytes(bytes, pos, value.get(Calendar.MONTH));
        start += 4;
        fillBytes(bytes, pos, value.get(Calendar.DAY_OF_MONTH));
        start += 4;
        fillBytes(bytes, pos, value.get(Calendar.HOUR_OF_DAY));
        start += 4;
        fillBytes(bytes, pos, value.get(Calendar.MINUTE));
        start += 4;
        fillBytes(bytes, pos, value.get(Calendar.MILLISECOND));

    }


    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
                .intValue();
    }
}
