package com.example.musiclyrics.di

import com.example.musiclyrics.BASE_URL_MUSICXMATCH
import com.example.musiclyrics.network.MusicXMatchApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val clientModule = module {
    val interceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        chain.proceed(
            originalRequest.newBuilder().url(originalRequest.url).build()
        )
    }

    single {
        OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }
}

val networkModule = module {
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL_MUSICXMATCH) //192.168.1.112 //192.168.1.20 //192.168.43.203:8080
            .addConverterFactory(MoshiConverterFactory.create(this.get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(this.get())
            .build()
    }

    factory { this.get<Retrofit>().create<MusicXMatchApi.SearchTrack>() }

    factory { this.get<Retrofit>().create<MusicXMatchApi.GetAlbum>() }

    factory { this.get<Retrofit>().create<MusicXMatchApi.GetLyrics>() }

    factory { this.get<Retrofit>().create<MusicXMatchApi.GetTrackFromISRC>() }

}