package com.flowable.springboot.util;

public class StringUtil {
    public static boolean isNotEmpty(String str){
        if(null != str && !"".equals(str)){
            return true;
        }
        return false;
    }
}
