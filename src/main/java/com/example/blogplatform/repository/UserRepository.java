package com.example.blogplatform.repository;

import com.example.blogplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * مستودع بيانات المستخدم (User Repository).
 * يوفر عمليات CRUD الأساسية للمستخدمين بالإضافة إلى دوال بحث مخصصة.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * البحث عن مستخدم باستخدام اسم المستخدم.
     * 
     * @param username اسم المستخدم
     * @return Optional يحتوي على المستخدم إذا وجد
     */
    Optional<User> findByUsername(String username);

    /**
     * البحث عن مستخدم باستخدام البريد الإلكتروني.
     * 
     * @param email البريد الإلكتروني
     * @return Optional يحتوي على المستخدم إذا وجد
     */
    Optional<User> findByEmail(String email);

    /**
     * التحقق من وجود مستخدم باسم مستخدم معين.
     * 
     * @param username اسم المستخدم
     * @return true إذا كان المستخدم موجوداً
     */
    Boolean existsByUsername(String username);

    /**
     * التحقق من وجود مستخدم ببريد إلكتروني معين.
     * 
     * @param email البريد الإلكتروني
     * @return true إذا كان البريد الإلكتروني موجوداً
     */
    Boolean existsByEmail(String email);
}