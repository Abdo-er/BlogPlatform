package com.example.blogplatform.service.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * كائن يحمل نتيجة التحقق من المحتوى.
 * يستخدم لنقل حالة التحقق (نجاح/فشل) وقائمة الرسائل (أخطاء أو تأكيدات)
 * من ContentValidator إلى ArticleService ثم إلى المتحكم لعرضها للمستخدم.
 */
public class ValidationResult {

    private boolean valid; // حالة التحقق: true نجاح، false فشل
    private List<String> messages; // قائمة الرسائل (أخطاء أو رسائل نجاح)

    /**
     * Constructor لإنشاء كائن ValidationResult مع قيم افتراضية.
     */
    public ValidationResult() {
        this.valid = true; // افتراضياً نعتبر التحقق ناجح حتى يثبت العكس
        this.messages = new ArrayList<>();
    }

    /**
     * Constructor مع تحديد الحالة والرسائل.
     * 
     * @param valid    حالة التحقق
     * @param messages قائمة الرسائل
     */
    public ValidationResult(boolean valid, List<String> messages) {
        this.valid = valid;
        this.messages = messages != null ? messages : new ArrayList<>();
    }

    // Getters and Setters
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages != null ? messages : new ArrayList<>();
    }

    /**
     * إضافة رسالة واحدة إلى القائمة.
     * 
     * @param message الرسالة المراد إضافتها
     */
    public void addMessage(String message) {
        this.messages.add(message);
    }

    /**
     * إضافة عدة رسائل إلى القائمة.
     * 
     * @param messages قائمة الرسائل المراد إضافتها
     */
    public void addAllMessages(List<String> messages) {
        if (messages != null) {
            this.messages.addAll(messages);
        }
    }

    /**
     * التحقق مما إذا كان هناك رسائل أم لا.
     * 
     * @return true إذا كانت القائمة غير فارغة
     */
    public boolean hasMessages() {
        return !messages.isEmpty();
    }

    /**
     * الحصول على أول رسالة (مناسبة لعرض خطأ واحد).
     * 
     * @return أول رسالة أو null إذا لم توجد
     */
    public String getFirstMessage() {
        return messages.isEmpty() ? null : messages.get(0);
    }
}