package com.fastRPC.netty.handler;


import com.fastRPC.serialization.jdkserialize.JdkDecoder;
import com.fastRPC.serialization.jdkserialize.JdkEncoder;
import com.fastRPC.serialization.jdkserialize.JdkSerializeCodecUtil;
import com.fastRPC.server.handler.TestServerHandler;
import io.netty.channel.ChannelPipeline;

public class JdkNaiveServerHandler implements NettyRpcHandler {
    @Override
    public void handle(ChannelPipeline pipeline) {
        JdkSerializeCodecUtil util = new JdkSerializeCodecUtil();
        pipeline.addLast(new JdkEncoder(util))
                .addLast(new JdkDecoder(util))
                .addLast(new TestServerHandler());
    }
}
