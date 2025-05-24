package com.ibrahimcanerdogan.awesomejettutorial.architecture

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument

/**
 * Bu dosya Jetpack Compose'da Navigation kullanımını ele almaktadır.
 * Navigation, uygulama içi ekranlar arası geçişleri yönetmek için kullanılır.
 */

/**
 * Örnek 1: Temel Navigation Kullanımı
 * Bu örnek, basit bir navigation yapısını gösterir.
 */
@Composable
fun BasicNavigationExample(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToProfile = { userId ->
                    navController.navigate("profile/$userId")
                }
            )
        }
        composable(
            route = "profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            ProfileScreen(
                userId = backStackEntry.arguments?.getString("userId") ?: "",
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun HomeScreen(onNavigateToProfile: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Ana Sayfa")
        Button(onClick = { onNavigateToProfile("user123") }) {
            Text("Profile Git")
        }
    }
}

@Composable
fun ProfileScreen(userId: String, onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Profil Sayfası - Kullanıcı: $userId")
        Button(onClick = onNavigateBack) {
            Text("Geri Dön")
        }
    }
}

/**
 * Örnek 2: Nested Navigation
 * Bu örnek, iç içe navigation yapısını gösterir.
 */
@Composable
fun NestedNavigationExample(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        navigation(startDestination = "feed", route = "main") {
            composable("feed") {
                FeedScreen(
                    onNavigateToDetail = { postId ->
                        navController.navigate("main/detail/$postId")
                    }
                )
            }
            composable(
                route = "detail/{postId}",
                arguments = listOf(navArgument("postId") { type = NavType.StringType })
            ) { backStackEntry ->
                DetailScreen(
                    postId = backStackEntry.arguments?.getString("postId") ?: "",
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun FeedScreen(onNavigateToDetail: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Feed Sayfası")
        Button(onClick = { onNavigateToDetail("post123") }) {
            Text("Detay Sayfasına Git")
        }
    }
}

@Composable
fun DetailScreen(postId: String, onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Detay Sayfası - Post: $postId")
        Button(onClick = onNavigateBack) {
            Text("Geri Dön")
        }
    }
}

/**
 * Örnek 3: Bottom Navigation ile Navigation
 * Bu örnek, bottom navigation ile navigation yapısını gösterir.
 */
@Composable
fun BottomNavigationExample(navController: NavHostController) {
    val items = listOf("home", "search", "profile")
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                
                items.forEach { route ->
                    NavigationBarItem(
                        selected = currentRoute == route,
                        onClick = {
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = { /* Icon */ },
                        label = { Text(route) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen(onNavigateToProfile = {}) }
            composable("search") { SearchScreen() }
            composable("profile") { ProfileScreen(userId = "", onNavigateBack = {}) }
        }
    }
}

@Composable
fun SearchScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Arama Sayfası")
    }
}

// Preview fonksiyonları
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(onNavigateToProfile = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(userId = "user123", onNavigateBack = {})
    }
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    MaterialTheme {
        FeedScreen(onNavigateToDetail = {})
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    MaterialTheme {
        DetailScreen(postId = "post123", onNavigateBack = {})
    }
} 