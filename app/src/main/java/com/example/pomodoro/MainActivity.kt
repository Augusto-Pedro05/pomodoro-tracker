package com.example.pomodoro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomodoro.Destination
import com.example.pomodoro.ui.theme.PomodoroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Começo da definição da UI
        setContent {
            // Wrapper que define cores e tipografia em escopo global da tela
            PomodoroTheme {
                /* Scaffold é utilizado como um widget que permite uma configuração básica,
                   de forma que os elementos possam ocupar o maior espaço possível sem
                   comprometer os espaços já utilizados pelo Android
                */
                MainView()
            }
        }
    }
}

/* ============ INTERFACE FUNCTIONS (JETPACK COMPOSE) =========== */
@Composable
fun InitialPomodoroScreen(
    modifier: Modifier = Modifier,
    viewModel: PomodoroViewModel = viewModel()
) {
    val progressIndicator by animateFloatAsState(
        targetValue = viewModel.progressIndicator,
        animationSpec = tween(durationMillis = 1000)
    )

    val minutos = viewModel.timeLeft / 60
    val segundos = viewModel.timeLeft % 60
    val time = String.format("%02d:%02d", minutos, segundos)

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.CircularProgressIndicator(
                progress = progressIndicator,
                modifier = Modifier.size(200.dp),
                strokeWidth = 8.dp
            )
            Text(
                text = time,
                fontSize = 48.sp, // Aumentei a fonte para parecer um timer
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            // Cria um botão para cada item do Enum
            PomodoroMode.values().forEach { mode ->
                OutlinedButton(
                    onClick = { viewModel.selectMode(mode) },
                    // Destaca o botão se ele for o modo selecionado
                    border = if (viewModel.currentMode == mode) BorderStroke(2.dp, mode.color) else null
                ) {
                    Text(mode.label, color = if (viewModel.currentMode == mode) mode.color else Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Play Button Pomodoro
        Button(onClick = {
            viewModel.toggleTime()
        }) {
            Text(text = if (viewModel.timerStatus) "Pausar" else "Iniciar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.reset()
            }){
            Text("Resetar")
        }
    }
}
@Composable
fun ScreenPlaceholder(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Tela de $text", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun MainView(){
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
                Destination.Timer -> InitialPomodoroScreen() // Sua tela atual
                Destination.Historico -> ScreenPlaceholder("Histórico")
                Destination.Config -> ScreenPlaceholder("Configurações")
            }
        }
    }
}