package com.fastRPC.client;


import com.fastRPC.zookeeper.ClientZookeeper;
import com.fastRPC.zookeeper.NamingManager;

public  class Client  {
    public Client(){
        new ClientZookeeper(NamingManager.getManager());
    }
}
