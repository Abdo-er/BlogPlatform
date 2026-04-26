package com.example.blogplatform.service.validation;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * خدمة التحقق من جودة المحتوى (Content Validator).
 * تقوم بتحليل عنوان المقالة ومحتواها للتأكد من أنها ليست سطحية وتحتوي على فكرة
 * متكاملة.
 * يتم استخدامها في ArticleService قبل حفظ المقالة.
 */
@Component
public class ContentValidator {

    // قائمة الكلمات الممنوعة أو التي تشير إلى محتوى غير مرغوب (يمكن توسيعها)
    // private static final Set<String> SPAM_KEYWORDS = new HashSet<>(Arrays.asList(
    // "اشترك", "رابط", "زيارة", "شراء", "خصم", "كوبون", "مجاني", "اضغط هنا",
    // "اكسب", "دولار", "عملة", "ربح", "كنز", "سريع", "مضمون", "100%"));

    // // قائمة كلمات قد تشير إلى سطحية (يمكن تعديلها)
    // private static final Set<String> SHALLOW_PHRASES = new
    // HashSet<>(Arrays.asList(
    // "صباح الخير", "مساء الخير", "كيفكم", "شخباركم", "حبيت أشارككم",
    // "بس كذا", "هذا والله اعلم", "انتهى", "والسلام"));

    /**
     * التحقق من صحة وجودة المحتوى.
     * 
     * @param title   عنوان المقالة
     * @param content نص المقالة
     * @return ValidationResult يحتوي على نتيجة التحقق وقائمة الرسائل
     */
    public ValidationResult validate(String title, String content) {
        ValidationResult result = new ValidationResult();
        result.setValid(true);

        // التحقق من الطول (تم بالفعل باستخدام Bean Validation لكن نضيف تحققاً إضافياً)
        if (content.length() < 200) {
            result.setValid(false);
            result.addMessage("المحتوى قصير جداً. يجب أن يكون على الأقل 200 حرف.");
            return result;
        }

        // التحقق من طول العنوان
        if (title.length() < 5 || title.length() > 200) {
            result.setValid(false);
            result.addMessage("العنوان يجب أن يكون بين 5 و 200 حرف.");
            return result;
        }

        // تحويل المحتوى إلى أحرف صغيرة لتوحيد المقارنة
        String lowerContent = content.toLowerCase();
        String lowerTitle = title.toLowerCase();

        // التحقق من وجود كلمات سبام في المحتوى
        // for (String keyword : SPAM_KEYWORDS) {
        // if (lowerContent.contains(keyword)) {
        // result.setValid(false);
        // result.addMessage("المحتوى يحتوي على كلمة غير مرغوب فيها: " + keyword);
        // return result;
        // }
        // }

        // التحقق من وجود كلمات سبام في العنوان
        // for (String keyword : SPAM_KEYWORDS) {
        // if (lowerTitle.contains(keyword)) {
        // result.setValid(false);
        // result.addMessage("العنوان يحتوي على كلمة غير مرغوب فيها: " + keyword);
        // return result;
        // }
        // }

        // التحقق من وجود عبارات سطحية
        // for (String phrase : SHALLOW_PHRASES) {
        // if (lowerContent.contains(phrase)) {
        // result.setValid(false);
        // result.addMessage("المحتوى يحتوي على عبارات غير مناسبة: " + phrase);
        // return result;
        // }
        // }

        // التحقق من وجود تكرار مفرط للأحرف (مثل "!!!!" أو "......")
        if (hasExcessiveRepeatedChars(content)) {
            result.setValid(false);
            result.addMessage("المحتوى يحتوي على تكرار مفرط للأحرف.");
            return result;
        }

        // التحقق من وجود عدد كافٍ من الجمل (على الأقل 3 جمل)
        int sentenceCount = countSentences(content);
        if (sentenceCount < 3) {
            result.setValid(false);
            result.addMessage("المحتوى يحتوي على عدد قليل جداً من الجمل. حاول التوسع في الفكرة.");
            return result;
        }

        // تحقق إضافي: متوسط طول الجملة أقل من 5 كلمات قد يدل على سطحية
        double avgWordsPerSentence = averageWordsPerSentence(content);
        if (avgWordsPerSentence < 5) {
            result.setValid(false);
            result.addMessage("الجمل قصيرة جداً. يبدو المحتوى غير متكامل.");
            return result;
        }

        // تحقق من نسبة الفقرات (إذا كان النص طويلاً جداً بدون فواصل)
        if (!hasParagraphs(content)) {
            result.setValid(false);
            result.addMessage("النص يبدو كتلة واحدة دون تقسيم إلى فقرات. يفضل تقسيم المحتوى.");
            return result;
        }

        // إذا اجتاز كل التحقيات
        return result;
    }

    /**
     * التحقق من وجود تكرار مفرط للأحرف (أكثر من 4 مرات متتالية).
     * 
     * @param text النص المراد فحصه
     * @return true إذا وجد تكرار مفرط
     */
    private boolean hasExcessiveRepeatedChars(String text) {
        // نمط للبحث عن تكرار نفس الحرف أكثر من 4 مرات متتالية
        Pattern pattern = Pattern.compile("(.)\\1{4,}");
        return pattern.matcher(text).find();
    }

    /**
     * حساب عدد الجمل في النص (تقريبي: التقسيم على .!؟ مع مراعاة اللغة العربية).
     * 
     * @param text النص
     * @return عدد الجمل
     */
    private int countSentences(String text) {
        // تقسيم على .!؟ (مع مراعاة علامات الترقيم العربية)
        String[] sentences = text.split("[.!?؟]+");
        // تصفية الجمل الفارغة
        return (int) Arrays.stream(sentences)
                .filter(s -> !s.trim().isEmpty())
                .count();
    }

    /**
     * حساب متوسط عدد الكلمات في الجملة.
     * 
     * @param text النص
     * @return متوسط الكلمات لكل جملة
     */
    private double averageWordsPerSentence(String text) {
        String[] sentences = text.split("[.!?؟]+");
        if (sentences.length == 0)
            return 0;

        int totalWords = 0;
        int validSentenceCount = 0;

        for (String sentence : sentences) {
            String trimmed = sentence.trim();
            if (trimmed.isEmpty())
                continue;

            String[] words = trimmed.split("\\s+");
            totalWords += words.length;
            validSentenceCount++;
        }

        return validSentenceCount == 0 ? 0 : (double) totalWords / validSentenceCount;
    }

    /**
     * التحقق من وجود فقرات (سطر جديد أو مسافتين متتاليتين) لضمان تنظيم المحتوى.
     * 
     * @param text النص
     * @return true إذا كان النص مقسماً إلى فقرات
     */
    private boolean hasParagraphs(String text) {
        // البحث عن وجود سطر جديد أو أكثر من مسافتين متتاليتين (علامة فقرة)
        return text.contains("\n\n") || text.contains("\r\n\r\n") || text.contains("  ");
    }
}