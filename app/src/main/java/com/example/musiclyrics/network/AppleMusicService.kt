package com.example.musiclyrics.network

import com.example.musiclyrics.BASE_URL_APPLEMUSIC
import com.example.musiclyrics.network.properties.search.track.Root
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


private val httpLogger: HttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    .addInterceptor(httpLogger)


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL_APPLEMUSIC)

    .client(httpClient.build())
    .build()

interface AppleMusicService {

    @GET("catalog/us/search")
    fun searchAny(@Header("Authorization") authorization: String, @Query ("term") queryAny: String, @Query ("types") types: String):
            Deferred<Root>

}

object AppleMusic {
    val retrofitService : AppleMusicService by lazy {
        retrofit.create(AppleMusicService::class.java)
    }
}