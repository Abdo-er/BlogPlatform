package com.example.blogplatform.controller;

import com.example.blogplatform.model.Article;
import com.example.blogplatform.model.Category;
import com.example.blogplatform.service.ArticleService;
import com.example.blogplatform.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * وحدة التحكم المسؤولة عن الصفحة الرئيسية.
 * تعرض أحدث المقالات وجميع الأقسام المتاحة للمستخدم.
 */
@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService; // خدمة المقالات لجلب أحدث المقالات

    @Autowired
    private CategoryService categoryService; // خدمة الأقسام لجلب جميع الأقسام

    /**
     * عرض الصفحة الرئيسية.
     * تقوم بإضافة قائمة بأحدث 10 مقالات وقائمة بجميع الأقسام إلى النموذج،
     * ثم عرض الصفحة الرئيسية (index.html).
     * 
     * @param model نموذج لنقل البيانات إلى العرض
     * @return اسم صفحة العرض (index)
     */
    @GetMapping("/")
    public String home(Model model) {
        // جلب أحدث 10 مقالات (باستخدام خدمة المقالات)
        List<Article> latestArticles = articleService.getLatestArticles(10);

        // جلب جميع الأقسام لعرضها كأزرار فلترة
        List<Category> categories = categoryService.findAll();

        // إضافة البيانات إلى النموذج ليتم استخدامها في الصفحة
        model.addAttribute("articles", latestArticles);
        model.addAttribute("categories", categories);

        // إرجاع اسم القالب (index.html)
        return "index";
    }
}