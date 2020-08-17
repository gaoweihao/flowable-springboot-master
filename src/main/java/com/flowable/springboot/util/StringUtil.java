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

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {
        String test ="299d69f7-56b6-11ea-a1b8-d23f8b4d1fd4";
        if(StringUtil.isBlank(test)){
            System.out.println(test);
        };
    }
}
