package com.example.blogplatform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * فئة تكوين Spring MVC الإضافية.
 * تُستخدم لإضافة View Controllers بسيطة (للصفحات التي لا تحتاج إلى منطق خاص في
 * Controller)
 * أو لتكوين إعدادات MVC أخرى مثل interceptors, resource handlers, إلخ.
 * 
 * تم إعدادها بطريقة قابلة للتوسع، حيث يمكن إضافة المزيد من الإعدادات لاحقاً
 * دون الحاجة لتعديل الـ Controllers الأساسية.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * إضافة View Controllers.
     * تستخدم هذه الطريقة لتسجيل صفحات بسيطة لا تحتاج إلى أي منطق (مثل صفحات static)
     * 
     * @param registry سجل View Controllers
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // يمكن إضافة view controllers هنا للصفحات الثابتة
        // مثال: registry.addViewController("/about").setViewName("about");

        // حالياً لا نحتاج إلى إضافات لأن لدينا Controllers مخصصة للصفحات الرئيسية
        // لكن تم ترك هذه الطريقة للتوسع المستقبلي
    }

    // يمكن إضافة override لطرق أخرى مثل addResourceHandlers, addInterceptors, إلخ
    // حسب الحاجة
}