package com.fastRPC.server.handler;

import com.fastRPC.model.MessageRequest;
import com.fastRPC.model.MessageResponse;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class TestServerHandler extends ChannelHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TestServerHandler.class);

    //    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            MessageRequest m = (MessageRequest)msg;
            MessageResponse response = new MessageResponse();
            response.setMessageId(m.getMessageId());
            try{
                Class clz = Class.forName(m.getClassName());
                Method method = clz.getMethod(m.getMethodName());
                String res = String.valueOf(method.invoke(clz.getConstructor().newInstance()));
                response.setResult(res);
                logger.info("Server return:{}",response.toString());
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
