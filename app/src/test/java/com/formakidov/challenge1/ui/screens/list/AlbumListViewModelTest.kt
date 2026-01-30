package com.formakidov.challenge1.ui.screens.list

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.formakidov.challenge1.data.model.Album
import com.formakidov.challenge1.data.repository.AlbumRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumListViewModelTest {

    private val mockRepo = mockk<AlbumRepository>()
    private lateinit var viewModel: AlbumListViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState filters albums based on search query`() = runTest {
        val a1 = createAlbum("1", "aaa bbb", isSaved = false)
        val a2 = createAlbum("2", "ccc ddd", isSaved = true)
        val a3 = createAlbum("3", "aaa eee", isSaved = false)

        every { mockRepo.albums } returns flowOf(Result.success(listOf(a1, a2, a3)))

        val savedStateHandle = SavedStateHandle()

        viewModel = AlbumListViewModel(mockRepo, savedStateHandle)

        viewModel.uiState.test {
            val initialState = awaitItem()

            val successState = if (initialState is AlbumListUiState.Loading) {
                awaitItem() as AlbumListUiState.Success
            } else {
                initialState as AlbumListUiState.Success
            }

            assertEquals(3, successState.featuredAlbums.size)
            assertEquals(1, successState.savedAlbums.size)

            viewModel.onSearchQueryChanged("aaa")

            val filteredState = awaitItem() as AlbumListUiState.Success

            assertEquals(2, filteredState.featuredAlbums.size)
            assertEquals("aaa bbb", filteredState.featuredAlbums[0].name)
            assertEquals("aaa eee", filteredState.featuredAlbums[1].name)

            assertEquals(0, filteredState.savedAlbums.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createAlbum(id: String, name: String, isSaved: Boolean) = Album(
        id = id, name = name, artistName = "artist_name", artworkUrl = "url",
        releaseDate = "date", appleMusicUrl = "url2", genres = emptyList(), isSaved = isSaved
    )
}
