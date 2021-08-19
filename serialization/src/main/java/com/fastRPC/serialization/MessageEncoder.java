package com.fastRPC.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageEncoder extends MessageToByteEncoder {
    private static final Logger logger = LoggerFactory.getLogger(MessageEncoder.class);
    private MessageCodecUtil messageCodec;

    public MessageEncoder(final MessageCodecUtil messageCodec) {
        this.messageCodec = messageCodec;
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        logger.info("Encoder: {}",messageCodec.getClass().getSimpleName());
        messageCodec.encode(byteBuf,o);
    }
}
