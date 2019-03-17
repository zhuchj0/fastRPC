package com.fastRPC.netty;

import com.fastRPC.client.handler.RpcClientHandler;

import com.fastRPC.serialization.RpcSerializeProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class MessageSendInitializeTask implements Callable {
    private static final Logger logger = LoggerFactory.getLogger(MessageSendInitializeTask.class);
    private EventLoopGroup eventLoopGroup;
    private String ip;
    private int port;
    private RpcSerializeProtocol protocol;

    public MessageSendInitializeTask(EventLoopGroup eventLoopGroup, String ip, int port,RpcSerializeProtocol protocol) {
        this.eventLoopGroup = eventLoopGroup;
        this.ip = ip;
        this.port = port;
        this.protocol = protocol;
    }

    @Override
    public Object call() throws Exception {
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .remoteAddress(ip,port);

        //自定义序列化方法
        b.handler(new MessageChannelInitializer(protocol));

        ChannelFuture future = b.connect();
        logger.info("connecting to {}:{}",ip,port);
        future.addListener((ChannelFuture future1)->{
            if(future1.isSuccess()){
                ChannelHandlerAdapter handler = future1.channel().pipeline().get(RpcClientHandler.class);
                logger.info("setting the handler for {}:{}",ip,port);
//                ((RpcClientHandler) handler).getChannel().writeAndFlush(new MessageRequest());
                RpcServerConnectionFactory.getRpcConnectionInstance(ip+":"+port).setMessageSendHandler(handler);
            }else {
                logger.info("Conn't connect to {}:{}",ip,port);
            }
        });
        return future;
    }
}
