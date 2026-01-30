package com.formakidov.challenge1.di

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
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import java.io.File

val appModule = module {

    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    single {
        val cacheSize = 10 * 1024 * 1024L
        val cache = Cache(File(androidContext().cacheDir, "http_cache"), cacheSize)

        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
            .build()
    }

    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl("https://rss.marketingtools.apple.com/")
            .client(get())
            .addConverterFactory(get<Json>().asConverterFactory(contentType))
            .build()
            .create(AppleMusicApi::class.java)
    }

    single { SavedAlbumManager(androidContext()) }

    single { AlbumRepository(get(), get()) }

    viewModel { AlbumListViewModel(get(), get()) }
}
