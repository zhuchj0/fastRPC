package com.fastRPC.netty.handler;

import io.netty.channel.ChannelPipeline;

public interface NettyRpcHandler   {
    void handle(ChannelPipeline pipeline);
}
