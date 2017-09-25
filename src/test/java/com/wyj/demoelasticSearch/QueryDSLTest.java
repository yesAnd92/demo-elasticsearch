package com.wyj.demoelasticSearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryDSLTest {

    @Autowired
    private TransportClient client;


    /**
     * MatchAllQuery  匹配所有
     * @user wangyj
     * @date 2017/9/25 15:04
     */
    @Test
    public void matchAllQuery(){
        SearchResponse sr =client.prepareSearch()
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();

        System.out.println(sr.getHits().getTotalHits());
    }


    /**
     * MatchQuery 单字段匹配查询
     * @user wangyj
     * @date 2017/9/25 15:05
     */
    @Test
    public void matchQuery(){
        SearchResponse sr =client.prepareSearch("informations")
                .setQuery(QueryBuilders.matchQuery("title","上海")).execute().actionGet();
        System.out.println("total:"+sr.getHits().getTotalHits());
        System.out.println("time took:"+sr.getTook());
    }

    /**
     * multiMatchQuery 一个关键词匹配多个字段
     * @user wangyj 
     * @date 2017/9/25 15:08
     */
    @Test
    public void multiMatchQuery(){
        SearchResponse sr =client.prepareSearch("informations")
                .setQuery(QueryBuilders.multiMatchQuery("上海","title","content")).execute().actionGet();
        System.out.println(sr.getHits().getTotalHits());
    }


    /**
     * commonTermsQuery 分词时会将关键字的stopwords--无用词分离出来（比如 the\a\is ...）,以提高检索效率
     * @user wangyj
     * @date 2017/9/25 15:28
     */
    @Test
    public void commonTermsQuery(){

        SearchResponse sr = client.prepareSearch("informations")
                .setQuery(QueryBuilders.commonTermsQuery("title","上海")).execute().actionGet();

        System.out.println("total:"+sr.getHits().getTotalHits());
        System.out.println("time took:"+sr.getTook());
        //可以对比matchQuery的搜索进行对比
    }


}
