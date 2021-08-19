package com.fastRPC.serialization;

import java.io.InputStream;
import java.io.OutputStream;

public interface RpcSerialize {

    void serialize(OutputStream outputStream, Object object) throws Exception;

    Object deserialize(InputStream inputStream) throws Exception;
}
