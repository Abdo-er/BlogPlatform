package com.example.blogplatform.controller;

import com.example.blogplatform.model.Article;
import com.example.blogplatform.model.Category;
import com.example.blogplatform.model.User;
import com.example.blogplatform.service.ArticleService;
import com.example.blogplatform.service.CategoryService;
import com.example.blogplatform.service.UserService;
import com.example.blogplatform.service.validation.ValidationResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * وحدة التحكم المسؤولة عن إدارة المقالات.
 * تشمل إنشاء مقالة جديدة، عرض المقالات حسب القسم، وعرض تفاصيل مقالة محددة.
 */
@Controller
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * عرض نموذج إنشاء مقالة جديدة.
     * 
     * @param model نموذج لإضافة البيانات إلى الصفحة
     * @return اسم صفحة إنشاء المقالة (create-article.html)
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        // إضافة كائن Article جديد للنموذج
        model.addAttribute("article", new Article());
        // إضافة قائمة الأقسام لاختيار القسم المناسب
        model.addAttribute("categories", categoryService.findAll());
        return "create-article";
    }

    /**
     * معالجة طلب إنشاء مقالة جديدة.
     * تقوم بالتحقق من صحة البيانات الأساسية باستخدام Bean Validation،
     * ثم تستخدم خدمة التحقق المخصصة للتأكد من عمق المحتوى وجودته،
     * ثم حفظ المقالة في قاعدة البيانات.
     * 
     * @param article            كائن المقالة المحمّل من النموذج
     * @param result             نتائج التحقق من صحة البيانات
     * @param categoryId         معرف القسم المختار
     * @param currentUser        المستخدم الحالي (الكاتب)
     * @param redirectAttributes لإضافة رسائل بعد إعادة التوجيه
     * @param model              نموذج لإعادة عرض البيانات في حالة وجود أخطاء
     * @return إعادة توجيه إلى الصفحة الرئيسية أو العودة إلى نموذج الإنشاء
     */
    @PostMapping("/create") // تم التصحيح: متوافق مع نموذج HTML
    public String createArticle(@Valid @ModelAttribute("article") Article article,
            BindingResult result,
            @RequestParam("categoryId") Long categoryId,
            @AuthenticationPrincipal UserDetails currentUser,
            RedirectAttributes redirectAttributes,
            Model model) {

        // التحقق من صحة الحقول الأساسية (العنوان والمحتوى) باستخدام Bean Validation
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "create-article";
        }

        // جلب المستخدم الحالي من قاعدة البيانات باستخدام اسم المستخدم
        User author = userService.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("المستخدم غير موجود"));

        // استخدام خدمة التحقق المخصصة للتأكد من جودة المحتوى وعمقه
        ValidationResult validationResult = articleService.createArticle(article, author, categoryId);

        // إذا فشل التحقق المخصص، نعرض الأخطاء للمستخدم ونعود إلى نموذج الإنشاء
        if (!validationResult.isValid()) {
            redirectAttributes.addFlashAttribute("errorMessages", validationResult.getMessages());
            return "redirect:/articles/create";
        }

        // إذا نجح التحقق، نضيف رسالة نجاح ونعيد التوجيه إلى الصفحة الرئيسية
        redirectAttributes.addFlashAttribute("successMessage", "تم نشر المقالة بنجاح!");
        return "redirect:/";
    }

    /**
     * عرض جميع المقالات في قسم معين.
     * 
     * @param categoryId معرف القسم
     * @param model      نموذج لإضافة البيانات
     * @return صفحة عرض مقالات القسم (category-articles.html)
     */
    @GetMapping("/category/{categoryId}")
    public String articlesByCategory(@PathVariable Long categoryId, Model model) {
        // جلب المقالات الخاصة بالقسم
        List<Article> articles = articleService.getArticlesByCategory(categoryId);

        // تم التصحيح: استخدام categoryId المرسل من المسار بدلاً من القيمة الثابتة 1L
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("القسم غير موجود: " + categoryId));

        model.addAttribute("articles", articles);
        model.addAttribute("categoryName", category.getName());
        return "category-articles";
    }

    /**
     * عرض تفاصيل مقالة محددة.
     * 
     * @param id    معرف المقالة
     * @param model نموذج لإضافة البيانات
     * @return صفحة تفاصيل المقالة (article-details.html) أو إعادة توجيه إذا لم توجد
     *         المقالة
     */
    @GetMapping("/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            // إذا لم توجد المقالة، نعيد التوجيه إلى الصفحة الرئيسية
            return "redirect:/";
        }
        model.addAttribute("article", article);
        return "article-details";
    }

    @GetMapping("/my-articles")
    public String myArticles(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        User user = userService.findByUsername(currentUser.getUsername()).orElseThrow();
        List<Article> articles = articleService.getArticlesByUser(user); // يجب إضافة هذه الدالة في ArticleService
        model.addAttribute("articles", articles);
        return "my-articles";
    }
}