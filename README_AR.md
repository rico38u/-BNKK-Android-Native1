# BNKK Android Native — مشروع إعادة البناء الأصلي

هذه حزمة مشروع Android Studio جديدة، أُعيد بناؤها من الصفر اعتماداً على ملفات ZIP التي أرسلها المستخدم. لم يُنقل ملف `core1.html`، ولم يعد التطبيق يستخدم WebView.

## ما الذي تغيّر؟

- التطبيق مبني بواجهات Android أصلية باستخدام Kotlin وJetpack Compose.
- لا يوجد `core1.html` ولا `WebView` داخل المشروع.
- تم تقسيم الكود إلى وحدات Gradle مستقلة:
  - `app`: نقطة تشغيل التطبيق والتنقل العام.
  - `core:model`: نماذج المنشورات والمحادثات.
  - `core:designsystem`: ألوان BNKK والثيم العام.
  - `feature:home`: الصفحة الرئيسية والقصص وبطاقات المنشورات.
  - `feature:explore`: البحث والاستكشاف والتصنيفات.
  - `feature:create`: إنشاء منشور واختيار صورة/فيديو من منتقي Android.
  - `feature:messages`: قائمة محادثات وواجهة محادثة محلية.
  - `feature:profile`: الملف الشخصي.
- تمت الاستفادة من شعار التطبيق الموجود في الحزمة المرسلة.
- لم يتم نسخ `keys/key.pem` أو شهادة التوقيع الخاصة إلى المشروع الجديد.

## فتح وبناء المشروع

1. فك ضغط `BNKK_Android_Native.zip`.
2. افتح مجلد `BNKK_Android_Native` من Android Studio حديث.
3. ثبّت Android SDK Platform 37 وBuild Tools 36.0.0 وPlatform Tools.
4. استخدم JDK 17 أو أحدث مدعوم من نسخة Android Gradle Plugin المحددة.
5. نفّذ `:app:assembleDebug` من نافذة Gradle. على Linux/macOS شغّل `./gradlew assembleDebug`، وعلى Windows شغّل `gradlew.bat assembleDebug`.
6. ناتج Debug المتوقع بعد نجاح البناء: `app/build/outputs/apk/debug/app-debug.apk`.

المشروع يحدد Android Gradle Plugin `9.3.1` وGradle `9.8.2` وKotlin Gradle Plugin وCompose Compiler `2.4.20` وCompose BOM `2026.09.00`. ملفا التشغيل `gradlew` و`gradlew.bat` يستخدمان Gradle Wrapper القياسي 9.8.2، ويتضمن المشروع ملف `gradle-wrapper.jar` وبصمة SHA-256 للتحقق من التوزيعة.

**مهم:** وجود Gradle وCommand Line Tools وحدهما لا يكفي. يحتاج البناء كذلك إلى Android SDK Platform 37 وBuild Tools 36.0.0، وإلى تنزيل Android Gradle Plugin ومكتبات Compose/Kotlin من مستودعات Google وMaven Central. لم يُنتج APK من هذه الحزمة حتى الآن؛ راجع `docs/BUILD_STATUS_AR.md` لحالة المحاولة الأخيرة.

## حالة الوظائف الحالية

هذه هي الدفعة الأولى من إعادة البناء الأصلية وليست منصة اجتماعية مكتملة الإنتاج بعد:

- التنقل والواجهات الأساسية Native تعمل محلياً.
- الإعجاب والتبديل بين التبويبات والبحث في عينات الاستكشاف تعمل محلياً.
- منتقي صور/فيديو Android الرسمي يعمل دون طلب صلاحية تخزين عامة.
- إنشاء المحتوى يضيف معاينة محلية ضمن الجلسة فقط.
- المحادثات والرسائل نموذج محلي داخل التطبيق وليست رسائل تصل إلى مستخدمين آخرين.
- لا يوجد حتى الآن خادم أو مصادقة Google/Facebook/Apple أو قاعدة بيانات سحابية أو رفع ملفات أو بث مباشر أو إشعارات Push.

لا تُعرض الوظائف السحابية على أنها مفعّلة قبل إعداد الخادم ومفاتيح OAuth وقواعد الأمان. راجع `docs/BACKEND_INTEGRATION_AR.md`.

## الأمان

لا تضع مفاتيح API أو مفاتيح توقيع أو كلمات مرور داخل Git. أضف إعدادات التوقيع عبر متغيرات بيئة أو ملف محلي مستبعد من Git قبل إصدار نسخة إنتاجية.

## بناء APK عبر GitHub Actions
إذا تعذر تنزيل Android SDK من بيئة محلية، راجع `docs/CLOUD_BUILD_AR.md`. يحتوي المشروع على Workflow جاهز في `.github/workflows/android-debug.yml` لبناء Debug APK ورفعه كـ Artifact بعد تشغيله على GitHub.
