package com.formakidov.challenge1.di

import android.content.Context
import com.formakidov.challenge1.data.api.AppleMusicApi
import com.formakidov.challenge1.data.local.SavedAlbumManager
import com.formakidov.challenge1.data.repository.AlbumRepository
import com.formakidov.challenge1.ui.screens.list.AlbumListViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import java.io.File

private val networkModule = module {
    single { provideJson() }
    single { provideOkHttpClient(androidContext()) }
    single { provideRetrofit(get(), get()) }
    single { provideAppleApi(get()) }
}

private val dataModule = module {
    singleOf(::SavedAlbumManager)
    singleOf(::AlbumRepository)
}

private val uiModule = module {
    viewModelOf(::AlbumListViewModel)
}

val appModule = module {
    includes(networkModule, dataModule, uiModule)
}

private fun provideJson(): Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

private fun provideOkHttpClient(context: Context): OkHttpClient {
    val cacheSize = 10 * 1024 * 1024L
    val cache = Cache(File(context.cacheDir, "http_cache"), cacheSize)

    return OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
    val contentType = "application/json".toMediaType()
    return Retrofit.Builder()
        .baseUrl("https://rss.marketingtools.apple.com/")
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
}

private fun provideAppleApi(retrofit: Retrofit): AppleMusicApi {
    return retrofit.create(AppleMusicApi::class.java)
}
