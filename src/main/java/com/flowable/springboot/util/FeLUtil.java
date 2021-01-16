package com.flowable.springboot.util;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.ArrayCtxImpl;
import com.greenpineyu.fel.context.FelContext;

import java.util.Map;

public class FeLUtil {

    public boolean executeFel(Map<String,Object> variables, String expression) {
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = new ArrayCtxImpl();
        //FelContext ctx = fel.getContext();
        for(String key:variables.keySet()) {
            ctx.set(key,variables.get(key));
        }
        boolean result = (boolean) fel.eval(expression,ctx);
        return result;
    }
}
