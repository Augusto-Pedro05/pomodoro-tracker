package com.example.pomodoro

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PomodoroViewModel : ViewModel() {

    var tempoFocoMinutos by mutableFloatStateOf(60f)
    var tempoCurtoMinutos by mutableFloatStateOf(10f)
    var tempoLongoMinutos by mutableFloatStateOf(30f)
    var currentMode by mutableStateOf(PomodoroMode.FOCUS)
        private set
    var timerStatus by mutableStateOf(false)
        private set
    var timeLeft by mutableStateOf(getTempoPeloModo(currentMode))
        private set
    var progressIndicator by mutableStateOf(1.0f)
        private set
    private var timerJob: Job? = null

    private fun getTempoPeloModo(modo: PomodoroMode): Long {
        val minutosConfigurados = when (modo) {
            PomodoroMode.FOCUS -> tempoFocoMinutos
            PomodoroMode.SHORT_BREAK -> tempoCurtoMinutos
            PomodoroMode.LONG_BREAK -> tempoLongoMinutos
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
}