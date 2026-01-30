package com.formakidov.challenge1.data.api

import com.formakidov.challenge1.data.model.FeedResponse
import retrofit2.http.GET

interface AppleMusicApi {
    @GET("api/v2/de/music/most-played/100/albums.json")
    suspend fun getTopAlbums(): FeedResponse
}
