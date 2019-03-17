package com.fastRPC.server;

import com.fastRPC.netty.MessageChannelInitializer;
import com.fastRPC.netty.MessageServerChannelInitializer;
import com.fastRPC.netty.handler.HessianHandler;
import com.fastRPC.serialization.RpcSerialize;
import com.fastRPC.serialization.RpcSerializeProtocol;
import com.fastRPC.serialization.codec.DefaultDecoder;
import com.fastRPC.serialization.codec.DefaultEncoder;
import com.fastRPC.server.autoregister.ServerZookeeper;
import com.fastRPC.server.handler.TestServerHandler;
import com.fastRPC.utils.PropertyUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Server implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private int port;

    public Server() throws Exception{
        new ServerZookeeper();
        int port = Integer.valueOf(PropertyUtil.getProp("port"));
        this.port = port;

    }

    public Server(int port) throws Exception{
        new ServerZookeeper();
        this.port = port;
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class);

            RpcSerializeProtocol protocol = RpcSerializeProtocol.HESSIANSERIALIZE;
            switch (PropertyUtil.getProp("serialize")){
                case "JDK":protocol = RpcSerializeProtocol.JDKSERIALIZE;break;
                case "HESSIAN":protocol = RpcSerializeProtocol.HESSIANSERIALIZE;break;
            }


            bootstrap.childHandler(new MessageServerChannelInitializer().build(protocol))
                    .option(ChannelOption.SO_BACKLOG,128);

            ChannelFuture f = null;
            try {
                f = bootstrap.bind(port).sync();
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                logger.error("channel bind port :{} failed !" ,port);
            }

        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}
