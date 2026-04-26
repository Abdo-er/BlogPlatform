package com.example.blogplatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * كائن نقل بيانات تسجيل المستخدم (User Registration DTO).
 * يستخدم لنقل بيانات التسجيل من نموذج الواجهة إلى طبقة الخدمة.
 * يحتوي على التحقق من صحة البيانات (Validation) باستخدام Jakarta Validation.
 */
public class UserRegistrationDto {

    @NotBlank(message = "اسم المستخدم مطلوب")
    @Size(min = 3, max = 50, message = "اسم المستخدم يجب أن يكون بين 3 و 50 حرفاً")
    private String username;

    @NotBlank(message = "البريد الإلكتروني مطلوب")
    @Email(message = "يرجى إدخال بريد إلكتروني صحيح")
    private String email;

    @NotBlank(message = "كلمة المرور مطلوبة")
    @Size(min = 6, message = "كلمة المرور يجب أن تكون على الأقل 6 أحرف")
    private String password;

    @NotBlank(message = "تأكيد كلمة المرور مطلوب")
    private String confirmPassword;

    @Size(max = 100, message = "الاسم الكامل يجب أن لا يتجاوز 100 حرف")
    private String fullName;

    @Size(max = 500, message = "النبذة يجب أن لا تتجاوز 500 حرف")
    private String bio;

    // Constructors
    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Getters and Setters
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
}