package com.jerlendds.moblab.data.remote

import com.jerlendds.moblab.domain.model.User
import com.squareup.moshi.Json

data class UserDto(
    val id: Long,
    val login: String,
    @Json(name = "avatar_url") val avatarUrl: String?,
) {
    fun toDomain(): User = User(
        id = id,
        login = login,
        avatarUrl = avatarUrl.orEmpty(),
    )
}
