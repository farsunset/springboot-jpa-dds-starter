package com.farsunset.boot.dds.jpa.thread;



public class SourceThreadLocal {

    private static ThreadLocal<String>  DATASOURCE_LOCAL = new ThreadLocal<>();

    public static void setSourceKey(String key){
        DATASOURCE_LOCAL.set(key);
    }

    public static void clearSourceKey(){
        DATASOURCE_LOCAL.remove();
    }

    public static String getSourceKey(){
        return DATASOURCE_LOCAL.get();
    }


}
