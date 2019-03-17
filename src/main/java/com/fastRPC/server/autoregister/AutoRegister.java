package com.fastRPC.server.autoregister;

import com.fastRPC.annotation.FastRpcRegisterableClass;


import java.net.InetAddress;
import java.util.*;

import com.fastRPC.utils.ClassUtil;
import com.fastRPC.utils.PropertyUtil;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AutoRegister {
    private static final Logger logger = LoggerFactory.getLogger(AutoRegister.class);


    public AutoRegister()  {
    }

    public static void startRegister(ServerZookeeper zookeeper) throws Exception {
        HashMap<String,String> services = new HashMap<>();
        List<Class<?>> list = ClassUtil.getAllClassesFromPackage(PropertyUtil.getProp("FastRpcScanScope"));
        for(Class clz :list){
            String aliases;
            String defaultImpl;
            if (clz.isInterface() && clz.isAnnotationPresent(FastRpcRegisterableClass.class)){
                FastRpcRegisterableClass anno = (FastRpcRegisterableClass) clz.getAnnotation(FastRpcRegisterableClass.class);
                aliases = anno.aliases();
                defaultImpl = anno.defaultImpl();
                if("SelfClassName".equals(aliases)){
                    aliases = clz.getSimpleName();
                }

                if (services.containsKey(aliases)){
                    logger .warn("services aliases:{} has been  registered ",aliases);
                }else {
                    services.put(aliases,defaultImpl);
                }
            }
        }

        for(Object key :services.keySet()){
            doRegister(String.valueOf(key),services.get(key),zookeeper);
        }
    }

    public static void doRegister(String name,String defatltImpl,ServerZookeeper zookeeper) throws Exception {
        ZkClient cli = zookeeper.getZkClient();
        if (!cli.exists("/services")) {
            cli.create("/services", (Object)null, CreateMode.PERSISTENT);
        }

        if (!cli.exists("/services/" + name) ) {
            cli.create("/services/" + name, (Object)null, CreateMode.PERSISTENT);
        }
        assert defatltImpl != null;

        if(cli.readData("/services/" + name) == null || !defatltImpl.equals(cli.readData("/services/" + name))) {
            cli.writeData("/services/" + name, defatltImpl);
        }
        //获取自己的ip
        String address = PropertyUtil.getProp("localhost") == null ? InetAddress.getLocalHost().getHostAddress() : PropertyUtil.getProp("localhost");
        String port = PropertyUtil.getProp("port") == null ? "9000": PropertyUtil.getProp("port");
        cli.create("/services/" + name + "/" + address+":"+port,  address+":"+port, CreateMode.EPHEMERAL);
    }

}
