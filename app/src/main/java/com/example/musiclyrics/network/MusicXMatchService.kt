package com.example.musiclyrics.network

import com.example.musiclyrics.network.properties.search.Root
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://api.musixmatch.com/ws/1.1/"
private const val API_KEY = "fb20e5b416b5d8f3bb484102abca1638"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MusicXMatchService {

    //https://api.musixmatch.com/ws/1.1/track.search?format=jsonp&callback=callback&q_track=say&apikey=fb20e5b416b5d8f3bb484102abca1638
 @GET("get /track.search")
 fun searchAny(@Query ("q_track") queryTrack: String, @Query("apikey") apiKey: String):
        Deferred<Root>

}

object MusicXMatch {
    val retrofitService : MusicXMatchService by lazy {
        retrofit.create(MusicXMatchService::class.java)
    }
}