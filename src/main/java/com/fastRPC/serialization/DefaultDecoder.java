package com.fastRPC.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

@Component
public class DefaultDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes()<4)return;
        byteBuf.markReaderIndex();//标记读取的位置,如果发现缓冲区并没有足够长度的字节，则放弃本次读取
        int length = byteBuf.readInt();
        if(length<0){
            channelHandlerContext.close();
        }
        if(byteBuf.readableBytes()<length){
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] body = new byte[length];
        byteBuf.readBytes(body);
        ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(body));
        Object object = oi.readObject();
        list.add(object);
    }
}
