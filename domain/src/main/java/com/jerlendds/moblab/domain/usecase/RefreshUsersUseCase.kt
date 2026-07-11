package com.jerlendds.moblab.domain.usecase

import com.jerlendds.moblab.domain.model.User
import com.jerlendds.moblab.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single

class RefreshUsersUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Single<List<User>> = userRepository.refreshUsers()
}
