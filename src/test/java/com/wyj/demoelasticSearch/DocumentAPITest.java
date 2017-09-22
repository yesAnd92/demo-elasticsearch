package com.wyj.demoelasticSearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentAPITest {

    @Autowired
    private TransportClient client;


    /**
     * 增加一个索引
     * @user wangyj
     * @date 2017/9/22 11:01
     */
    @Test
    public void indexBlog() throws JsonProcessingException {
        Map<String,Object> blog = new HashMap<>();
        blog.put("title","wangyj");
        blog.put("content","balllla...........");
        blog.put("site","wangyj.me");
        blog.put("keywords","仲裁 南海");
        blog.put("pubtime","2016-07-18T02:10:35.000+08:00");
        ObjectMapper mapper = new ObjectMapper();
        byte[] json =mapper.writeValueAsBytes(blog);
        IndexResponse response= client.prepareIndex("informations","blog","9527").setSource(json).get();
        System.out.println(response.isCreated());
    }


    /**
     * 获取一个索引
     * @user wangyj
     * @date 2017/9/22 10:29
     * @param
     * @return
     */
    @Test
    public void getBlog(){

        GetResponse response = client.prepareGet("informations","blog","9527").get();
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


    /**
     * 删除一个索引
     * @user wangyj
     * @date 2017/9/22 11:05
     */
    @Test
    public void deleteBlog(){
        DeleteResponse response = client.prepareDelete("informations","blog","9527").get();
        System.out.println(response.isFound());
    }



    @Test
    public void updateBlog() throws Exception {
        Map<String,Object> blog = new HashMap<>();
        blog.put("title","wangyj");
        blog.put("content","I'm a content...........");
        blog.put("site","wangyj.me");
        blog.put("keywords","仲裁 南海");
        blog.put("pubtime","2016-07-18T02:10:35.000+08:00");
        ObjectMapper mapper = new ObjectMapper();
        byte[] json =mapper.writeValueAsBytes(blog);
        UpdateRequest updateRequest= new UpdateRequest("informations","blog","9527").doc(json);
        client.update(updateRequest).get();
    }
}
