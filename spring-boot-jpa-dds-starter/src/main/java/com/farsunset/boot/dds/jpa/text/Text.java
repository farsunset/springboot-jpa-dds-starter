package com.farsunset.boot.dds.jpa.text;

public class Text {
    private static final String BEAN_KEY = "%s_%s";
    private static final String TRANSACTION_MANAGER_KEY = "%s_transactionManager";

    public static String getRepositoryName(int key,Class tClass){
         return getRepositoryName(String.valueOf(key),tClass);
    }

    public static String getTransactionManager(int key){
        return getTransactionManager(String.valueOf(key));
    }


    public static String getRepositoryName(String key,Class tClass){
        return String.format(BEAN_KEY, key,tClass.getSimpleName());
    }

    public static String getTransactionManager(String key){
        return String.format(TRANSACTION_MANAGER_KEY, key);
    }
}
