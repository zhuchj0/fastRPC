package com.fastRPC.server.service.impl;

import com.fastRPC.server.service.Echo2;

public class Echo2Impl implements Echo2 {

    @Override
    public String echotry() {
        return "新方法" + Math.random();
    }
}
