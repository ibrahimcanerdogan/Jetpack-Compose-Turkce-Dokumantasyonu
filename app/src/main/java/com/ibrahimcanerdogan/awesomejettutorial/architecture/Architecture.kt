package com.ibrahimcanerdogan.awesomejettutorial.architecture

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Bu dosya Jetpack Compose'da Mimari (Architecture) konusunu ele almaktadır.
 * UI State yönetimi, State Holder pattern ve ViewModel entegrasyonu konularını içerir.
 */

/**
 * Örnek 1: UI State Interface
 * Bu örnek, UI state'in nasıl tanımlanacağını gösterir.
 * @Stable annotation'ı ile type stability sağlanır.
 */
@Stable
interface TodoUiState {
    val isLoading: Boolean
    val error: String?
    val data: List<String>
}

/**
 * Örnek 2: State Holder Pattern
 * Bu örnek, state yönetiminin bir sınıfa nasıl taşınacağını gösterir.
 * State holder pattern kullanımını içerir.
 */
@Stable
class TodoListStateHolder {
    private val _todos = mutableStateOf<List<String>>(emptyList())
    val todos: List<String> get() = _todos.value

    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    private val _error = mutableStateOf<String?>(null)
    val error: String? get() = _error.value

    fun addTodo(todo: String) {
        _todos.value = _todos.value + todo
    }

    fun removeTodo(todo: String) {
        _todos.value = _todos.value - todo
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setError(error: String?) {
        _error.value = error
    }
}


@Composable
fun rememberTodoListStateHolder(): TodoListStateHolder {
    return remember { TodoListStateHolder() }
}

@Composable
fun TodoListScreen() {
    val stateHolder = rememberTodoListStateHolder()
    var newTodo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Yeni todo ekleme alanı
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newTodo,
                onValueChange = { newTodo = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Yeni görev ekle") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newTodo.isNotBlank()) {
                        stateHolder.addTodo(newTodo)
                        newTodo = ""
                    }
                }
            ) {
                Text("Ekle")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Todo listesi
        if (stateHolder.isLoading) {
            CircularProgressIndicator()
        } else {
            stateHolder.todos.forEach { todo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = todo,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { stateHolder.removeTodo(todo) }
                    ) {
                        Text("Sil")
                    }
                }
            }
        }

        // Hata mesajı
        stateHolder.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/**
 * Örnek 3: ViewModel ile State Yönetimi
 * Bu örnek, ViewModel kullanarak state yönetimini gösterir.
 * State'in ViewModel'de nasıl tutulacağını ve UI'da nasıl kullanılacağını içerir.
 */
class TodoViewModel : ViewModel() {
    private val _todos = mutableStateOf<List<String>>(emptyList())
    val todos: State<List<String>> = _todos

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun addTodo(todo: String) {
        _todos.value = _todos.value + todo
    }

    fun removeTodo(todo: String) {
        _todos.value = _todos.value - todo
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setError(error: String?) {
        _error.value = error
    }
}

@Composable
fun TodoViewModelScreen(
    viewModel: TodoViewModel = viewModel()
) {
    var newTodo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Yeni todo ekleme alanı
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newTodo,
                onValueChange = { newTodo = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Yeni görev ekle") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newTodo.isNotBlank()) {
                        viewModel.addTodo(newTodo)
                        newTodo = ""
                    }
                }
            ) {
                Text("Ekle")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Todo listesi
        if (viewModel.isLoading.value) {
            CircularProgressIndicator()
        } else {
            viewModel.todos.value.forEach { todo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = todo,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { viewModel.removeTodo(todo) }
                    ) {
                        Text("Sil")
                    }
                }
            }
        }

        // Hata mesajı
        viewModel.error.value?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

// Preview fonksiyonları
@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    MaterialTheme {
        TodoListScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun TodoViewModelScreenPreview() {
    MaterialTheme {
        TodoViewModelScreen()
    }
} 