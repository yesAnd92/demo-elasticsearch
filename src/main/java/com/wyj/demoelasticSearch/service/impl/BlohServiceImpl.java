package com.wyj.demoelasticSearch.service.impl;


import com.wyj.demoelasticSearch.service.BlogService;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
public class BlohServiceImpl  implements BlogService{

    @Autowired
    private TransportClient client;


    @Override
    public void getBlog() {
        GetResponse response = client.prepareGet("informations","blog","14470115").get();
        Map<String, Object> rpMap = response.getSource();
        if (rpMap == null) {
            System.out.println("empty");
            return;
        }
        Iterator<Map.Entry<String, Object>> rpItor = rpMap.entrySet().iterator();
        while (rpItor.hasNext()) {
            Map.Entry<String, Object> rpEnt = rpItor.next();
            System.out.println(rpEnt.getKey() + " : " + rpEnt.getValue());
        }

    }
}
