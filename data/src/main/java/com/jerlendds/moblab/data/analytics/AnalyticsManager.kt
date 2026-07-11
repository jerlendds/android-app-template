package com.jerlendds.moblab.data.analytics

interface AnalyticsManager {
    fun track(event: AnalyticsEvent)
    fun setUserId(userId: String?)
    fun flush()
}
