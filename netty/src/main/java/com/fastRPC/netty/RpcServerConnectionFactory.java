package com.fastRPC.netty;

import java.util.ArrayList;

public class RpcServerConnectionFactory {
    private final static ArrayList<RpcServerLoader> rpcServerLoaders = new ArrayList<>();

    public static RpcServerLoader getRpcConnectionInstance(Object o) throws Exception {
        assert o instanceof String;
        String addr = String.valueOf(o);
        if (-1 != containsServerLoader(addr)){
            return rpcServerLoaders.get(containsServerLoader(addr));
        }
        RpcServerLoader loader;
        synchronized (RpcServerConnectionFactory.class) {
                if (-1 == containsServerLoader(addr)) {
                    loader = new RpcServerLoader(addr);
                    rpcServerLoaders.add(loader);
                    loader.load();
                }else {
                    loader = rpcServerLoaders.get(containsServerLoader(addr));
                }
            }
        return loader;
    }

    private static int containsServerLoader(String addr){
        for(RpcServerLoader loader:rpcServerLoaders){
            if(loader.conpareAddr(addr)){
                return rpcServerLoaders.indexOf(loader);
            }
        }
        return -1;
    }
}
