package com.formakidov.challenge1.data.repository

import app.cash.turbine.test
import com.formakidov.challenge1.data.api.AppleMusicApi
import com.formakidov.challenge1.data.local.SavedAlbumManager
import com.formakidov.challenge1.data.model.AlbumDto
import com.formakidov.challenge1.data.model.Feed
import com.formakidov.challenge1.data.model.FeedResponse
import com.formakidov.challenge1.data.model.GenreDto
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AlbumRepositoryTest {

    private val mockApi = mockk<AppleMusicApi>()
    private val mockSavedManager = mockk<SavedAlbumManager>()

    @Test
    fun `test album flow emits success with correct saved status`() = runTest {
        val album1 = createAlbumDto("123", "aaa")
        val album2 = createAlbumDto("456", "bbb")
        val mockResponse = FeedResponse(Feed(listOf(album1, album2)))

        coEvery { mockApi.getTopAlbums() } returns mockResponse
        every { mockSavedManager.savedIds } returns flowOf(setOf("123"))

        val repository = AlbumRepository(mockApi, mockSavedManager)

        repository.albums.test {
            val result = awaitItem()

            assertTrue(result.isSuccess)
            val list = result.getOrThrow()

            assertEquals(2, list.size)

            val domainAlbum1 = list.find { it.id == "123" }!!
            assertEquals(true, domainAlbum1.isSaved)

            val domainAlbum2 = list.find { it.id == "456" }!!
            assertEquals(false, domainAlbum2.isSaved)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test album flow emits failure on api error`() = runTest {
        coEvery { mockApi.getTopAlbums() } throws RuntimeException("Error 500")
        every { mockSavedManager.savedIds } returns flowOf(emptySet())

        val repository = AlbumRepository(mockApi, mockSavedManager)

        repository.albums.test {
            val result = awaitItem()

            assertTrue(result.isFailure)
            assertEquals("Error 500", result.exceptionOrNull()?.message)

            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createAlbumDto(id: String, name: String) = AlbumDto(
        id = id, name = name, artistName = "artist_name", artworkUrl100 = "url",
        releaseDate = "date", url = "url2", genres = listOf(GenreDto("genre"))
    )
}
