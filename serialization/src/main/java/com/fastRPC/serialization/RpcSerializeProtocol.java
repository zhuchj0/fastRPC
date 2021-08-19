package com.fastRPC.serialization;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public enum RpcSerializeProtocol {
    JDKSERIALIZE("jdknative"), HESSIANSERIALIZE("hessian"), PROTOSTUFFSERIALIZE("protostuff");

    private String serializeProtocol;

    RpcSerializeProtocol(String serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
    }

    @Override
    public String toString() {
        ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
        return ReflectionToStringBuilder.toString(this);
    }

    public String getProtocol() {
        return serializeProtocol;
    }


}
