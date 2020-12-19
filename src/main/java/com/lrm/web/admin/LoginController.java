package com.lrm.web.admin;

import com.lrm.po.User;
import com.lrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping({"/", "/login.html"})
    public String loginPage() {
        return "/admin/login.html";
    }

    @GetMapping("/index.html")
    public String index(Model model) {
        model.addAttribute("user", userService.getUser());
        return "/admin/index.html";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (null != user) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "redirect:/admin/index.html";
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin/login.html";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin/";
    }

}
