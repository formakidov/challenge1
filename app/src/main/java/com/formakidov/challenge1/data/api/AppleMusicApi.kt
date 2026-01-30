package com.formakidov.challenge1.data.api

import com.formakidov.challenge1.data.model.FeedResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AppleMusicApi {
    @GET("api/v2/{region}/music/most-played/{limit}/albums.json")
    suspend fun getTopAlbums(
        @Path("region") region: String = "de",
        @Path("limit") limit: Int = 100
    ): FeedResponse
}
