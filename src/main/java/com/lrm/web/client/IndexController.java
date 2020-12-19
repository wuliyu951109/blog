package com.lrm.web.client;

import com.lrm.po.Blog;
import com.lrm.po.Tag;
import com.lrm.po.Type;
import com.lrm.service.BlogService;
import com.lrm.service.TagService;
import com.lrm.service.TypeService;
import com.lrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.getTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "/static/index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query);
        return "/static/search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Long id, Model model) {
        model.addAttribute("blog", blogService.getBlog(id));
        blogService.updateBlogViewsById(id);
        return "/static/blog";
    }

    @GetMapping("/types")
    public String types(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("type", new Type());
        return "/static/types";
    }

    @GetMapping("/types/{id}")
    public String typeid(@PathVariable("id") Long id, Model model) {
        List<Type> types = typeService.listType();
        Type tp = null;
        for (Type type : types) {
            if (type.getId() == id) {
                tp = type;
                tp.setBlogs(blogService.getBlogsByTypeId(type.getId()));
            }
        }
        model.addAttribute("id", id);
        model.addAttribute("tp", tp);
        model.addAttribute("types", types);
        return "/static/types";
    }

    @GetMapping("/tags")
    public String tags(Model model) {
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("tag", new Tag());
        return "/static/tags";
    }

    @GetMapping("/tags/{id}")
    public String tagid(@PathVariable("id") Long id, Model model) {
        List<Tag> tags = tagService.listTag();
        Tag tg = null;
        for (Tag tag : tags) {
            if (tag.getId() == id) {
                tg = tag;
                tg.setBlogs(blogService.getBlogsByTagId(id));
            }
        }
        model.addAttribute("id", id);
        model.addAttribute("tags", tags);
        model.addAttribute("tg", tg);
        return "/static/tags";
    }

    @GetMapping("/archives")
    public String archives(Model model) {
        List<Blog> blogs = blogService.allBlogs();
        Map<Integer, List<Blog>> map = blogs.stream().collect(Collectors.groupingBy(Blog::createyear));
        model.addAttribute("blogs", blogs);
        model.addAttribute("blogsmaps", map);
        return "/static/archives";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("user", userService.getUser());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return "/static/about";
    }

}
