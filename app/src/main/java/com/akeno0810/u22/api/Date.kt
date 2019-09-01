package com.akeno0810.u22.api

data class Date (
    var id: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var questionnaire_id: String? = null,
    var candidate_datetime: String? = null,
    var Answers: List<Boolean>? = null
)