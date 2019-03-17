package com.fastRPC.netty;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.fastRPC.exception.ConnectionTimeoutException;
import com.fastRPC.exception.InvokeModuleException;
import com.fastRPC.model.MessageRequest;
import com.fastRPC.model.MessageResponse;


public class MessageCallBack {

    private MessageRequest request;
    private MessageResponse response;
    private Lock lock = new ReentrantLock();
    private Condition finish = lock.newCondition();

    public MessageCallBack(MessageRequest request) {
        this.request = request;
    }

    public Object start() throws Exception {
        try {
            lock.lock();
            await();
            if (this.response != null) {
                boolean isInvokeSucc = getInvokeResult();
                if (isInvokeSucc) {
                    if (this.response.getError() == null || this.response.getError().isEmpty()) {
                        return this.response.getResult();
                    } else {
                        throw new InvokeModuleException(this.response.getError());
                    }
                }
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    private boolean getInvokeResult() {
        if(response != null){
            return true;
        }
        return false;
    }

    public void over(MessageResponse reponse) {
        try {
            lock.lock();
            finish.signal();
            this.response = reponse;
        } finally {
            lock.unlock();
        }
    }

    private void await() throws Exception {
        if (response == null) {
            boolean timeout = false;
            try {
                timeout = finish.await(10000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!timeout) {
                throw new ConnectionTimeoutException("connection time out!!");
            }
        }
    }

}
