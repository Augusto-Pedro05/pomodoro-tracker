package com.example.pomodoro.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomodoro.model.Destination
import com.example.pomodoro.viewModel.AppViewModelProvider
import com.example.pomodoro.viewModel.PomodoroViewModel

@Composable
fun MainView() {
    val sharedViewModel: PomodoroViewModel = viewModel(factory = AppViewModelProvider.Factory)
    var currentScreen by remember { mutableStateOf<Destination>(Destination.Timer) }
    val screens = listOf(Destination.Timer, Destination.Historico, Destination.Config)

    Scaffold(
        bottomBar = {
            NavigationBar {
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen }
                    )
                }
            }
        }
    ) { innerPadding ->
        // 2. O Conteúdo principal muda conforme o estado
        // O Box e o padding são essenciais para o conteúdo não ficar atrás da barra
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                Destination.Timer -> TimerScreen(viewModel = sharedViewModel) // Sua tela atual
                Destination.Historico -> HistoryScreen()
                Destination.Config -> ConfigScreen(viewModel = sharedViewModel)
            }
        }
    }
}