package com.example.pomodoro.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.data.UserPreferencesRepository
import com.example.pomodoro.model.PomodoroMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PomodoroViewModel (
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var focusTimeMinutes by mutableFloatStateOf(60f)
    var shortBreakTimeMinutes by mutableFloatStateOf(10f)
    var longBreakTimeMinutes by mutableFloatStateOf(30f)
    var currentMode by mutableStateOf(PomodoroMode.FOCUS)
        private set
    var timerStatus by mutableStateOf(false)
        private set
    var timeLeft by mutableStateOf(getTempoPeloModo(currentMode))
        private set
    var progressIndicator by mutableStateOf(1.0f)
        private set
    private var timerJob: Job? = null

    init {
        // Assim que o ViewModel iniciar, carregamos os dados do disco
        viewModelScope.launch {
            // Coleta os valores salvos. O combine junta os 3 fluxos.
            // Mas para simplificar, vamos lançar 3 coletas separadas:

            launch {
                userPreferencesRepository.focusTimeFlow.collect { save ->
                    focusTimeMinutes = save
                    // Se o modo atual for FOCO e estiver parado, atualiza o timer visual também
                    if (currentMode == PomodoroMode.FOCUS && !timerStatus) reset()
                }
            }
            launch {
                userPreferencesRepository.shortBreakFlow.collect { save ->
                    shortBreakTimeMinutes = save
                    if (currentMode == PomodoroMode.SHORT_BREAK && !timerStatus) reset()
                }
            }
            launch {
                userPreferencesRepository.longBreakFlow.collect { save ->
                    longBreakTimeMinutes = save
                    if (currentMode == PomodoroMode.LONG_BREAK && !timerStatus) reset()
                }
            }
        }
    }
    
    private fun getTempoPeloModo(modo: PomodoroMode): Long {
        val minutosConfigurados = when (modo) {
            PomodoroMode.FOCUS -> focusTimeMinutes
            PomodoroMode.SHORT_BREAK -> shortBreakTimeMinutes
            PomodoroMode.LONG_BREAK -> longBreakTimeMinutes
        }
        return (minutosConfigurados * 60).toLong() // Converte para segundos
    }
    fun selectMode(newMode : PomodoroMode){
        pause()
        currentMode = newMode
        timeLeft = getTempoPeloModo(newMode)
        progressIndicator = 1f
    }
    fun toggleTime(){
        if(timerStatus){
            pause()
        }else{
            start()
        }
    }

    private fun start() {
        if(timeLeft <= 0) return
        timerStatus = true
        timerJob = viewModelScope.launch {
            while(timeLeft > 0){
                delay(1000L)
                timeLeft--
                progressIndicator = timeLeft.toFloat() / getTempoPeloModo(currentMode).toFloat()
            }
            timerStatus = false
            progressIndicator = 0f
        }
    }

    private fun pause() {
        timerStatus = false
        timerJob?.cancel()
    }

    fun reset(){
        timerStatus = false
        timeLeft = getTempoPeloModo(currentMode)
        progressIndicator = 1f
        timerJob?.cancel()
    }

    fun updateFocusTime(novoValor: Float) {
        focusTimeMinutes = novoValor
        viewModelScope.launch {
            userPreferencesRepository.saveFocusTime(novoValor)
        }
    }

    fun updateShortBreakTime(novoValor: Float) {
        shortBreakTimeMinutes = novoValor
        viewModelScope.launch { userPreferencesRepository.saveShortBreak(novoValor) }
    }

    fun updateLongBreakTime(novoValor: Float) {
        longBreakTimeMinutes = novoValor
        viewModelScope.launch { userPreferencesRepository.saveLongBreak(novoValor) }
    }
}