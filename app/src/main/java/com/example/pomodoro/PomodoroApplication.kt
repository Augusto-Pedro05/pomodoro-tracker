package com.example.pomodoro

import android.app.Application
import com.example.pomodoro.data.UserPreferencesRepository

class PomodoroApplication : Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        // Inicializa o repositório passando o contexto da aplicação
        userPreferencesRepository = UserPreferencesRepository(this)
    }
}