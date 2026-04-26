package com.example.blogplatform.controller;

import com.example.blogplatform.model.Article;
import com.example.blogplatform.model.User;
import com.example.blogplatform.service.ArticleService;
import com.example.blogplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/{username}")
    public String profile(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("المستخدم غير موجود: " + username));

        List<Article> articles = articleService.getArticlesByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("articles", articles);
        model.addAttribute("articlesCount", articles.size());

        return "profile";
    }
}