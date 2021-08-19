

package com.fastRPC.server.autoregister;

import com.fastRPC.zookeeper.BaseZookeeper;
import com.fastRPC.zookeeper.BaseZookeeperFactory;
import org.I0Itec.zkclient.ZkClient;

public class ServerZookeeper implements BaseZookeeper{

    private ZkClient zkClient;

    public ServerZookeeper() {
        zkClient = BaseZookeeperFactory.getInstance("server");
        try {
            AutoRegister.startRegister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ZkClient getZkClient() {
        if(null != zkClient) {
            return zkClient;
        }else {
            return BaseZookeeperFactory.getInstance("server");
        }
    }


}
