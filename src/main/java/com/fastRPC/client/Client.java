package com.fastRPC.client;


import com.fastRPC.client.zookeeper.ClientZookeeper;
import com.fastRPC.client.zookeeper.NamingManager;
import com.fastRPC.netty.RpcServerLoader;
import com.fastRPC.utils.PropertyUtil;

import java.util.Properties;

public  class Client  {
    public Client(){
        new ClientZookeeper(NamingManager.getManager());
    }
}
