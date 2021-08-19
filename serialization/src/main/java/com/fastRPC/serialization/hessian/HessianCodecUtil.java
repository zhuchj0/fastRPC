package com.fastRPC.serialization.hessian;

import com.fastRPC.serialization.MessageCodecUtil;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class HessianCodecUtil implements MessageCodecUtil {
    @Override
    public void encode(ByteBuf out, Object message)  {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            //池化工厂生成
            HessianSerialize serialize = new HessianSerialize();

            serialize.serialize(bo, message);
            byte[] result = bo.toByteArray();
            int length = result.length;
            out.writeInt(length);
            out.writeBytes(result);
            //返还serializer

        }catch (Exception e){
            //close
        }
    }

    @Override
    public Object decode(byte[] bytes)  {
        Object result = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            //池化工厂生成
            HessianSerialize serialize = new HessianSerialize();

             result = serialize.deserialize(bis);

            //返还serializer
        }catch (Exception e){
//            close;
        }
        return result;
    }
}
