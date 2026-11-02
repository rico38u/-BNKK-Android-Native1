# بناء APK سحابيًا عبر GitHub Actions

بيئة العمل الحالية لا تستطيع الوصول إلى Google Maven وMaven Central بسبب فشل DNS، ولا تحتوي على Android SDK Platform أو Build Tools. لذلك أُضيف Workflow يبني على GitHub-hosted Ubuntu runner متصل بالإنترنت.

## ما يلزم

1. حساب GitHub تملكه.
2. إنشاء مستودع جديد (يمكن جعله Private).
3. رفع محتويات هذا المجلد إلى جذر المستودع، مع مجلد `.github/workflows`.
4. فتح تبويب Actions في المستودع.
5. اختيار `BNKK Android Debug APK` ثم `Run workflow`.
6. بعد نجاح التشغيل، تنزيل Artifact باسم `bnkk-debug-apk` واستخراج `app-debug.apk`.

يعمل الـ Workflow أيضًا تلقائيًا عند دفع commit إلى `main` أو `master`.

## ما الذي يثبته ويستخدمه البناء؟

- JDK 17.
- Android Command-line Tools 22.0.
- Android SDK Platform 37.
- Android Build Tools 36.0.0.
- Gradle Wrapper 9.8.2.

## الأمان

لا ترسل كلمة مرور GitHub أو Personal Access Token في المحادثة. استخدم شاشة ربط GitHub الرسمية للموافقة على الأذونات. لا يتطلب بناء Debug APK أي مفتاح توقيع Release خاص.

## حدود الناتج

الـ APK الناتج Debug-signed ومناسب للاختبار والتثبيت. لا يُعد نسخة Production أو Google Play قبل إعداد خادم حقيقي، واختبار تكامل الوظائف، وإضافة توقيع Release خاص وآمن.
