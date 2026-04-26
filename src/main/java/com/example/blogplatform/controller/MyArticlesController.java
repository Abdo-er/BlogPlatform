package com.example.blogplatform.controller;

import com.example.blogplatform.model.Article;
import com.example.blogplatform.model.User;
import com.example.blogplatform.service.ArticleService;
import com.example.blogplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/my-articles")
public class MyArticlesController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public String myArticles(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("المستخدم غير موجود"));

        List<Article> articles = articleService.getArticlesByUser(user);

        model.addAttribute("articles", articles);
        model.addAttribute("articlesCount", articles.size());

        return "my-articles";
    }
}