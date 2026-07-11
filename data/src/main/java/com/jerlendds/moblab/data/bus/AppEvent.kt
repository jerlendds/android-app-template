package com.jerlendds.moblab.data.bus

sealed interface AppEvent {
    data object SessionExpired : AppEvent
    data class UsersRefreshed(val count: Int) : AppEvent
    data class ErrorReported(val message: String) : AppEvent
}
