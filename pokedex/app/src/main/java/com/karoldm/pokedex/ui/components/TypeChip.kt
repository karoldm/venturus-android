package com.karoldm.pokedex.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.karoldm.pokedex.ui.theme.gold
import com.karoldm.pokedex.ui.theme.white

@Composable
fun TypeChip(type: String) {
    Surface(
        color = gold,
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = type.replaceFirstChar { it.uppercase() },
            color = white,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
