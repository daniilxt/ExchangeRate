package ru.daniilxt.feature.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.daniilxt.feature.data.remote.model.response.CurrencyResponse

interface FeatureApiService {

    @GET("/latest")
    suspend fun getCurrencyList(@Query("base") currencyName: String): Response<CurrencyResponse>
}
