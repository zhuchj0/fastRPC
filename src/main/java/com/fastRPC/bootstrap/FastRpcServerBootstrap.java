package com.fastRPC.bootstrap;

import com.fastRPC.server.Server;

public class FastRpcServerBootstrap {
    public static void start() throws Exception {
       Thread thread = new Thread(new Server(9000));
       thread.start();
    }
}
