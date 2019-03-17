package com.fastRPC.netty.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

public interface NettyRpcHandler   {
    void handle(ChannelPipeline pipeline);
}
