package com.example.musiclyrics.network

import com.example.musiclyrics.BASE_URL_MUSICXMATCH
import com.example.musiclyrics.network.properties.search.track.Root
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


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
    .baseUrl(BASE_URL_MUSICXMATCH)
    .client(httpClient.build())
    .build()

interface MusicXMatchService {
    //https://api.musixmatch.com/ws/1.1/get/track.search?q_track=<TRACK_NAME>&apikey=<API_KEY>
    @GET("track.search")
    fun searchAny(@Query ("q_track") queryTrack: String, @Query("apikey") apiKey: String):
        Deferred<Root>
    @GET("album.get")
    fun getImageAlbum(@Query ("album_id") albumId: Long, @Query("apikey") apiKey: String):
            Deferred<com.example.musiclyrics.network.properties.result.Root>
    @GET("track.lyrics.get")
    fun getLyrics(@Query ("track_id") trackId: Long, @Query("apikey") apiKey: String):
            Deferred<com.example.musiclyrics.network.properties.lyrics.Root>
}




object MusicXMatch {
    val retrofitService : MusicXMatchService by lazy {
        retrofit.create(MusicXMatchService::class.java)
    }
}