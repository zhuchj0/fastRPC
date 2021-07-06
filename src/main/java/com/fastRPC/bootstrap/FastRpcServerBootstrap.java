package com.fastRPC.bootstrap;

import com.fastRPC.server.Server;

public class FastRpcServerBootstrap {

    public static void main(String[] args) throws Exception {
       Thread thread = new Thread(new Server());
       thread.start();
    }
}
