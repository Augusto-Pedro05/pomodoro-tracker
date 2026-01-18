package com.example.pomodoro.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ConfigItem(
    title: String,
    value: Float,
    color: Color,
    steps: Int,
    valueRange: Float,
    onValueChange: (Float) -> Unit
){
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween // Texto na esq, Valor na dir
        ) {
            Text(text = title, fontWeight = FontWeight.SemiBold)
            Text(
                text = "${value.toInt()} min",
                fontWeight = FontWeight.Bold,
                color = color
            )
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..valueRange, // Mínimo 1 min, Máximo 60 min
            steps = steps, // Faz o slider pular de 1 em 1 (sem números quebrados)
            colors = SliderDefaults.colors(
                thumbColor = color,
                activeTrackColor = color,
                inactiveTrackColor = color.copy(alpha = 0.2f)
            )
        )
    }
}