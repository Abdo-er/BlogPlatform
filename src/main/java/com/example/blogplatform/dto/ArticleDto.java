package com.example.blogplatform.dto;

import com.example.blogplatform.model.Article;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * كائن نقل بيانات المقالة (Article DTO).
 * يستخدم لعرض بيانات المقالة في الواجهات (الصفحة الرئيسية، صفحة القسم، صفحة
 * التفاصيل)
 * دون الحاجة لتمرير كيان Article بالكامل، مما يحسن الأداء والأمان.
 */
public class ArticleDto {

    private Long id;
    private String title;
    private String content; // يمكن أن يكون مختصراً أو كاملاً حسب الحاجة
    private String authorName; // اسم كاتب المقالة (بدلاً من كائن User الكامل)
    private String categoryName; // اسم القسم (بدلاً من كائن Category الكامل)
    private LocalDateTime createdAt;
    private String formattedDate; // تاريخ منسق للعرض

    // Constructor افتراضي
    public ArticleDto() {
    }

    /**
     * Constructor لتحويل كيان Article إلى ArticleDto.
     * 
     * @param article كيان المقالة
     */
    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.authorName = article.getAuthor() != null ? article.getAuthor().getUsername() : "غير معروف";
        this.categoryName = article.getCategory() != null ? article.getCategory().getName() : "غير مصنف";
        this.createdAt = article.getCreatedAt();
        // تنسيق التاريخ للعرض (مثال: 15 مارس 2025)
        if (article.getCreatedAt() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            this.formattedDate = article.getCreatedAt().format(formatter);
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    /**
     * الحصول على محتوى مختصر (للعرض في البطاقات).
     * 
     * @param maxLength الحد الأقصى لعدد الأحرف
     * @return نص مختصر
     */
    public String getShortContent(int maxLength) {
        if (content == null)
            return "";
        if (content.length() <= maxLength)
            return content;
        return content.substring(0, maxLength) + "...";
    }
}