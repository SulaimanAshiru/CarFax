package net.carfax.api

import net.carfax.api.model.AssignmentModel
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {


    @GET("assignment.json")
    suspend fun getData(): Response<AssignmentModel>

}