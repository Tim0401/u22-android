package com.akeno0810.u22.api

import retrofit2.http.*
import rx.Observable

interface ApiClient {

    @POST("houses")
    fun createHouse(@Body group: GroupName): Observable<Group>

    @GET("houses/{id}")
    fun getHouse(@Path("id") id: Int): Observable<Group>

    @POST("houses/{id}/users/{user}")
    fun joinHouse(@Path("id") id: Int, @Path("user") userId: Int, @Body group: GroupName): Observable<Group>

    @PUT("houses/{id}/users/{user}")
    fun updateHouse(@Path("id") id: Int, @Path("user") userId: Int, @Body group: GroupAdmin): Observable<Group>

    @GET("houses/{id}/events")
    fun getHouseEvents(@Path("id") id: Int): Observable<List<Event>>

    @GET("houses/{id}/events/{eid}/questionnaires")
    fun getHouseEventQuestionnaires(@Path("id") id: Int, @Path("eid") eid: Int): Observable<List<Questionnaire>>

    @GET("houses/{id}/events/{eid}/questionnaires/{qid}")
    fun getHouseEventQuestionnaire(@Path("id") id: Int, @Path("eid") eid: Int, @Path("qid") qid: Int): Observable<Questionnaire>

    @POST("houses/{id}/events/{eid}/questionnaires/{qid}/dates/{did}/answers")
    fun setHouseEventQuestionnaireDateAnswer(@Path("id") id: Int, @Path("eid") eid: Int, @Path("qid") qid: Int,@Path("did") did: Int,@Body answer: Answer): Observable<DateAnswer>

    @POST("houses/{id}/events/{eid}/questionnaires/{qid}/shops/{did}/answers")
    fun setHouseEventQuestionnaireShopAnswer(@Path("id") id: Int, @Path("eid") eid: Int, @Path("qid") qid: Int,@Path("did") did: Int,@Body answer: Answer): Observable<ShopAnswer>

    @GET("houses/{id}/events/{eid}/questionnaires/{qid}/dates/{did}/answers")
    fun getHouseEventQuestionnaireDateAnswer(@Path("id") id: Int, @Path("eid") eid: Int, @Path("qid") qid: Int,@Path("did") did: Int): Observable<List<DateAnswer>>

    @GET("houses/{id}/events/{eid}/questionnaires/{qid}/shops/{did}/answers")
    fun getHouseEventQuestionnaireShopAnswer(@Path("id") id: Int, @Path("eid") eid: Int, @Path("qid") qid: Int,@Path("did") did: Int): Observable<List<ShopAnswer>>

}