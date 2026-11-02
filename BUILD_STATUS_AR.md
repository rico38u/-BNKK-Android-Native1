# حالة بناء BNKK Android Native

## نتيجة المحاولة المحلية

لم يتم إنشاء APK في بيئة العمل هذه. فشل Gradle قبل ترجمة Kotlin أثناء حل إضافة Android Gradle Plugin واعتماديات Kotlin/Compose، لأن DNS لا يستطيع الوصول إلى مستودعات Google وMaven Central. كما فشل `sdkmanager` في جلب فهارس Google، ولم يتوفر محليًا Android SDK Platform 37 أو `android.jar` أو Build Tools (`aapt2` و`apksigner`).

تم التحقق من وجود Gradle 9.8.2 وAndroid Command-line Tools 22.0 محليًا، لكنهما وحدهما لا يكفيان لإنتاج APK.

## تغييرات تجهيز البناء

- أضيف `gradle-wrapper.jar` القياسي إلى المشروع.
- تمت مزامنة Gradle Wrapper على 9.8.2، مع SHA-256 للتحقق من ملف التوزيع.
- تم تحديث GitHub Actions لبناء Debug APK على Ubuntu، وتنزيل SDK Platform 37 وBuild Tools 36.0.0، ثم رفع الناتج كـ Artifact.

## طريقة الحصول على APK

بعد رفع محتويات المشروع إلى مستودع GitHub، افتح Actions، ثم اختر `BNKK Android Debug APK`، ثم `Run workflow`. بعد اكتمال التشغيل بنجاح نزّل Artifact باسم `bnkk-debug-apk` واستخرج `app-debug.apk`.

## حالة الناتج

لا يوجد APK جديد في هذه الحزمة. الـ Workflow جاهز لكنه لم يُشغّل داخل مستودع GitHub الخاص بالمستخدم بعد. APK الناتج من `assembleDebug` سيكون Debug-signed للتجربة وليس إصدار Play Store موقّع بمفتاح Release خاص.
