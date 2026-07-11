package com.jerlendds.moblab.data.analytics

class NoOpAnalyticsManager : AnalyticsManager {
    override fun track(event: AnalyticsEvent) = Unit
    override fun setUserId(userId: String?) = Unit
    override fun flush() = Unit
}
