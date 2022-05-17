package net.carfax.api

import kotlinx.coroutines.Dispatchers
import net.carfax.api.ApiInterface
import net.carfax.api.BaseRepository
import net.carfax.api.ResponseWrapper
import javax.inject.Inject

class GetDetailsRepository @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository() {

    // assignment api - GET
    suspend fun getData(): ResponseWrapper<Any?> {
        return baseApiCall(Dispatchers.IO) { apiInterface.getData() }
    }
}