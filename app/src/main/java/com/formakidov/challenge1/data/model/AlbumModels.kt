package com.formakidov.challenge1.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class FeedResponse(val feed: Feed)

@Serializable
data class Feed(val results: List<AlbumDto>)

@Serializable
data class AlbumDto(
    val id: String,
    val name: String,
    val artistName: String,
    val artworkUrl100: String,
    val releaseDate: String,
    val url: String,
    val genres: List<GenreDto>
)

@Serializable
data class GenreDto(val name: String)


// --- Domain Model ---
@Parcelize
@Serializable
data class Album(
    val id: String,
    val name: String,
    val artistName: String,
    val artworkUrl: String,
    val releaseDate: String,
    val appleMusicUrl: String,
    val genres: List<String>,
    val isSaved: Boolean = false
) : Parcelable

fun AlbumDto.toDomain(isSaved: Boolean): Album {
    return Album(
        id = id,
        name = name,
        artistName = artistName,
        artworkUrl = artworkUrl100,
        releaseDate = releaseDate,
        appleMusicUrl = url,
        genres = genres.map { it.name },
        isSaved = isSaved
    )
}
