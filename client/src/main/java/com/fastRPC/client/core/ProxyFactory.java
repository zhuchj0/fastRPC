

package com.fastRPC.client.core;


import com.fastRPC.proxy.MyInvocationHandler;

import java.lang.reflect.Proxy;

public class ProxyFactory {
    //实例化
    public static <T> T getInstance(Class<T> tClass) {
        ClassLoader classLoader = tClass.getClassLoader();
        Class<T>[] interfaces = new Class[]{tClass};
        return (T) Proxy.newProxyInstance(classLoader, interfaces, new MyInvocationHandler());
    }
}
