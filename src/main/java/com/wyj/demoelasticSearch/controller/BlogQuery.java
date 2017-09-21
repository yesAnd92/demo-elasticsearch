package com.wyj.demoelasticSearch.controller;


import com.wyj.demoelasticSearch.domain.Blog;
import com.wyj.demoelasticSearch.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogQuery {

    @Autowired
    private BlogService blogService;

    @GetMapping("/blog")
    public Page<Blog> getBlogList(String title, String content, Pageable pageable){
        Page<Blog> blogList=blogService.getBlogList(title,content,pageable);
        return blogList;
    }

    @GetMapping("/blog/{id}")
    public Blog getOne(@PathVariable String id){
        Blog blog= blogService.findOne(id);
        return blog;
    }



}
