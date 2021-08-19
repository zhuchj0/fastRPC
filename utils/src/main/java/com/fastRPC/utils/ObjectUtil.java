package com.fastRPC.utils;

import java.util.List;
import java.util.Map;

public class ObjectUtil {
    public static boolean isEmptyOrNull(Object object){
        if(object == null){
            return true;
        } else if(object instanceof String){
            String str = String.valueOf(object);
            return "".equals(str.trim());
        }else if(object instanceof Map){
            Map map = (Map)object;
            return map.size() == 0;
        }else if(object instanceof List){
            List list = (List)object;
            return list.size() == 0;
        }else if(object instanceof Object[]){
            Object[] arr = (Object[])object;
            return arr.length == 0;
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
