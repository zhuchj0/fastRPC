package com.fastRPC.serialization;

import java.io.InputStream;
import java.io.OutputStream;

public interface RpcSerialize {

    public void serialize(OutputStream outputStream, Object object) throws Exception;

    public Object deserialize(InputStream inputStream) throws Exception;
}
