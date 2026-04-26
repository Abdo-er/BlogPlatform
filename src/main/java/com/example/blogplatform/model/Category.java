package com.example.blogplatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * كيان القسم (Category Entity).
 * يمثل تصنيفاً أو قسماً للمقالات (مثل: أدب، أعمال، تعلم، سفر، مهارات).
 */
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "اسم القسم مطلوب")
    @Column(unique = true, nullable = false)
    private String name; // اسم القسم (مثلاً: "أدب", "أعمال", "تعلم", "مهارات", "سفر")

    @Column(length = 200)
    private String description; // وصف اختياري للقسم

    /**
     * علاقة One-to-Many مع المقالات.
     * القسم الواحد يمكن أن يحتوي على العديد من المقالات.
     * mappedBy = "category" يشير إلى أن الحقل المسؤول عن الربط في كيان Article هو
     * "category".
     * cascade = CascadeType.ALL يعني أن أي عملية على القسم تنعكس على مقالاته
     * (اختياري، قد لا نريد حذف المقالات عند حذف القسم).
     * لكن هنا نفضل عدم استخدام cascade على الحذف لحماية المقالات، لذا نكتفي بالـ
     * mappedBy فقط بدون cascade.
     * cascade يمكن ضبطه حسب الحاجة، لكني سأتركه بدون cascade لتفادي حذف مقالات عند
     * مسح قسم.
     */
    @OneToMany(mappedBy = "category")
    private List<Article> articles = new ArrayList<>();

    // Constructors
    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}