package com.example.blogplatform.service;

import com.example.blogplatform.model.Category;
import com.example.blogplatform.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * خدمة الأقسام (Category Service).
 * مسؤولة عن منطق الأعمال المتعلق بالأقسام، مثل:
 * - جلب جميع الأقسام
 * - جلب قسم بواسطة المعرف
 * - البحث عن قسم بالاسم
 * - إضافة قسم جديد (للمشرفين مثلاً)
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * جلب جميع الأقسام الموجودة في قاعدة البيانات.
     * 
     * @return قائمة بجميع الأقسام
     */
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * جلب قسم بواسطة معرفه.
     * 
     * @param id معرف القسم
     * @return Optional يحتوي على القسم إذا وجد
     */
    @Transactional(readOnly = true)
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * البحث عن قسم بواسطة اسمه.
     * 
     * @param name اسم القسم
     * @return Optional يحتوي على القسم إذا وجد
     */
    @Transactional(readOnly = true)
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * حفظ قسم جديد أو تحديث قسم موجود.
     * 
     * @param category كائن القسم
     * @return القسم المحفوظ
     */
    @Transactional
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * حذف قسم بواسطة المعرف.
     * 
     * @param id معرف القسم
     */
    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    /**
     * التحقق من وجود قسم بالاسم.
     * 
     * @param name اسم القسم
     * @return true إذا كان موجوداً
     */
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.findByName(name).isPresent();
    }
}