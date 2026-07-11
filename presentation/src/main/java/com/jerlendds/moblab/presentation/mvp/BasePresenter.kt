package com.jerlendds.moblab.presentation.mvp

interface BasePresenter<V : BaseView> {
    fun attach(view: V)
    fun detach()
}
