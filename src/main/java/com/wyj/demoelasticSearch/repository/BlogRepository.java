package com.wyj.demoelasticSearch.repository;


import com.wyj.demoelasticSearch.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlogRepository extends ElasticsearchCrudRepository<Blog,String>{
    Page<Blog> findByTitleOrContent(String title, String content, Pageable pageable);
}
