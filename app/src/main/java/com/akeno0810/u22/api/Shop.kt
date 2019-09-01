package com.akeno0810.u22.api

data class Shop (
    var id: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var questionnaire_id: String? = null,
    var shop_id: String? = null,
    var Answers: List<Boolean>? = null
)