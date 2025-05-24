package com.ibrahimcanerdogan.awesomejettutorial.architecture

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Bu dosya Jetpack Compose'da Katmanlama (Layering) konusunu ele almaktadır.
 * Farklı seviyelerdeki bileşenlerin kullanımı ve özelleştirilmesi konularını içerir.
 */

/**
 * Örnek 1: Yüksek Seviye API Kullanımı
 * Bu örnek, yüksek seviye animasyon API'sinin kullanımını gösterir.
 * animateColorAsState kullanarak basit bir renk animasyonu.
 */
@Composable
fun HighLevelAnimationExample() {
    var isActive by remember { mutableStateOf(false) }
    
    // Yüksek seviye animasyon API'si
    val backgroundColor by animateColorAsState(
        targetValue = if (isActive) Color.Green else Color.Red,
        label = "backgroundColor"
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color = backgroundColor)
            .clickable { isActive = !isActive },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isActive) "Aktif" else "Pasif",
            color = Color.White
        )
    }
}

/**
 * Örnek 2: Düşük Seviye API Kullanımı
 * Bu örnek, düşük seviye animasyon API'sinin kullanımını gösterir.
 * Animatable kullanarak daha fazla kontrol sağlayan bir animasyon.
 */
@Composable
fun LowLevelAnimationExample() {
    var isActive by remember { mutableStateOf(false) }
    
    // Düşük seviye animasyon API'si
    val color = remember { Animatable(Color.Gray) }
    
    LaunchedEffect(isActive) {
        color.animateTo(
            targetValue = if (isActive) Color.Green else Color.Red
        )
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color = color.value)
            .clickable { isActive = !isActive },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isActive) "Aktif" else "Pasif",
            color = Color.White
        )
    }
}

/**
 * Örnek 3: Material Bileşen Özelleştirme
 * Bu örnek, Material Button bileşeninin nasıl özelleştirileceğini gösterir.
 * Gradient arka plana sahip özel bir buton.
 */
@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Blue, Color.Cyan)
                )
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Composable
fun CustomButtonExample() {
    GradientButton(
        onClick = { /* İşlem */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Gradient Buton",
            color = Color.White
        )
    }
}

/**
 * Örnek 4: Foundation Katmanı Kullanımı
 * Bu örnek, Material katmanını kullanmadan sadece foundation katmanı
 * bileşenleriyle özel bir buton oluşturmayı gösterir.
 */
@Composable
fun BespokeButton(
    onClick: () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(color = backgroundColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Composable
fun FoundationButtonExample() {
    BespokeButton(
        onClick = { /* İşlem */ },
        backgroundColor = Color.Magenta,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Foundation Buton",
            color = Color.White
        )
    }
}

// Preview fonksiyonları
@Preview(showBackground = true)
@Composable
fun HighLevelAnimationExamplePreview() {
    MaterialTheme {
        HighLevelAnimationExample()
    }
}

@Preview(showBackground = true)
@Composable
fun LowLevelAnimationExamplePreview() {
    MaterialTheme {
        LowLevelAnimationExample()
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonExamplePreview() {
    MaterialTheme {
        CustomButtonExample()
    }
}

@Preview(showBackground = true)
@Composable
fun FoundationButtonExamplePreview() {
    MaterialTheme {
        FoundationButtonExample()
    }
} 