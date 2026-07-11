package com.jerlendds.moblab.data.bus

import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.core.Observable

class DataBus<T : Any>(initialValue: T) {
    private val relay = BehaviorRelay.createDefault(initialValue).toSerialized()

    fun update(value: T) {
        relay.accept(value)
    }

    fun observe(): Observable<T> = relay.distinctUntilChanged().hide()

    fun current(): T = relay.value ?: error("DataBus has no current value")
}
