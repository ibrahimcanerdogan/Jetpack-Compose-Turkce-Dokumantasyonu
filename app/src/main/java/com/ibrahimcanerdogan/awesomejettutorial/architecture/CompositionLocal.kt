package com.ibrahimcanerdogan.awesomejettutorial.architecture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Bu dosya Jetpack Compose'da CompositionLocal kullanımını ele almaktadır.
 * CompositionLocal, değerlerin composable hiyerarşisinde aşağıya doğru aktarılmasını sağlar.
 */

/**
 * Örnek 1: Temel CompositionLocal Kullanımı
 * Bu örnek, basit bir CompositionLocal tanımlama ve kullanımını gösterir.
 */
// CompositionLocal tanımı
val LocalElevations = compositionLocalOf { Elevations() }

// Elevation veri sınıfı
data class Elevations(
    val card: Dp = 0.dp,
    val default: Dp = 0.dp
)

@Composable
fun BasicCompositionLocalExample() {
    // CompositionLocal değerini sağlama
    CompositionLocalProvider(LocalElevations provides Elevations(card = 4.dp)) {
        // CompositionLocal değerini kullanma
        val elevations = LocalElevations.current
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Card elevation: ${elevations.card}",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * Örnek 2: İç İçe CompositionLocal Kullanımı
 * Bu örnek, iç içe CompositionLocal kullanımını gösterir.
 */
@Composable
fun NestedCompositionLocalExample() {
    CompositionLocalProvider(LocalElevations provides Elevations(card = 4.dp)) {
        // İlk seviye
        val outerElevations = LocalElevations.current
        
        CompositionLocalProvider(LocalElevations provides Elevations(card = 8.dp)) {
            // İkinci seviye
            val innerElevations = LocalElevations.current
            
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Outer elevation: ${outerElevations.card}")
                Text("Inner elevation: ${innerElevations.card}")
            }
        }
    }
}

/**
 * Örnek 3: CompositionLocal ile Tema Özelleştirme
 * Bu örnek, CompositionLocal kullanarak tema özelleştirmesini gösterir.
 */
// Tema renkleri için CompositionLocal
val LocalThemeColors = compositionLocalOf { ThemeColors() }

data class ThemeColors(
    val primary: Color = Color.Blue,
    val secondary: Color = Color.Gray
)

@Composable
fun ThemeCustomizationExample() {
    CompositionLocalProvider(
        LocalThemeColors provides ThemeColors(
            primary = Color.Green,
            secondary = Color.Yellow
        )
    ) {
        val colors = LocalThemeColors.current
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Primary Color Example",
                modifier = Modifier
                    .background(colors.primary)
                    .padding(16.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Secondary Color Example",
                modifier = Modifier
                    .background(colors.secondary)
                    .padding(16.dp)
            )
        }
    }
}

// Preview fonksiyonları
@Preview(showBackground = true)
@Composable
fun BasicCompositionLocalExamplePreview() {
    MaterialTheme {
        BasicCompositionLocalExample()
    }
}

@Preview(showBackground = true)
@Composable
fun NestedCompositionLocalExamplePreview() {
    MaterialTheme {
        NestedCompositionLocalExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeCustomizationExamplePreview() {
    MaterialTheme {
        ThemeCustomizationExample()
    }
} 