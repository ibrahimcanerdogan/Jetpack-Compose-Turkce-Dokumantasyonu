# Jetpack Compose Türkçe Dokümantasyonu

Bu proje, Jetpack Compose'un temel kavramlarını ve kullanımını Türkçe olarak açıklayan örnekler içermektedir.

## İçerik

### 1. Architecture
- **Phases.kt**: Compose'un yaşam döngüsü ve fazları
- **State.kt**: State yönetimi ve örnekleri
- **Architecture.kt**: Compose mimarisi ve best practices
- **Layering.kt**: Katmanlı yapı ve composable organizasyonu
- **CompositionLocal.kt**: CompositionLocal kullanımı ve örnekleri
- **Navigation.kt**: Compose Navigation yapısı ve örnekleri

## Kurulum

Projeyi çalıştırmak için aşağıdaki adımları takip edin:

1. Android Studio'yu açın
2. Projeyi klonlayın
3. Gradle sync işlemini tamamlayın
4. Uygulamayı çalıştırın

## Gereksinimler

- Android Studio Arctic Fox veya üzeri
- Kotlin 1.8.0 veya üzeri
- Android Gradle Plugin 7.0.0 veya üzeri
- Compose BOM 2023.10.00 veya üzeri

## Bağımlılıklar

```gradle
dependencies {
    implementation platform('androidx.compose:compose-bom:2023.10.00')
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.material3:material3'
    implementation 'androidx.compose.ui:ui-tooling-preview'
    implementation 'androidx.navigation:navigation-compose:2.7.5'
    debugImplementation 'androidx.compose.ui:ui-tooling'
}
```

## Katkıda Bulunma

1. Bu depoyu fork edin
2. Yeni bir branch oluşturun (`git checkout -b feature/newFeature`)
3. Değişikliklerinizi commit edin (`git commit -am 'New feature: Description'`)
4. Branch'inizi push edin (`git push origin feature/newFeature`)
5. Pull Request oluşturun

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakın.

## İletişim

Sorularınız veya önerileriniz için lütfen bir issue açın.

---

Bu dokümantasyon, Jetpack Compose'un temel kavramlarını anlamak ve uygulamak isteyen geliştiriciler için hazırlanmıştır. Her bir dosya, ilgili konuyu detaylı örnekler ve açıklamalarla ele almaktadır. 