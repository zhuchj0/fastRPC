package com.fastRPC.serialization.jdkserialize;

import com.fastRPC.serialization.MessageCodecUtil;
import com.fastRPC.serialization.MessageEncoder;

public class JdkEncoder extends MessageEncoder {
    public JdkEncoder(MessageCodecUtil messageCodec) {
        super(messageCodec);
    }
}
