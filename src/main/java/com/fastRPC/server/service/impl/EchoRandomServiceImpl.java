package com.fastRPC.server.service.impl;


import com.fastRPC.server.service.EchoRandomService;

public class EchoRandomServiceImpl implements EchoRandomService {
    @Override
    public String echo() {
        return String.valueOf(Math.random());
    }
}
