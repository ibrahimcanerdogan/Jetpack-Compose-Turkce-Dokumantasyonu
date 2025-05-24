package com.ibrahimcanerdogan.awesomejettutorial.architecture

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Bu dosya Jetpack Compose'da Side Effects (Yan Etkiler) konusunu ele almaktadır.
 * Side Effects, composable fonksiyonların dış dünya ile etkileşimini yönetmek için kullanılır.
 */

/**
 * Örnek 1: LaunchedEffect
 * LaunchedEffect, composable içinde coroutine başlatmak için kullanılır.
 * Composable yeniden oluşturulduğunda, key değişmediği sürece coroutine yeniden başlatılmaz.
 */
@Composable
fun LaunchedEffectExample() {
    var count by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }

    // LaunchedEffect, composable ilk kez oluşturulduğunda çalışır
    LaunchedEffect(key1 = count) {
        message = "Sayaç değişti: $count"
        delay(1000) // 1 saniye bekle
        message = "Güncelleme tamamlandı: $count"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { count++ }) {
            Text("Sayaç: $count")
        }
    }
}

/**
 * Örnek 2: DisposableEffect
 * DisposableEffect, composable destroy edildiğinde temizleme işlemlerini gerçekleştirir.
 * Örneğin: Event listener'ları kaldırma, kaynakları serbest bırakma gibi.
 */
@Composable
fun DisposableEffectExample() {
    var isActive by remember { mutableStateOf(true) }
    var message by remember { mutableStateOf("") }

    DisposableEffect(key1 = isActive) {
        // Effect başladığında çalışacak kod
        message = if (isActive) "Effect aktif" else "Effect pasif"
        println("Effect başladı: $isActive")

        // Cleanup fonksiyonu
        onDispose {
            println("Effect temizlendi")
            message = "Effect temizlendi"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { isActive = !isActive }) {
            Text(if (isActive) "Effect'i Devre Dışı Bırak" else "Effect'i Etkinleştir")
        }
    }
}

/**
 * Örnek 3: SideEffect
 * SideEffect, her başarılı recomposition'dan sonra çalışır.
 * Composable'ın state'i değiştiğinde her seferinde tetiklenir.
 */
@Composable
fun SideEffectExample() {
    var count by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }

    SideEffect {
        // Her recomposition'da çalışır
        message = "SideEffect çalıştı: $count"
        println("SideEffect çalıştı: $count")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { count++ }) {
            Text("Sayaç: $count")
        }
    }
}

/**
 * Örnek 4: rememberCoroutineScope
 * rememberCoroutineScope, composable içinde coroutine scope oluşturur.
 * Bu scope, composable destroy edildiğinde otomatik olarak iptal edilir.
 */
@Composable
fun RememberCoroutineScopeExample() {
    var count by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                scope.launch {
                    message = "İşlem başladı"
                    delay(1000)
                    count++
                    message = "İşlem tamamlandı: $count"
                }
            }
        ) {
            Text("Asenkron İşlem Başlat")
        }
    }
}

/**
 * Örnek 5: rememberUpdatedState
 * rememberUpdatedState, bir değeri güncel tutmak için kullanılır.
 * Özellikle LaunchedEffect içinde kullanıldığında faydalıdır.
 */
@Composable
fun RememberUpdatedStateExample() {
    var count by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }

    // Güncel count değerini tut
    val currentCount by rememberUpdatedState(newValue = count)

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            message = "Güncel sayaç: $currentCount"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { count++ }) {
            Text("Sayaç: $count")
        }
    }
}

/**
 * Örnek 6: produceState
 * produceState, bir State nesnesi üretir ve asenkron olarak güncelleyebilir.
 * Coroutine scope'u otomatik olarak yönetir.
 */
@Composable
fun ProduceStateExample() {
    var count by remember { mutableStateOf(0) }
    
    // Asenkron olarak güncellenen state
    val asyncCount by produceState(initialValue = 0) {
        while (true) {
            delay(1000)
            value = count
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Asenkron Sayaç: $asyncCount",
            style = MaterialTheme.typography.bodyLarge
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { count++ }) {
            Text("Sayaç: $count")
        }
    }
}

// Preview fonksiyonları
@Preview(showBackground = true)
@Composable
fun LaunchedEffectExamplePreview() {
    MaterialTheme {
        LaunchedEffectExample()
    }
}

@Preview(showBackground = true)
@Composable
fun DisposableEffectExamplePreview() {
    MaterialTheme {
        DisposableEffectExample()
    }
}

@Preview(showBackground = true)
@Composable
fun SideEffectExamplePreview() {
    MaterialTheme {
        SideEffectExample()
    }
}

@Preview(showBackground = true)
@Composable
fun RememberCoroutineScopeExamplePreview() {
    MaterialTheme {
        RememberCoroutineScopeExample()
    }
}

@Preview(showBackground = true)
@Composable
fun RememberUpdatedStateExamplePreview() {
    MaterialTheme {
        RememberUpdatedStateExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ProduceStateExamplePreview() {
    MaterialTheme {
        ProduceStateExample()
    }
} 