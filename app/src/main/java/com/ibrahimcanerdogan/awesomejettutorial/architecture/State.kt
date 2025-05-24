package com.ibrahimcanerdogan.awesomejettutorial.architecture

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Bu dosya Jetpack Compose'da State (Durum) yönetimini ele almaktadır.
 * Temel state yönetimi, state hoisting ve state kaydetme konularını içerir.
 */

/**
 * Örnek 1: Temel State Yönetimi
 * Bu örnek, Compose'da temel state yönetimini gösterir.
 * remember ve mutableStateOf kullanımını içerir.
 */
@Composable
fun BasicStateExample() {
    // State tanımlama
    var count by remember { mutableStateOf(0) }

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

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { count++ }) {
            Text("Arttır")
        }
    }
}

/**
 * Örnek 2: State Hoisting
 * Bu örnek, state'in nasıl yukarı taşınacağını gösterir.
 * State'i composable fonksiyonun parametresi olarak alıyoruz.
 */
@Composable
fun StateHoistingExample(
    count: Int,
    onCountChange: (Int) -> Unit
) {
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onCountChange(count + 1) }) {
            Text("Arttır")
        }
    }
}

/**
 * Örnek 3: State Kaydetme
 * Bu örnek, state'in nasıl kaydedileceğini gösterir.
 * rememberSaveable kullanarak state'i Bundle'a kaydediyoruz.
 */
@Composable
fun StateSavingExample() {
    var savedCount by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Kaydedilen Sayaç: $savedCount",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { savedCount++ }) {
            Text("Arttır ve Kaydet")
        }
    }
}

/**
 * Örnek 4: State Holder Sınıfı
 * Bu örnek, state yönetimini bir sınıfa taşımayı gösterir.
 * State holder pattern kullanımını içerir.
 */
@Stable
class CounterStateHolder {
    var count by mutableStateOf(0)
        private set

    fun increment() {
        count++
    }
}

@Composable
fun rememberCounterStateHolder(): CounterStateHolder {
    return remember { CounterStateHolder() }
}

@Composable
fun StateHolderExample() {
    val stateHolder = rememberCounterStateHolder()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "State Holder Sayaç: ${stateHolder.count}",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { stateHolder.increment() }) {
            Text("Arttır")
        }
    }
}

/**
 * Örnek 5: ViewModel ile State Yönetimi
 * Bu örnek, ViewModel kullanarak state yönetimini gösterir.
 * State'in ViewModel'de nasıl tutulacağını ve UI'da nasıl kullanılacağını içerir.
 */
class CounterViewModel : androidx.lifecycle.ViewModel() {
    private val _count = mutableStateOf(0)
    val count: State<Int> = _count

    fun increment() {
        _count.value++
    }
}

@Composable
fun ViewModelStateExample(
    counterViewModel: CounterViewModel = viewModel()
) {
    val count by counterViewModel.count

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ViewModel Sayaç: $count",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { counterViewModel.increment() }) {
            Text("Arttır")
        }
    }
}

// Preview fonksiyonları
@Preview(showBackground = true)
@Composable
fun BasicStateExamplePreview() {
    MaterialTheme {
        BasicStateExample()
    }
}

@Preview(showBackground = true)
@Composable
fun StateHoistingExamplePreview() {
    MaterialTheme {
        StateHoistingExample(
            count = 0,
            onCountChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StateSavingExamplePreview() {
    MaterialTheme {
        StateSavingExample()
    }
}

@Preview(showBackground = true)
@Composable
fun StateHolderExamplePreview() {
    MaterialTheme {
        StateHolderExample()
    }
}

@Preview(showBackground = true)
@Composable
fun ViewModelStateExamplePreview() {
    MaterialTheme {
        ViewModelStateExample()
    }
} 