package com.jerlendds.moblab.domain.repository

import com.jerlendds.moblab.domain.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun observeUsers(): Observable<List<User>>
    fun refreshUsers(): Single<List<User>>
    fun clearUsers(): Completable
}
