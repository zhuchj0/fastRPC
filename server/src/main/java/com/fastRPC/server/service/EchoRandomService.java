package com.fastRPC.server.service;

import com.fastRPC.annotation.FastRpcRegisterableClass;
import com.fastRPC.annotation.FastRpcRegisterableMethod;


@FastRpcRegisterableClass(defaultImpl = "com.fastRPC.server.service.impl.EchoRandomServiceImpl")
public interface EchoRandomService {
    @FastRpcRegisterableMethod
    String echo();
}
