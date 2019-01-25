package com.fastRPC;

import com.fastRPC.client.Client;
import com.fastRPC.server.Server;
import com.fastRPC.server.service.EchoRandomServiceImpl;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
public class FastRpcApplication {
    //Spring boot的CommandLineRunner接口主要用于实现在应用初始化后，去执行一段代码块逻辑，这段初始化代码在整个应用生命周期内只会执行一次。

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FastRpcApplication.class, args);
//        new Server(9000).run();
         new Client().run();

    }


}

