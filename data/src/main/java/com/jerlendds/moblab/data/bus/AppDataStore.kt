package com.jerlendds.moblab.data.bus

import com.jerlendds.moblab.domain.model.User

class AppDataStore {
    val users = DataBus<List<User>>(emptyList())
}
