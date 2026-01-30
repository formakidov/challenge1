package com.formakidov.challenge1.ui.screens.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.formakidov.challenge1.data.model.Album
import com.formakidov.challenge1.data.repository.AlbumRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface AlbumListUiState {
    data object Loading : AlbumListUiState
    data class Error(val message: String) : AlbumListUiState
    data class Success(
        val featuredAlbums: List<Album>,
        val savedAlbums: List<Album>
    ) : AlbumListUiState
}

class AlbumListViewModel(
    private val repository: AlbumRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val searchQuery: StateFlow<String> = savedStateHandle.getStateFlow(KEY_SEARCH_QUERY, "")

    val currentSection: StateFlow<ListSection> = savedStateHandle.getStateFlow(KEY_CURRENT_SECTION, ListSection.Featured)

    val uiState: StateFlow<AlbumListUiState> = combine(
        repository.albums,
        searchQuery
    ) { result, query ->
        result.fold(
            onSuccess = { allAlbums ->
                val filtered = if (query.isBlank()) {
                    allAlbums
                } else {
                    // todo move to separate testable filter
                    allAlbums.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.artistName.contains(query, ignoreCase = true)
                    }
                }

                AlbumListUiState.Success(
                    featuredAlbums = filtered,
                    savedAlbums = filtered.filter { it.isSaved }
                )
            },
            onFailure = {
                AlbumListUiState.Error(it.localizedMessage ?: "Unknown error")
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AlbumListUiState.Loading
    )

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[KEY_SEARCH_QUERY] = query
    }

    fun onSectionChanged(section: ListSection) {
        savedStateHandle[KEY_CURRENT_SECTION] = section
    }

    fun toggleSave(album: Album) {
        viewModelScope.launch {
            repository.toggleSave(album.id)
        }
    }

    companion object {
        private const val KEY_SEARCH_QUERY = "search_query"
        private const val KEY_CURRENT_SECTION = "current_section"
    }
}
