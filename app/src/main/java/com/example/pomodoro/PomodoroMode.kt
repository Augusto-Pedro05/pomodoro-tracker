package com.example.pomodoro

import androidx.compose.ui.graphics.Color

enum class PomodoroMode (
    val color : Color,
    val label : String,
){
    SHORT_BREAK(Color(0xFF4CAF50), "Pausa Curta"),
    FOCUS(Color(0xFFF44336), "Foco"), // Vermelho// Verde
    LONG_BREAK(Color(0xFF2196F3), "Pausa Longa") // Azul
}