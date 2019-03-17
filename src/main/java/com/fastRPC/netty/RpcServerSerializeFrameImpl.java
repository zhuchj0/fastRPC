package com.fastRPC.netty;

import com.fastRPC.netty.handler.*;
import com.fastRPC.serialization.RpcSerializeFrame;
import com.fastRPC.serialization.RpcSerializeProtocol;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import io.netty.channel.ChannelPipeline;

public class RpcServerSerializeFrameImpl implements RpcSerializeFrame {

    private static ClassToInstanceMap<NettyRpcHandler> handler = MutableClassToInstanceMap.create();

    static {
        handler.putInstance(JdkNaiveServerHandler.class, new JdkNaiveServerHandler());
        handler.putInstance(HessianServerHandler.class, new HessianServerHandler());
    }

    @Override
    public void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline) {
        switch (protocol){
            case JDKSERIALIZE:
                handler.getInstance(JdkNaiveServerHandler.class).handle(pipeline);break;
            case HESSIANSERIALIZE:
                handler.getInstance(HessianServerHandler.class).handle(pipeline);break;
        }
    }
}
