package com.fastRPC.server.service;


import org.springframework.stereotype.Component;

@Component
public class EchoRandomServiceImpl implements EchoRandomService {
    @Override
    public String echo() {
        return String.valueOf(Math.random());
    }
}
