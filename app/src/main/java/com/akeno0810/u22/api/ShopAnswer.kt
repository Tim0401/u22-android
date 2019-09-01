package com.akeno0810.u22.api

data class ShopAnswer (
    var id: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var questionnaire_shop_id: String? = null,
    var user: User? = null,
    var userID: String? = null,
    var Ok: Boolean? = null
)