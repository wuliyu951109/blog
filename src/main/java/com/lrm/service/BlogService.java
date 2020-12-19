package com.lrm.service;

import com.lrm.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    List<Blog> allBlogs();

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, Blog blog);

    Page<Blog> listBlog(String query, Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    List<Blog> listBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    Page<Blog> listBlog(Pageable pageable);

    List<Blog> getBlogsByTypeId(Long id);

    List<Blog> getBlogsByTagId(Long id);

    void updateBlogViewsById(Long id);
}
