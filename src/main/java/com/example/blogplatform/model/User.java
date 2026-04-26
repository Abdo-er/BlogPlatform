package com.example.blogplatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * كيان المستخدم (User Entity).
 * يمثل مستخدم المنصة الذي يمكنه كتابة المقالات.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "اسم المستخدم مطلوب")
    @Size(min = 3, max = 50, message = "اسم المستخدم يجب أن يكون بين 3 و 50 حرفاً")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "البريد الإلكتروني مطلوب")
    @Email(message = "يرجى إدخال بريد إلكتروني صحيح")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "كلمة المرور مطلوبة")
    @Size(min = 6, message = "كلمة المرور يجب أن تكون على الأقل 6 أحرف")
    private String password;

    @Column(name = "full_name")
    private String fullName; // الاسم الكامل (اختياري)

    @Column(length = 500)
    private String bio; // نبذة عن الكاتب (اختياري)

    @Column(name = "created_at")
    private LocalDateTime createdAt; // تاريخ إنشاء الحساب

    /**
     * علاقة One-to-Many مع المقالات.
     * المستخدم الواحد يمكنه كتابة العديد من المقالات.
     * cascade = CascadeType.ALL يعني أن أي عملية على المستخدم (حذف، حفظ) تنعكس على
     * مقالاته.
     * orphanRemoval = true يعني إذا أزلنا مقالة من القائمة، تحذف من قاعدة البيانات.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    // Constructors
    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}