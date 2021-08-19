package com.fastRPC.exception;

public class ConnectionTimeoutException extends Exception {
    public ConnectionTimeoutException() {
        super();
    }

    public ConnectionTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionTimeoutException(String message) {
        super(message);
    }

    public ConnectionTimeoutException(Throwable cause) {
        super(cause);
    }
}
