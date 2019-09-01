package com.akeno0810.u22.api

data class Group (
    var id: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var name: String? = null,
    var user: List<User>? = null,
    var house_user: List<HouseUser>? = null,
    var events: List<Event>? = null
)