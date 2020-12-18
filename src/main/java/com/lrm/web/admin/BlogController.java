package com.lrm.web.admin;

import com.lrm.po.Blog;
import com.lrm.po.Tag;
import com.lrm.po.Type;
import com.lrm.po.User;
import com.lrm.service.BlogService;
import com.lrm.service.TagService;
import com.lrm.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "/admin/blogs-input";

    private static final String LIST = "/admin/blogs";

    private static final String REDITECT_LIST = "redirect: /admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Blog blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Blog blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        model.addAttribute("types", typeService.listType());
        return "/admin/blogs";
    }

    @GetMapping({"/blogs/input","/blogs-input"})
    public String input(Model model) {
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b = blogService.saveBlog(blog);
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDITECT_LIST;
    }

    @PostMapping("/blogs/update")
    public String postupdate(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b = blogService.updateBlog(blog.getId(), blog);
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDITECT_LIST;
    }

    @GetMapping("/blogs/{id}/input")
    public String updateBlog(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlog(id);
        model.addAttribute("blog", blog);
        Type blogType = blog.getType();
        model.addAttribute("blogType", blogType);
        String blogTags = blog.getTags().stream().map((item)->(item.getName())).reduce((iter, item)->(iter + "," + item)).get();
        model.addAttribute("blogTags", blogTags);
        List<Type> typeList = typeService.listType();
        model.addAttribute("types", typeList);
        List<Tag> tagList = tagService.listTag();
        model.addAttribute("tags", tagList);
        return "/admin/blogs-update";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return "redirect: /admin/blogs";
    }


}
