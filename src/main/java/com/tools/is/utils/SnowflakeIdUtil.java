package com.tools.is.utils;


/**
 * 雪花算法生成唯一ID
 */
public class SnowflakeIdUtil {

    private static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1,1);


    public static long nextId(){
        return snowflakeIdWorker.nextId();
    }
}
