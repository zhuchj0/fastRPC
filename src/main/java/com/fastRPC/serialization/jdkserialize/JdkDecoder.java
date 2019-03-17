package com.fastRPC.serialization.jdkserialize;

import com.fastRPC.serialization.MessageCodecUtil;
import com.fastRPC.serialization.MessageDecoder;

public class JdkDecoder extends MessageDecoder {
    public JdkDecoder(MessageCodecUtil messageCodec) {
        super(messageCodec);
    }
}
