package com.wyj.demoelasticSearch.config;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Configuration
public class EsConfig {

    /**
     * 配置连接elasticsearch用的client
     * @user wangyj
     * @date 2017/9/21 17:40
     */
    @Bean
    public TransportClient getClient()throws Exception{
        //若集群的名字更改了（默认为elasticsearch），需要配置setting
        TransportClient client = TransportClient
                .builder()
                .build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
                //可以添加多个transportAddress
        return client;

    }
}
