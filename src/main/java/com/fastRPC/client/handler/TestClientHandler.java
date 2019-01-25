package com.fastRPC.client.handler;

import com.fastRPC.model.MessageRequest;
import com.fastRPC.model.MessageResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

public class TestClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("返回结果:");
        MessageResponse response = (MessageResponse)msg;
        System.out.println("返回结果："+response);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MessageRequest request = new MessageRequest();
        request.setClassName("com.fastRPC.server.service.EchoRandomServiceImpl");
        request.setMessageId("1");
        request.setMethodName("echo");
        ctx.channel().writeAndFlush(request);
    }
}
