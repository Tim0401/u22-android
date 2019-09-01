package com.akeno0810.u22.api

data class Questionnaire (
    var id: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var event_id: String? = null,
    var event_name: String? = null,
    var dates: List<Date>? = null,
    var shops: List<Shop>? = null
)