package com.wyj.demoelasticSearch;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchAPITest {

    @Autowired
    private TransportClient client;


    /**
     * search
     * @user wangyj
     * @date 2017/9/22 15:57
     */
    @Test
    public void searchApi(){
        SearchResponse response= client.prepareSearch("informations")
                .setTypes("blog")
                .setQuery(QueryBuilders.matchPhraseQuery("title","北京"))
                .execute()
                .actionGet();
        for (SearchHit hit :response.getHits()){
            System.out.println(hit.getSourceAsString());
        }
    }


    /**
     * scrolls
     * 是先做一次初始化搜索把所有符合搜索条件的结果缓存起来生成一个快照，然后持续地、批量地从快照里拉取数据直到没有数据剩下
     * @user wangyj
     * @date 2017/9/22 16:53
     */
    @Test
    public void scrolls(){
        SearchResponse scrollResp=client.prepareSearch("informations")
                .setTypes("blog")
                .setScroll(new TimeValue(30000))//设置
                .setQuery(QueryBuilders.matchPhraseQuery("title","北京"))
                .setSize(5)  //每个分片上将返回100条数据
                .execute()
                .actionGet();

        //在hint全部获取之前一直scroll
        while (true){
            for (SearchHit hit: scrollResp.getHits()){
                System.out.println(hit.getSourceAsString());
            }

            System.out.println("I am a cut-off rule-------");
            scrollResp=client.prepareSearchScroll(scrollResp.getScrollId())
                    .setScroll(new TimeValue(60000))
                    .execute().actionGet();

            //hint返回完毕结束
            if(scrollResp.getHits().getHits().length==0){
                break;
            }
        }
    }


    /**
     * 通过MultiSearchResponse可以分别获取各自的请求响应
     * @user wangyj 
     * @date 2017/9/25 11:29
     */
    @Test
    public void multiSearch(){
        SearchRequestBuilder srb1=client
                .prepareSearch().setQuery(QueryBuilders.queryStringQuery("天津")).setSize(1);

        SearchRequestBuilder srb2=client
                .prepareSearch().setQuery(QueryBuilders.matchQuery("title","北京")).setSize(1);

        MultiSearchResponse response = client.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .execute().actionGet();

        for (MultiSearchResponse.Item item :response.getResponses()){
            SearchResponse searchResponse = item.getResponse();
            System.out.println(searchResponse.getHits().totalHits());
        }
    }



}
