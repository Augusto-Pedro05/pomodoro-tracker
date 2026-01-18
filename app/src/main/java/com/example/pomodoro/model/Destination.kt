package com.example.pomodoro.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val title: String, val icon: ImageVector) {
    // USE Icons.Filled EM VEZ DE Icons.Default
    object Timer : Destination("Timer", Icons.Filled.Home)
    object Historico : Destination("Histórico", Icons.AutoMirrored.Filled.List)
    object Config : Destination("Ajustes", Icons.Filled.Settings)
}