package com.fastRPC.proxy;

import com.fastRPC.annotation.FastRpcConsumerClass;
import com.fastRPC.annotation.FastRpcRegisterableClass;
import com.fastRPC.annotation.FastRpcRegisterableMethod;
import com.fastRPC.client.handler.RpcClientHandler;
import com.fastRPC.client.zookeeper.NamingManager;
import com.fastRPC.exception.InvokeModuleException;
import com.fastRPC.model.MessageRequest;
import com.fastRPC.netty.RpcServerConnectionFactory;
import com.fastRPC.netty.RpcServerLoader;
import com.fastRPC.utils.ObjectUtil;
import org.assertj.core.internal.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MyInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyInvocationHandler.class);

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        MessageRequest request = new MessageRequest();
        String aliases = "";
        Class<?> iface[] = proxy.getClass().getInterfaces();
        for(Class<?> clz : iface){
            if(clz.isAnnotationPresent(FastRpcConsumerClass.class)
                    && method.isAnnotationPresent(FastRpcRegisterableMethod.class)){
                FastRpcConsumerClass anno = clz.getAnnotation(FastRpcConsumerClass.class);
                aliases = anno.aliases();
                if("SelfClassName".equals(aliases)){
                    aliases = clz.getSimpleName();
                }
            }
        }

        HashMap map = NamingManager.findInvokeInfo(aliases);
        if(ObjectUtil.isEmptyOrNull(map,aliases)){
            logger.error("Con't find  any implements in zookeeper");
            return null;
        }
        request.setClassName(String.valueOf(map.get("serviceImpl")));
        request.setMethodName(method.getName());
        request.setMessageId(new Date().toString());
        if (args != null && args.length > 0) {
            String[] classes = new String[args.length];

            for (int i = 0; i < args.length; ++i) {
                classes[i] = args[i].getClass().getName();
            }

            request.setTypeParameters(classes);
            request.setParameters(args);
        }
        List addrs = (List) map.get("addrs");
        if (ObjectUtil.isEmptyOrNull(addrs)){
            logger.error("Con't find  any implements address in zookeeper");
            return null;
        }else {
            //简单的负载均衡
            int index = ((int) (new Random().nextFloat() * addrs.size() ));
            logger.info("called : {}",addrs.get(index));
            RpcServerLoader serverLoader =  RpcServerConnectionFactory.getRpcConnectionInstance(addrs.get(index));
            RpcClientHandler handler = (RpcClientHandler) serverLoader.getMessageSendHandler();
            if(serverLoader.getConnStatus()){
                return handler.sendRequest(request).start();
            }
            return null;
        }
    }
}
