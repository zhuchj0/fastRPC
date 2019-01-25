package com.fastRPC.server.handler;

import com.fastRPC.model.MessageRequest;
import com.fastRPC.model.MessageResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;

public class TestServerHandler extends ChannelHandlerAdapter {



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            MessageRequest m = (MessageRequest)msg;
            MessageResponse response = new MessageResponse();
            response.setMessageId(m.getMessageId());
            System.out.println("get:");
            try{
                Class clz = Class.forName(m.getClassName());
                Method method = clz.getMethod(m.getMethodName());
                String res = String.valueOf(method.invoke(clz.getConstructor().newInstance()));
                response.setResult(res);
                System.out.println(response);
            }catch (Exception e){
                response.setError(e.getMessage());
            }
            ctx.writeAndFlush(response);
        }finally {

        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
