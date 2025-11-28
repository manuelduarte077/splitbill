package dev.donmanuel.app.splitbill

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.donmanuel.app.splitbill.ui.theme.colors
import dev.donmanuel.app.splitbill.views.HomeView

@Composable
fun App() {
    MaterialTheme(colorScheme = colors) {
        HomeView()
    }
}