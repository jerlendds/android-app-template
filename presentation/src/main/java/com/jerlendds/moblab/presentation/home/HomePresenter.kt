package com.jerlendds.moblab.presentation.home

import com.jerlendds.moblab.domain.usecase.ObserveUsersUseCase
import com.jerlendds.moblab.domain.usecase.RefreshUsersUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomePresenter(
    private val observeUsers: ObserveUsersUseCase,
    private val refreshUsers: RefreshUsersUseCase,
) : HomeContract.Presenter {
    private val disposables = CompositeDisposable()
    private var view: HomeContract.View? = null
    private var state = HomeContract.State()

    override fun attach(view: HomeContract.View) {
        this.view = view
        view.render(state)
        disposables.add(
            observeUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { users -> render(state.copy(users = users, error = null)) },
                    { error -> render(state.copy(error = error.message)) },
                ),
        )
        refresh()
    }

    override fun detach() {
        disposables.clear()
        view = null
    }

    override fun refresh() {
        render(state.copy(isLoading = true, error = null))
        disposables.add(
            refreshUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { users -> render(state.copy(isLoading = false, users = users, error = null)) },
                    { error -> render(state.copy(isLoading = false, error = error.message)) },
                ),
        )
    }

    private fun render(nextState: HomeContract.State) {
        state = nextState
        view?.render(nextState)
    }
}
