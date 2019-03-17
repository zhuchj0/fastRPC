package com.fastRPC.client.service;

import com.fastRPC.annotation.FastRpcConsumerClass;
import com.fastRPC.annotation.FastRpcRegisterableClass;
import com.fastRPC.annotation.FastRpcRegisterableMethod;


@FastRpcConsumerClass
public interface EchoRandomService {
    @FastRpcRegisterableMethod
    public String echo();
}
