
package com.fastRPC.client.zookeeper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fastRPC.zookeeper.BaseZookeeperFactory;
import io.netty.channel.Channel;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamingManager {
    private static final Logger logger = LoggerFactory.getLogger(NamingManager.class);
    private static List<HashMap<String, List<String>>> zookeeperCache = new ArrayList<>();
    public static Channel channel= null;
    private static final NamingManager namingManager = new NamingManager();
    private NamingManager() {
    }

    public static NamingManager getManager(){
        return namingManager;
    }

    public void refresh(ClientZookeeper zkClient){
        logger.info("############# start refresh ################");
        this.zookeeperCache = zkClient.getNamingServices();
        System.out.println(this.toString());
        logger.info("############# refresh finished #############");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cache:");
        for (HashMap map :zookeeperCache) {
            sb.append(map.toString());
        }
        return sb.toString();
    }

    public List<HashMap<String, List<String>>> getCache() {
        return zookeeperCache;
    }

    public static  HashMap findInvokeInfo(String aliases){
        if(aliases != null) {
            for (HashMap map : zookeeperCache) {
                if (aliases.equals(map.get("aliases"))){
                    return map;
                }
            }
            return null;
        }
        return null;
    }
}
