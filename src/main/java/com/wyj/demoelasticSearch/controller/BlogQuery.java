package com.wyj.demoelasticSearch.controller;


import com.wyj.demoelasticSearch.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogQuery {

    @Autowired
    private BlogService blogService;

    @GetMapping("/blog")
    public void getBlogList(){

        blogService.getBlog();
    }




}
