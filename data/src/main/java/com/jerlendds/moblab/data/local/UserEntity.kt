package com.jerlendds.moblab.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jerlendds.moblab.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    val login: String,
    val avatarUrl: String,
) {
    fun toDomain(): User = User(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
    )

    companion object {
        fun fromDomain(user: User): UserEntity = UserEntity(
            id = user.id,
            login = user.login,
            avatarUrl = user.avatarUrl,
        )
    }
}
