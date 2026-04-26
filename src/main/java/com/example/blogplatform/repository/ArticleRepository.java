package com.example.blogplatform.repository;

import com.example.blogplatform.model.Article;
import com.example.blogplatform.model.Category;
import com.example.blogplatform.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * مستودع بيانات المقالات (Article Repository).
 * يوفر عمليات CRUD للمقالات بالإضافة إلى استعلامات مخصصة للبحث والتصفية.
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * جلب جميع المقالات التي تنتمي إلى قسم معين.
     * 
     * @param category كائن القسم
     * @return قائمة بالمقالات في هذا القسم
     */
    List<Article> findByCategory(Category category);

    /**
     * جلب جميع المقالات التي كتبها مؤلف معين.
     * 
     * @param author كائن المستخدم (المؤلف)
     * @return قائمة بمقالات هذا المؤلف
     */
    List<Article> findByAuthor(User author);

    /**
     * جلب أحدث المقالات مرتبة تنازلياً حسب تاريخ الإنشاء.
     * يتم استخدام Pageable لتحديد عدد النتائج (مثلاً أحدث 10 مقالات).
     * 
     * @param pageable كائن لتحديد عدد النتائج وترتيبها
     * @return قائمة بأحدث المقالات
     */
    List<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * البحث عن المقالات التي تحتوي على كلمة مفتاحية في العنوان أو المحتوى.
     * يستخدم استعلام JPQL مع شرط LIKE.
     * 
     * @param keyword الكلمة المفتاحية للبحث
     * @return قائمة بالمقالات المطابقة
     */
    @Query("SELECT a FROM Article a WHERE a.title LIKE %:keyword% OR a.content LIKE %:keyword%")
    List<Article> search(@Param("keyword") String keyword);
}