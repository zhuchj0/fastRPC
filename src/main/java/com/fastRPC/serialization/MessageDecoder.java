package com.fastRPC.serialization;

import com.fastRPC.model.MessageRequest;
import com.fastRPC.model.MessageResponse;
import com.fastRPC.serialization.jdkserialize.JdkSerializeCodecUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageDecoder extends ByteToMessageDecoder {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MessageDecoder.class);
    private MessageCodecUtil messageCodec ;

    public final static int MESSAGE_LENGTH = MessageCodecUtil.MESSAGE_LENGTH;

    public MessageDecoder(MessageCodecUtil messageCodec) {
        this.messageCodec = messageCodec;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) {
        //没有读取完就返回
        if (in.readableBytes() < MessageDecoder.MESSAGE_LENGTH) {
            return;
        }
        //标记
        in.markReaderIndex();
        int messageLength = in.readInt();

        if (messageLength < 0) {
            ctx.close();
        }

        if (in.readableBytes() < messageLength) {
            in.resetReaderIndex();
            return;
            //前面都是无法读取完整数据的情况
        } else {
            byte[] messageBody = new byte[messageLength];
            in.readBytes(messageBody);

            try {
                logger.info("Decoder: {}",messageCodec.getClass().getSimpleName());
                Object obj = messageCodec.decode(messageBody);
                list.add(obj);
            } catch (IOException ex) {
                logger.error("decode error");
            }
        }
    }
}
