package com.jerlendds.moblab.data.analytics

import android.content.Context
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject

class MixpanelAnalyticsManager(
    context: Context,
    token: String,
) : AnalyticsManager {
    private val mixpanel = MixpanelAPI.getInstance(context, token, true)

    override fun track(event: AnalyticsEvent) {
        mixpanel.track(event.name, JSONObject(event.properties))
    }

    override fun setUserId(userId: String?) {
        if (userId == null) {
            mixpanel.reset()
        } else {
            mixpanel.identify(userId)
        }
    }

    override fun flush() {
        mixpanel.flush()
    }
}
