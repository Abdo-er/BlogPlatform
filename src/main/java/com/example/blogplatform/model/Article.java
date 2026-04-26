package com.example.blogplatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * كيان المقالة (Article Entity).
 * يمثل مقالة مكتوبة بواسطة مستخدم وتنتمي إلى قسم معين.
 */
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "العنوان مطلوب")
    @Size(min = 5, max = 200, message = "العنوان يجب أن يكون بين 5 و 200 حرف")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "المحتوى مطلوب")
    @Size(min = 200, message = "المحتوى يجب أن يكون على الأقل 200 حرف لضمان العمق") // تحقق أساسي
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    /**
     * علاقة Many-to-One مع المستخدم (المؤلف).
     * fetch = FetchType.LAZY لتحسين الأداء (يتم تحميل المؤلف فقط عند الحاجة).
     * JoinColumn يحدد اسم عمود المفتاح الأجنبي في جدول articles.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    /**
     * علاقة Many-to-One مع القسم (Category).
     * كل مقالة تنتمي إلى قسم واحد.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * حقل للإشارة إلى أن المقالة اجتازت التحقق الإضافي (مثل فحص الجودة).
     * يمكن استخدامه لاحقاً لعرض المقالات الموثقة فقط أو لإدارة المحتوى.
     */
    @Column(name = "validated")
    private boolean validated;

    // Constructors
    public Article() {
    }

    public Article(String title, String content, User author, Category category) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}