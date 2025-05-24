package com.ibrahimcanerdogan.awesomejettutorial.architecture

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

/**
 * Bu dosya Jetpack Compose'da Phases (Aşamalar) konusunu ele almaktadır.
 * Compose'un üç ana aşaması: Composition, Layout ve Drawing aşamalarıdır.
 */

/**
 * Örnek 1: Layout Phase Optimizasyonu
 * Bu örnek, state okumalarının layout aşamasına nasıl taşınacağını gösterir.
 * Bu sayede gereksiz recomposition'ları önleriz.
 */
@Composable
fun LayoutPhaseOptimizationExample() {
    val listState = rememberLazyListState()

    Box {
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = "Paralaks Efektli Resim",
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    // State okuması layout aşamasında yapılıyor
                    IntOffset(x = 0, y = listState.firstVisibleItemScrollOffset / 2)
                }
        )

        LazyColumn(state = listState) {
            items(20) { index ->
                Text(
                    text = "Öğe $index",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

/**
 * Örnek 2: Recomposition Döngüsü (Kötü Örnek)
 * Bu örnek, yanlış kullanım sonucu oluşabilecek recomposition döngüsünü gösterir.
 * Bu tür döngülerden kaçınmak gerekir.
 */
@Composable
fun RecompositionLoopExample() {
    var imageHeightPx by remember { mutableStateOf(0) }

    Column {
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = "Resim",
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { size ->
                    // Kötü örnek: Her boyut değişiminde state güncelleniyor
                    imageHeightPx = size.height
                }
        )

        Text(
            text = "Resmin altındaki metin",
            modifier = Modifier.padding(
                top = with(LocalDensity.current) { imageHeightPx.toDp() }
            )
        )
    }
}

/**
 * Örnek 3: Doğru Layout Kullanımı
 * Bu örnek, önceki kötü örneğin doğru implementasyonunu gösterir.
 * Column kullanarak tek bir layout içinde elemanları düzenliyoruz.
 */
@Composable
fun CorrectLayoutExample() {
    Column {
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = "Resim",
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Resmin altındaki metin",
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Örnek 4: Phase Bağımlılıkları
 * Bu örnek, farklı phase'lerde state kullanımını gösterir.
 */
@Composable
fun PhaseDependenciesExample() {
    var count by remember { mutableStateOf(0) }
    var layoutPhaseValue by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Composition phase'de state kullanımı
        Text(
            text = "Sayaç: $count",
            style = MaterialTheme.typography.headlineMedium
        )

        // Layout phase'de state kullanımı
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset {
                    IntOffset(x = layoutPhaseValue, y = 0)
                }
        ) {
            Text("Layout Phase")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                count++
                layoutPhaseValue += 10
            }
        ) {
            Text("Güncelle")
        }
    }
}

// Preview fonksiyonları
@Preview(showBackground = true)
@Composable
fun LayoutPhaseOptimizationExamplePreview() {
    MaterialTheme {
        LayoutPhaseOptimizationExample()
    }
}

@Preview(showBackground = true)
@Composable
fun RecompositionLoopExamplePreview() {
    MaterialTheme {
        RecompositionLoopExample()
    }
}

@Preview(showBackground = true)
@Composable
fun CorrectLayoutExamplePreview() {
    MaterialTheme {
        CorrectLayoutExample()
    }
}

@Preview(showBackground = true)
@Composable
fun PhaseDependenciesExamplePreview() {
    MaterialTheme {
        PhaseDependenciesExample()
    }
} 