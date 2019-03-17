package com.fastRPC.server.service;

import com.fastRPC.annotation.FastRpcRegisterableClass;
import com.fastRPC.annotation.FastRpcRegisterableMethod;

@FastRpcRegisterableClass(defaultImpl = "com.fastRPC.server.service.impl.Echo2Impl")
public interface Echo2 {
    @FastRpcRegisterableMethod
    public String echotry();
}
