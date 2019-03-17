package com.fastRPC.serialization.hessian;

import com.fastRPC.serialization.MessageCodecUtil;
import com.fastRPC.serialization.MessageDecoder;

public class HessianDecoder extends MessageDecoder {
    public HessianDecoder(MessageCodecUtil messageCodec) {
        super(messageCodec);
    }
}
