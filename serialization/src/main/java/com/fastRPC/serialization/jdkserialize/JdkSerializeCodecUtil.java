package com.fastRPC.serialization.jdkserialize;

import com.fastRPC.serialization.MessageCodecUtil;
import io.netty.buffer.ByteBuf;

import java.io.*;

public class JdkSerializeCodecUtil implements MessageCodecUtil {
    @Override
    public void encode(ByteBuf out, Object message) throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(ba);
        oos.writeObject(message);
        byte[] body = ba.toByteArray();
        out.writeInt(body.length);
        out.writeBytes(body);
    }

    @Override
    public Object decode(byte[] body) throws IOException {
        ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(body));
        Object object = null;
        try {
            object = oi.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
