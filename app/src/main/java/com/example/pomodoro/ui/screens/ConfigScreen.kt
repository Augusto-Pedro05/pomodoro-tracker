package com.example.pomodoro.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pomodoro.model.PomodoroMode
import com.example.pomodoro.ui.components.ConfigItem
import com.example.pomodoro.viewModel.PomodoroViewModel

@Composable
fun ConfigScreen(
    viewModel: PomodoroViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text("Configurações", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(24.dp))
        ConfigItem(
            title = "Tempo de Foco",
            value = viewModel.focusTimeMinutes,
            color = Color(0xFFF44336), // Vermelho (ou use PomodoroMode.FOCO.color)
            steps = 11,
            valueRange = 120f,
            onValueChange = { newValue ->
                viewModel.focusTimeMinutes = newValue
                viewModel.updateFocusTime(newValue)
                if(viewModel.currentMode == PomodoroMode.FOCUS && !viewModel.timerStatus && (viewModel.progressIndicator == 1f || viewModel.timeLeft <= 0)){
                    viewModel.reset()
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        ConfigItem(
            title = "Pausa Curta",
            value = viewModel.shortBreakTimeMinutes,
            color = Color(0xFF4CAF50),// Verde
            steps = 5,
            valueRange = 30f,
            onValueChange = { newValue ->
                viewModel.shortBreakTimeMinutes = newValue
                viewModel.updateShortBreakTime(newValue)
                if(viewModel.currentMode == PomodoroMode.SHORT_BREAK && !viewModel.timerStatus && (viewModel.progressIndicator == 1f || viewModel.timeLeft <= 0)){
                    viewModel.reset()
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        ConfigItem(
            title = "Pausa Longa",
            value = viewModel.longBreakTimeMinutes,
            color = Color(0xFF2196F3),// Azul
            steps = 11,
            valueRange = 120f,
            onValueChange = { newValue ->
                viewModel.longBreakTimeMinutes = newValue
                viewModel.updateLongBreakTime(newValue)
                if(viewModel.currentMode == PomodoroMode.LONG_BREAK && !viewModel.timerStatus && (viewModel.progressIndicator == 1f || viewModel.timeLeft <= 0)){
                    viewModel.reset()
                }
            }
        )
    }
}