package com.fastRPC.netty;

import com.fastRPC.serialization.RpcSerializeFrame;
import com.fastRPC.serialization.RpcSerializeProtocol;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MessageChannelInitializer extends ChannelInitializer<SocketChannel> {
    private RpcSerializeProtocol protocol;
    private RpcSerializeFrame frame = new RpcSerializeFrameImpl();

    public MessageChannelInitializer(RpcSerializeProtocol protocol) {
        this.protocol = protocol;
    }

    public RpcSerializeProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(RpcSerializeProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        frame.select(protocol,pipeline);
    }
}
