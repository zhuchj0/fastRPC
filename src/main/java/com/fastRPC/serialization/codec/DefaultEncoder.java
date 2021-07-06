package com.fastRPC.serialization.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultEncoder extends MessageToByteEncoder {
    Logger logger = Logger.getLogger(DefaultEncoder.class.getName());

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        logger.log(Level.FINE,"in encode" + o.getClass() + "size " + byteBuf.readableBytes());
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(ba);
        oos.writeObject(o);
        byte[] body = ba.toByteArray();
        byteBuf.writeInt(body.length);
        byteBuf.writeBytes(body);
    }
}
