package com.example.pomodoro.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pomodoro.PomodoroApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PomodoroApplication)

            PomodoroViewModel(
                userPreferencesRepository = application.userPreferencesRepository
            )
        }
    }
}