package com.ibrahimcanerdogan.awesomejettutorial.architecture

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * Bu dosya Jetpack Compose'da Lifecycle (Yaşam Döngüsü) konusunu ele almaktadır.
 * Composable fonksiyonların yaşam döngüsü ve state yönetimi örnekleri içerir.
 */

/**
 * Örnek 1: Key Kullanımı
 * Key composable'ı, composable'ların kimliğini belirlemek için kullanılır.
 * Özellikle liste içindeki öğelerin yeniden oluşturulmasını optimize etmek için önemlidir.
 */
@Composable
fun KeyExample(items: List<String>) {
    Column {
        for (item in items) {
            key(item) { // Her öğe için benzersiz key
                Text(
                    text = item,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

/**
 * Örnek 2: LifecycleEventObserver ile Yaşam Döngüsü Takibi
 * Bu örnek, composable'ın yaşam döngüsü olaylarını nasıl takip edeceğini gösterir.
 */

@Composable
fun LifecycleObserverExample() {
    var lifecycleState by remember { mutableStateOf("") }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycleState = when (event) {
                Lifecycle.Event.ON_CREATE -> "ON_CREATE"
                Lifecycle.Event.ON_START -> "ON_START"
                Lifecycle.Event.ON_RESUME -> "ON_RESUME"
                Lifecycle.Event.ON_PAUSE -> "ON_PAUSE"
                Lifecycle.Event.ON_STOP -> "ON_STOP"
                Lifecycle.Event.ON_DESTROY -> "ON_DESTROY"
                else -> "UNKNOWN"
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Yaşam Döngüsü Durumu: $lifecycleState",
            style = MaterialTheme.typography.bodyLarge
        )
        Log.i("LifecycleObserver", "Current state: $lifecycleState")
    }
}

/**
 * Örnek 3: Stable Tip Kullanımı
 * @Stable annotation'ı ile tip kararlılığını sağlama örneği.
 */
@Stable
interface UiState<T> {
    val value: T?
    val exception: Throwable?
    val hasError: Boolean
        get() = exception != null
}

/**
 * Örnek 4: Recomposition Optimizasyonu
 * Bu örnek, recomposition'ın nasıl optimize edileceğini gösterir.
 */
@Composable
fun RecompositionExample() {
    var count by remember { mutableStateOf(0) }
    var expensiveValue by remember { mutableStateOf(0) }

    // Pahalı hesaplama işlemi
    val derivedValue = remember(count) {
        // Bu hesaplama sadece count değiştiğinde yapılır
        count * 2
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sayaç: $count",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Text(
            text = "Türetilmiş Değer: $derivedValue",
            style = MaterialTheme.typography.bodyLarge
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { count++ }) {
            Text("Arttır")
        }
    }
}

// Preview fonksiyonları
@Preview(showBackground = true)
@Composable
fun KeyExamplePreview() {
    MaterialTheme {
        KeyExample(listOf("Öğe 1", "Öğe 2", "Öğe 3"))
    }
}

@Preview(showBackground = true)
@Composable
fun LifecycleObserverExamplePreview() {
    MaterialTheme {
        LifecycleObserverExample()
    }
}

@Preview(showBackground = true)
@Composable
fun RecompositionExamplePreview() {
    MaterialTheme {
        RecompositionExample()
    }
} 