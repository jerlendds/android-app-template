package com.jerlendds.moblab.domain.usecase

import com.jerlendds.moblab.domain.model.User
import com.jerlendds.moblab.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Observable

class ObserveUsersUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Observable<List<User>> = userRepository.observeUsers()
}
