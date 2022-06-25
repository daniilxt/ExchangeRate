package ru.daniilxt.feature.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(

    @SerializedName("success")
    val isSuccess: Boolean,

    @SerializedName("base")
    val baseCurrency: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("rates")
    val currencyClass: CurrencyNamesResponse
)
