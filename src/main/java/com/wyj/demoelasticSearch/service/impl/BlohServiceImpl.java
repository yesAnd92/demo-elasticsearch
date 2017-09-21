package com.wyj.demoelasticSearch.service.impl;

import com.wyj.demoelasticSearch.domain.Blog;
import com.wyj.demoelasticSearch.repository.BlogRepository;
import com.wyj.demoelasticSearch.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


@Service
public class BlohServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Page<Blog> getBlogList(String title, String content, Pageable pageable) {
        return blogRepository.findByTitleOrContent(title,content,pageable);
    }

    @Override
    public Blog findOne(String id) {
        return blogRepository.findOne(id);
    }
}
