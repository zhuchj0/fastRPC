package com.fastRPC.serialization.hessian;

import com.fastRPC.serialization.MessageCodecUtil;
import com.fastRPC.serialization.MessageEncoder;

public class HessianEncoder extends MessageEncoder {
    public HessianEncoder(MessageCodecUtil messageCodec) {
        super(messageCodec);
    }
}
