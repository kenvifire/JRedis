package com.kenvifire.jredis;

import javax.imageio.IIOException;
import java.io.Closeable;
import java.io.IOException;

/**
 * Created by hannahzhang on 15/4/9.
 */
public class IOUtils {
    public static void closeQuietly(Closeable closeable){
        try {
            if(closeable != null) {
                closeable.close();
            }
        }catch (IOException e){

        }
    }
}
