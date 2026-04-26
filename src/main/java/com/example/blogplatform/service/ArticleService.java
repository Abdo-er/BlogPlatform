package com.example.blogplatform.service;

import com.example.blogplatform.model.Article;
import com.example.blogplatform.model.Category;
import com.example.blogplatform.model.User;
import com.example.blogplatform.repository.ArticleRepository;
import com.example.blogplatform.repository.CategoryRepository;
import com.example.blogplatform.service.validation.ContentValidator;
import com.example.blogplatform.service.validation.ValidationResult;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ArticleService {

    private static final Logger logger = Logger.getLogger(ArticleService.class.getName());

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ContentValidator contentValidator;

    /**
     * يتم استدعاؤها بعد إنشاء الخدمة للتأكد من وجود أقسام افتراضية في قاعدة
     * البيانات
     */
    @PostConstruct
    public void ensureDefaultCategories() {
        if (categoryRepository.count() == 0) {
            logger.info("⚠️ لا توجد أقسام في قاعدة البيانات. سيتم إنشاء أقسام افتراضية...");
            categoryRepository.save(new Category("أدب", "كتابات أدبية ونصوص إبداعية"));
            categoryRepository.save(new Category("أعمال", "مقالات عن ريادة الأعمال والإدارة"));
            categoryRepository.save(new Category("تعلم", "دروس ومهارات معرفية"));
            categoryRepository.save(new Category("سفر", "تجارب السفر والمغامرات"));
            categoryRepository.save(new Category("مهارات", "تطوير الذات والمهارات الحياتية"));
            logger.info("✅ تم إنشاء 5 أقسام افتراضية بنجاح.");
        } else {
            logger.info("✅ الأقسام موجودة مسبقاً، عددها: " + categoryRepository.count());
        }
    }

    @Transactional
    public ValidationResult createArticle(Article article, User author, Long categoryId) {
        // التحقق من صحة المحتوى
        ValidationResult validationResult = contentValidator.validate(article.getTitle(), article.getContent());
        if (!validationResult.isValid()) {
            return validationResult;
        }

        // البحث عن القسم
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("القسم غير موجود بالرقم: " + categoryId +
                        ". الرجاء التأكد من اختيار قسم صحيح."));

        // تعيين بيانات المقالة
        article.setAuthor(author);
        article.setCategory(category);
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        article.setValidated(true);

        // حفظ المقالة
        articleRepository.save(article);

        // إعداد نتيجة النجاح
        validationResult.setValid(true);
        validationResult.setMessages(new ArrayList<>()); // مسح رسائل الخطأ القديمة
        validationResult.addMessage("تم نشر المقالة بنجاح!");
        return validationResult;
    }

    @Transactional(readOnly = true)
    public List<Article> getLatestArticles(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return articleRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional(readOnly = true)
    public List<Article> getArticlesByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("القسم غير موجود بالرقم: " + categoryId));
        return articleRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Article> searchArticles(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return articleRepository.search(keyword);
    }

    @Transactional(readOnly = true)
    public List<Article> getArticlesByUser(User user) {
        return articleRepository.findByAuthor(user);
    }

}