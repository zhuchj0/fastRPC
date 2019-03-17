//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fastRPC.zookeeper;

import com.fastRPC.utils.PropertyUtil;
import java.util.concurrent.CountDownLatch;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.zookeeper.Watcher;

public class BaseZookeeperFactory {
    private static  ZkClient serverZK;
    private static  ZkClient clientZK;
    private  String host;
    private  static final int SESSION_TIME_OUT = 3000;
    private  static final int CONNECT_TIME_OUT = 3000;


    private BaseZookeeperFactory() {

    }

    public static ZkClient getInstance(String type) {
        switch (type){
            case "client":
                if (clientZK == null) {
                    String host = PropertyUtil.getProp("zookeeper.server.url");
                    synchronized(ZkClient.class) {
                        if (clientZK == null) {
                            clientZK = new ZkClient(new ZkConnection(host, BaseZookeeperFactory.SESSION_TIME_OUT));
                        }
                    }
                }
                return clientZK;
            case "server":
                if (serverZK == null) {
                String host = PropertyUtil.getProp("zookeeper.server.url");
                synchronized(ZkClient.class) {
                    if (serverZK == null) {
                        serverZK = new ZkClient(new ZkConnection(host, BaseZookeeperFactory.SESSION_TIME_OUT));
                    }
                }
            }
                return serverZK;
        }
        return null;
    }


}
