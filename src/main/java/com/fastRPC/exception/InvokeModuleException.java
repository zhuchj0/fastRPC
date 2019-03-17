package com.fastRPC.exception;

public class InvokeModuleException extends Exception {
    public InvokeModuleException() {
        super();
    }

    public InvokeModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokeModuleException(String message) {
        super(message);
    }

    public InvokeModuleException(Throwable cause) {
        super(cause);
    }
}
