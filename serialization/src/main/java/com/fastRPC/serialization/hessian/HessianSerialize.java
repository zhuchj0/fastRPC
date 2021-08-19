package com.fastRPC.serialization.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.fastRPC.serialization.RpcSerialize;

import java.io.InputStream;
import java.io.OutputStream;

public class HessianSerialize implements RpcSerialize {


    @Override
    public void serialize(OutputStream outputStream, Object object) throws Exception {
        Hessian2Output hessian2Output = new Hessian2Output(outputStream);
        hessian2Output.startMessage();
        hessian2Output.writeObject(object);
        hessian2Output.completeMessage();
        hessian2Output.close();
        outputStream.close();
    }

    @Override
    public Object deserialize(InputStream inputStream) throws Exception {
        Object object = null;
        Hessian2Input hessian2Input = new Hessian2Input(inputStream);
        hessian2Input.startMessage();
        object = hessian2Input.readObject();
        hessian2Input.completeMessage();
        hessian2Input.close();
        inputStream.close();
        return object;
    }
}
