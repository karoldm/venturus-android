package com.karoldm.pokedex.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.karoldm.pokedex.ui.theme.black
import com.karoldm.pokedex.ui.theme.red

@Composable
fun FilterMenu(
    label: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    items: List<String>,
    onSelect: (String) -> Unit
) {
    Box {
        Button(
            onClick = { onExpandChange(!expanded) },
            colors = ButtonDefaults.buttonColors(
                containerColor = red
            )
        ) {
            Text(label)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandChange(false) }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item, color = black) },
                    onClick = { onSelect(item) }
                )
            }
        }
    }
}
