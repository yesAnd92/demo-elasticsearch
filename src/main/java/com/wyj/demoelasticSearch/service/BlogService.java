package com.wyj.demoelasticSearch.service;

import com.wyj.demoelasticSearch.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Page<Blog> getBlogList(String title, String content, Pageable pageable);

    Blog findOne(String id);
}
