package com.jerlendds.moblab.data.bus

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable

class EventBus {
    private val relay = PublishRelay.create<AppEvent>().toSerialized()

    fun post(event: AppEvent) {
        relay.accept(event)
    }

    fun events(): Observable<AppEvent> = relay.hide()

    inline fun <reified T : AppEvent> eventsOfType(): Observable<T> = relay.ofType(T::class.java)
}
