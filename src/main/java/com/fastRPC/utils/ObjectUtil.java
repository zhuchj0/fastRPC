package com.fastRPC.utils;

import java.util.*;

public class ObjectUtil {
    public static boolean isEmptyOrNull(Object object){
        if(object == null){
            return true;
        } else if(object instanceof String){
            String str = String.valueOf(object);
            if ("".equals(str.trim())){
                return true;
            }
        }else if(object instanceof Map){
            Map map = (Map)object;
            if (map.size() == 0){
                return true;
            }
        }else if(object instanceof List){
            List list = (List)object;
            if (list.size() == 0){
                return true;
            }
        }else if(object instanceof Object[]){
            Object[] arr = (Object[])object;
            if(arr.length == 0){
                return true;
            }
        }
        return false;
    }

    public static boolean isEmptyOrNull(Object... objs) {
        boolean flag = false;
        for(Object obj : objs){
            flag = flag || isEmptyOrNull(obj);
        }
        return flag;
    }
}
