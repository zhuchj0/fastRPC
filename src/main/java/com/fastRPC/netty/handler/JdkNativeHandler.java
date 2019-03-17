package com.fastRPC.netty.handler;

import com.fastRPC.client.handler.RpcClientHandler;
import com.fastRPC.serialization.jdkserialize.JdkDecoder;
import com.fastRPC.serialization.jdkserialize.JdkEncoder;
import com.fastRPC.serialization.jdkserialize.JdkSerializeCodecUtil;
import io.netty.channel.ChannelPipeline;

public class JdkNativeHandler implements NettyRpcHandler {

    @Override
    public void handle(ChannelPipeline pipeline) {
        JdkSerializeCodecUtil util = new JdkSerializeCodecUtil();
        //添加编码解码器和处理器
       pipeline.addLast(new JdkDecoder(util))
               .addLast(new JdkEncoder(util))
               .addLast(new RpcClientHandler());
    }
}
