package com.karoldm.pokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.karoldm.pokedex.ui.theme.black
import com.karoldm.pokedex.ui.theme.blue


@Composable
fun StatBar(statName: String, value: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = statName.uppercase(),
            style = MaterialTheme.typography.bodySmall,
            color = black
        )
        LinearProgressIndicator(
            progress = (value / 150f).coerceIn(0f, 1f),
            color = blue,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}