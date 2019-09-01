package com.akeno0810.u22.api

data class User(
    var username: String ?= null, //"address1": "北海道",
    var email: String ?= null, //"address2": "美唄市",
    var password: String ?= null, //"address3": "上美唄町協和",
    var gender: String ?= null, //"kana1": "ﾎｯｶｲﾄﾞｳ",
    var birthday: String ?= null //"kana2": "ﾋﾞﾊﾞｲｼ"
)