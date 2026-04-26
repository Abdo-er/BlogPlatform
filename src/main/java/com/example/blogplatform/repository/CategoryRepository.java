package com.example.blogplatform.repository;

import com.example.blogplatform.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * مستودع بيانات الأقسام (Category Repository).
 * يوفر عمليات CRUD للأقسام بالإضافة إلى استعلامات مخصصة حسب الحاجة.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * البحث عن قسم باستخدام اسمه (حيث أن الاسم يجب أن يكون فريداً).
     * 
     * @param name اسم القسم
     * @return Optional يحتوي على القسم إذا وجد
     */
    Optional<Category> findByName(String name);

    // يمكن إضافة المزيد من الدوال المخصصة هنا عند الحاجة
}