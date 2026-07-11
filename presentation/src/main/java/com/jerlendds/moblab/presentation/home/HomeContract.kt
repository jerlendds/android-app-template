package com.jerlendds.moblab.presentation.home

import com.jerlendds.moblab.domain.model.User
import com.jerlendds.moblab.presentation.mvp.BasePresenter
import com.jerlendds.moblab.presentation.mvp.BaseView

object HomeContract {
    data class State(
        val isLoading: Boolean = false,
        val users: List<User> = emptyList(),
        val error: String? = null,
    )

    interface View : BaseView {
        fun render(state: State)
    }

    interface Presenter : BasePresenter<View> {
        fun refresh()
    }
}
