

package com.fastRPC.client.zookeeper;

import com.fastRPC.zookeeper.BaseZookeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.fastRPC.zookeeper.BaseZookeeperFactory;
import org.I0Itec.zkclient.ZkClient;

public class ClientZookeeper implements BaseZookeeper {

    private ZkClient zkClient;
    private NamingManager manager;

    public ClientZookeeper() {
        zkClient = BaseZookeeperFactory.getInstance("client");
        getNamingServices();
        subscribe();
    }

    public ClientZookeeper(NamingManager manager) {
        this.manager = manager;
        zkClient = BaseZookeeperFactory.getInstance("client");
        getNamingServices();
        manager.refresh(this);
        subscribe();
    }

    public void subscribe() {
        for (HashMap map : manager.getCache()) {
            String key = String.valueOf(map.keySet().toArray()[0]);
            this.zkClient.subscribeChildChanges("/services/" + map.get(key), (s, list) -> {
                manager.refresh(this);
            });
        }
    }

    public List getNamingServices() {
        ArrayList<HashMap> result = new ArrayList<>();

        List<String> list = this.zkClient.getChildren("/services");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String service = (String) iterator.next();
            HashMap<String, Object> tmpCache = new HashMap();

            String impl = this.zkClient.readData("/services/"+service);//服务实现类
            List<String> addrs = this.zkClient.getChildren("/services/" + service + "");
            tmpCache.put("aliases", service);//服务别名
            tmpCache.put("serviceImpl", impl);//服务实现类
            tmpCache.put("addrs", addrs);

            result.add(tmpCache);
        }
        return result;
    }


}
