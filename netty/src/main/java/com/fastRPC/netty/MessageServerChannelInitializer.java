package com.fastRPC.netty;

import com.fastRPC.serialization.RpcSerializeProtocol;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MessageServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private RpcSerializeProtocol protocol;
    private RpcServerSerializeFrameImpl frame;

    public RpcSerializeProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(RpcSerializeProtocol protocol) {
        this.protocol = protocol;
    }

    public MessageServerChannelInitializer build(RpcSerializeProtocol protocol){
        this.protocol = protocol;
        return this;
    }

    public MessageServerChannelInitializer() {
        this.frame =  new RpcServerSerializeFrameImpl();
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        frame.select(protocol,pipeline);
    }
}
