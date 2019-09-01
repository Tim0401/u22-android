package com.akeno0810.u22.api

data class UserResponse(
    var username: String ?= null, //"address1": "北海道",
    var email: String ?= null, //"address2": "美唄市",
    var password: String ?= null, //"address3": "上美唄町協和",
    var gender: String ?= null, //"kana1": "ﾎｯｶｲﾄﾞｳ",
    var birthday: String ?= null, //"kana2": "ﾋﾞﾊﾞｲｼ"
    var id: String ?= null, //"address1": "北海道",
    var created_at: String ?= null, //"address2": "美唄市",
    var updated_at: String ?= null, //"address3": "上美唄町協和",
    var Houses: String ?= null //"kana1": "ﾎｯｶｲﾄﾞｳ",
)