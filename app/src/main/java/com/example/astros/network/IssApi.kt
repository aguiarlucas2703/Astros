package com.example.astros.network

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// API: https://api.wheretheiss.at/v1/satellites/25544
data class IssResponse(
    @SerializedName("name") val name: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("altitude") val altitude: Double,
    @SerializedName("velocity") val velocity: Double,
    @SerializedName("visibility") val visibility: String,
    @SerializedName("timestamp") val timestamp: Long
)

interface IssApiService {
    @GET("satellites/25544")
    suspend fun getCurrentPosition(): IssResponse
}

object IssNetwork {
    private const val BASE_URL = "https://api.wheretheiss.at/v1/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val issApiService: IssApiService = retrofit.create(IssApiService::class.java)
}
