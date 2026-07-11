package com.jerlendds.moblab.data.analytics

data class AnalyticsEvent(
    val name: String,
    val properties: Map<String, Any?> = emptyMap(),
)
