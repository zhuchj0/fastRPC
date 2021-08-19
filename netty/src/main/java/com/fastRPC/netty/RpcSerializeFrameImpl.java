package com.fastRPC.netty;

import com.fastRPC.netty.handler.HessianHandler;
import com.fastRPC.netty.handler.JdkNativeHandler;
import com.fastRPC.netty.handler.NettyRpcHandler;
import com.fastRPC.serialization.RpcSerializeFrame;
import com.fastRPC.serialization.RpcSerializeProtocol;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import io.netty.channel.ChannelPipeline;

public class RpcSerializeFrameImpl implements RpcSerializeFrame {

    private static ClassToInstanceMap<NettyRpcHandler> handler = MutableClassToInstanceMap.create();

    static {
        handler.putInstance(JdkNativeHandler.class, new JdkNativeHandler());
        handler.putInstance(HessianHandler.class, new HessianHandler());
    }

    @Override
    public void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline) {
        switch (protocol) {
            case JDKSERIALIZE: {
                handler.getInstance(JdkNativeHandler.class).handle(pipeline);
                break;
            }
            case HESSIANSERIALIZE: {
                handler.getInstance(HessianHandler.class).handle(pipeline);
                break;
            }
            default: {
                break;
            }
        }
    }
}
