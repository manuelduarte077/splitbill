package dev.donmanuel.app.splitbill.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainRow(
    title: String,
    total: Double,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Text(
            title,
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            "$$total",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}