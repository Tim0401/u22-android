package com.akeno0810.u22.api

import retrofit2.http.*
import rx.Observable

interface ApiUserClient {
    @POST("users/login")
    fun login(@Body user: LoginUser): Observable<JWT>

    @POST("users")
    fun createUser(@Body user: User): Observable<UserResponse>

}