package com.fastRPC.server.service;

import org.springframework.stereotype.Component;

@Component
 interface EchoRandomService {
    public String echo();
}
