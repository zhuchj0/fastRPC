package com.fastRPC;

import com.fastRPC.bootstrap.FastRpcClientBootstrap;
import com.fastRPC.bootstrap.FastRpcServerBootstrap;
import com.fastRPC.client.core.ProxyFactory;
import com.fastRPC.client.service.Echo2;


public class FastRpcApplication {

    public static void main(String[] args) throws Exception {

//        FastRpcClientBootstrap.start();
//        Echo2 service = ProxyFactory.getInstance(Echo2.class);
//        //计时500次
//        Long start = System.currentTimeMillis();
//        int count = 10;
//        for(int i=0;i<count;i++){
//            //实际调用
//            service.echotry();
//
//        }
//        Long pass = (System.currentTimeMillis()-start)/1000;
//        System.out.println("执行"+count+"次用时"+pass+"秒");

        FastRpcServerBootstrap.start();


    }
}


