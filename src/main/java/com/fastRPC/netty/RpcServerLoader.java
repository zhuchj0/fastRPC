package com.fastRPC.netty;


import com.fastRPC.serialization.RpcSerializeProtocol;
import com.fastRPC.utils.PropertyUtil;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class RpcServerLoader {
    private static final Logger logger = LoggerFactory.getLogger(RpcServerLoader.class);
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    private ChannelHandlerAdapter messageSendHandler = null;
    private Lock lock = new ReentrantLock();
    private Condition connectStatus = lock.newCondition();
    private Condition handlerStatus = lock.newCondition();
    private boolean connStatus=false;
    private String addr;
    private int port;


    public RpcServerLoader(String addr,int port) {
        this.addr = addr;
        this.port = port;
    }

    public RpcServerLoader(String addr) {
        Object[] o = addrToIPPort(addr);
        this.addr = (String) o[0];
        this.port = (int) o[1];
    }


    //开启连接
    public void load () throws Exception{

        RpcSerializeProtocol protocol = RpcSerializeProtocol.HESSIANSERIALIZE;
        switch (PropertyUtil.getProp("serialize")){
            case "JDK":protocol = RpcSerializeProtocol.JDKSERIALIZE;break;
            case "HESSIAN":protocol = RpcSerializeProtocol.HESSIANSERIALIZE;break;
        }
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        Future<ChannelFuture> res = executor.submit(new MessageSendInitializeTask(eventLoopGroup, addr,port,protocol));
        ChannelFuture confutue = res.get(2000,TimeUnit.MILLISECONDS);
        confutue.addListener((ChannelFuture future1)->{
            if(future1.isSuccess()){
                lock.lock();

                if (messageSendHandler == null) {
                    handlerStatus.await();
                }

                if (messageSendHandler != null) {
                    connectStatus.signalAll();
                    this.connStatus = true;
                }

                lock.unlock();
            }
        });
    }

    public void setMessageSendHandler(ChannelHandlerAdapter messageSendHandler) {
        try {
            lock.lock();
            this.messageSendHandler = messageSendHandler;
            handlerStatus.signal();
        } finally {
            lock.unlock();
        }
    }

    public ChannelHandlerAdapter getMessageSendHandler() {
        try {
            lock.lock();
            if (messageSendHandler == null) {
                connectStatus.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return messageSendHandler;
    }

    public boolean getConnStatus() {
        return connStatus;
    }

    public boolean conpareAddr(String addr){
        if(addr != null) {
            String[] s = addr.split(":");
            if(s.length == 2 && this.addr.equals(s[0]) && Integer.valueOf(s[1]) == this.port){
                return true;
            }
        }
        return false;
    }

    public Object[] addrToIPPort(String addr){
        Object[] result = new Object[2];
        if(addr != null) {
            String[] s = addr.split(":");
            if(s.length == 2 ){
                try {
                    result[0] = String.valueOf(s[0]);
                    result[1] = Integer.valueOf(s[1]);
                }catch (Exception e){
                    logger.error("address error");
                }
            }
        }
        return result;
    }

}
