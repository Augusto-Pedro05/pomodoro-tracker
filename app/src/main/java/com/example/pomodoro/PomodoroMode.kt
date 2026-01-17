package com.example.pomodoro

import androidx.compose.ui.graphics.Color

enum class PomodoroMode (
    val timeSeconds : Long,
    val color : Color,
    val label : String,
){
    SHORT_BREAK(10 * 60L, Color(0xFF4CAF50), "Pausa Curta"),
    FOCUS(50 * 60L, Color(0xFFF44336), "Foco"), // Vermelho// Verde
    LONG_BREAK(25 * 60L, Color(0xFF2196F3), "Pausa Longa") // Azul
}