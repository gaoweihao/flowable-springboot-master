package com.flowable.springboot.util;

import java.util.UUID;

public class StringUtil {

    public static boolean isNotEmpty(String str){
        if(null != str && !"".equals(str)){
            return true;
        }
        return false;
    }

    public static String getPrimary(){
        return UUID.randomUUID().toString().replace("-","");
    }
    public static void main(String[] args) {
      System.out.println(UUID.randomUUID().toString().replace("-",""));
//        System.out.println("021001001-000626-54-0-1594879935287".length());
    }
}
