package com.formakidov.challenge1.data.repository

import com.formakidov.challenge1.data.api.AppleMusicApi
import com.formakidov.challenge1.data.local.SavedAlbumManager
import com.formakidov.challenge1.data.model.Album
import com.formakidov.challenge1.data.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class AlbumRepository(
    private val api: AppleMusicApi,
    private val savedManager: SavedAlbumManager
) {
    val albums: Flow<Result<List<Album>>> = combine(
        flow {
            try {
                val response = api.getTopAlbums()
                emit(Result.success(response.feed.results))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        },
        savedManager.savedIds
    ) { apiResult, savedIds ->
        apiResult.map { dtoList ->
            dtoList.map { dto ->
                dto.toDomain(isSaved = savedIds.contains(dto.id))
            }
        }
    }

    suspend fun toggleSave(albumId: String) = savedManager.toggleSave(albumId)
}
