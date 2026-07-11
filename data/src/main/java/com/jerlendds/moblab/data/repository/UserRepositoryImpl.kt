package com.jerlendds.moblab.data.repository

import com.jerlendds.moblab.data.bus.AppDataStore
import com.jerlendds.moblab.data.bus.AppEvent
import com.jerlendds.moblab.data.bus.EventBus
import com.jerlendds.moblab.data.local.UserDao
import com.jerlendds.moblab.data.local.UserEntity
import com.jerlendds.moblab.data.remote.GitHubApi
import com.jerlendds.moblab.domain.model.User
import com.jerlendds.moblab.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class UserRepositoryImpl(
    private val gitHubApi: GitHubApi,
    private val userDao: UserDao,
    private val dataStore: AppDataStore,
    private val eventBus: EventBus,
) : UserRepository {
    override fun observeUsers(): Observable<List<User>> = userDao.observeUsers()
        .map { entities -> entities.map(UserEntity::toDomain) }
        .doOnNext(dataStore.users::update)
        .toObservable()

    override fun refreshUsers(): Single<List<User>> = Single.fromCallable {
        val response = gitHubApi.getUsers().execute()
        if (!response.isSuccessful) {
            error("Users request failed with HTTP ${response.code()}")
        }

        val users = response.body().orEmpty().map { dto -> dto.toDomain() }
        userDao.upsertUsers(users.map(UserEntity::fromDomain))
        dataStore.users.update(users)
        eventBus.post(AppEvent.UsersRefreshed(users.size))
        users
    }.subscribeOn(Schedulers.io())

    override fun clearUsers(): Completable = Completable.fromAction {
        userDao.clearUsers()
        dataStore.users.update(emptyList())
    }.subscribeOn(Schedulers.io())
}
