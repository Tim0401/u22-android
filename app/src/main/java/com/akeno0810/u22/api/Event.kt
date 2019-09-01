package com.akeno0810.u22.api

data class Event (
    var id: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var house_id: String? = null,
    var name: String? = null,
    var candidate_datetime: String? = null,
    var shop_id: String? = null,
    var questionnaires: List<Questionnaire>? = null
)