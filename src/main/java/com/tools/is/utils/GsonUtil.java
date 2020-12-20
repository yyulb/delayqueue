package com.tools.is.utils;

import com.google.gson.*;

public class GsonUtil {
    private static final JsonParser JSON_PARSER = new JsonParser();
    //不用创建对象,直接使用Gson.就可以调用方法
    private static Gson gson;

    //判断gson对象是否存在了,不存在则创建对象
    static {
        //当使用GsonBuilder方式时属性为空的时候输出来的json字符串是有键值key的,显示形式是"key":null，而直接new出来的就没有"key":null的
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .disableHtmlEscaping()
                // long序列化为字符串, 不使用科学计数法形式
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .create();
    }

    //无参的私有构造方法
    private GsonUtil() {
    }

    public static JsonObject string2Object(String strJson) {
        return JSON_PARSER.parse(strJson).getAsJsonObject();
    }


    public static JsonArray string2Array(String strJson) {
        return JSON_PARSER.parse(strJson).getAsJsonArray();
    }


    /**
     * 将对象转成json格式
     *
     * @return String
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 将json转成特定的cls的对象
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            //传入json对象和对象类型,将json转成对象
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }


}
