package com.jerlendds.moblab

import android.content.Context
import com.jerlendds.moblab.data.analytics.AnalyticsEvent
import com.jerlendds.moblab.data.analytics.AnalyticsManager
import com.jerlendds.moblab.data.analytics.MixpanelAnalyticsManager
import com.jerlendds.moblab.data.analytics.NoOpAnalyticsManager
import com.jerlendds.moblab.data.bus.AppDataStore
import com.jerlendds.moblab.data.bus.EventBus
import com.jerlendds.moblab.data.local.AppDatabase
import com.jerlendds.moblab.data.remote.NetworkModule
import com.jerlendds.moblab.data.repository.UserRepositoryImpl
import com.jerlendds.moblab.domain.repository.UserRepository
import com.jerlendds.moblab.domain.usecase.ObserveUsersUseCase
import com.jerlendds.moblab.domain.usecase.RefreshUsersUseCase
import com.jerlendds.moblab.presentation.home.HomePresenter

class AppContainer(context: Context) {
    private val appContext = context.applicationContext

    val eventBus = EventBus()
    val dataStore = AppDataStore()
    val analyticsManager: AnalyticsManager = createAnalyticsManager()

    private val database = AppDatabase.create(appContext)
    private val okHttpClient = NetworkModule.createOkHttpClient(DebugTools.networkInterceptors())
    private val gitHubApi = NetworkModule.createGitHubApi(okHttpClient)

    val userRepository: UserRepository = UserRepositoryImpl(
        gitHubApi = gitHubApi,
        userDao = database.userDao(),
        dataStore = dataStore,
        eventBus = eventBus,
    )

    private val observeUsersUseCase = ObserveUsersUseCase(userRepository)
    private val refreshUsersUseCase = RefreshUsersUseCase(userRepository)

    fun createHomePresenter(): HomePresenter = HomePresenter(
        observeUsers = observeUsersUseCase,
        refreshUsers = refreshUsersUseCase,
    ).also {
        analyticsManager.track(AnalyticsEvent("home_presenter_created"))
    }

    private fun createAnalyticsManager(): AnalyticsManager {
        val token = BuildConfig.MIXPANEL_TOKEN.orEmpty()
        return if (token.isBlank()) {
            NoOpAnalyticsManager()
        } else {
            MixpanelAnalyticsManager(appContext, token)
        }
    }
}
