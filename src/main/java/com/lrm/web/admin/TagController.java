package com.lrm.web.admin;
import com.lrm.po.Tag;
import com.lrm.service.TagService;
import com.lrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        model.addAttribute("user", userService.getUser());
        return "/admin/tags";
    }

    @GetMapping({"/tags/input", "/tags-input"})
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        model.addAttribute("user", userService.getUser());
        return "/admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        model.addAttribute("user", userService.getUser());
        return "/admin/tags-update";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes, Model model) {
        model.addAttribute("user", userService.getUser());
        Tag type1 = tagService.getTagByName(tag.getName());
        if (type1 != null){
            result.rejectValue("name","nameError", "不能重复添加分类");
        }
        if (result.hasErrors()) {
            return "/admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes, Model model) {
        model.addAttribute("user", userService.getUser());
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes, Model model) {
        model.addAttribute("user", userService.getUser());
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除失败");
        return "redirect:/admin/tags";
    }



}
