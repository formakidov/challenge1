package com.formakidov.challenge1.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.formakidov.challenge1.data.model.Album
import com.formakidov.challenge1.ui.components.AlbumItem
import com.formakidov.challenge1.ui.components.SearchBar
import com.formakidov.challenge1.ui.components.SectionHeader
import com.formakidov.challenge1.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun AlbumListScreen(
    onAlbumClick: (Album) -> Unit,
    viewModel: AlbumListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val currentSection by viewModel.currentSection.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFFF2F2F7),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F2F7))
                    .statusBarsPadding()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "Apple Music: Albums",
                    style = Typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                SearchBar(
                    query = searchQuery,
                    onQueryChange = viewModel::onSearchQueryChanged
                )
            }
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.navigationBars)
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is AlbumListUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                is AlbumListUiState.Error -> {
                    Text(
                        text = "Error: ${state.message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is AlbumListUiState.Success -> {
                    AlbumListContent(
                        featuredList = state.featuredAlbums,
                        savedList = state.savedAlbums,
                        currentSection = currentSection,
                        onSectionChange = viewModel::onSectionChanged,
                        onAlbumClick = onAlbumClick,
                        onToggleSave = viewModel::toggleSave
                    )
                }
            }
        }
    }
}

@Composable
fun AlbumListContent(
    featuredList: List<Album>,
    savedList: List<Album>,
    currentSection: ListSection,
    onSectionChange: (ListSection) -> Unit,
    onAlbumClick: (Album) -> Unit,
    onToggleSave: (Album) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SectionHeader(
            currentSection = currentSection,
            featuredCount = featuredList.size,
            savedCount = savedList.size,
            onSectionChange = onSectionChange
        )

        val albumsToShow = when (currentSection) {
            ListSection.Featured -> featuredList
            ListSection.Saved -> savedList
        }

        val navBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp + navBottom),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = albumsToShow,
                key = { it.id }
            ) { album ->
                AlbumItem(
                    album = album,
                    onItemClick = onAlbumClick,
                    onToggleSave = onToggleSave
                )
            }

            if (albumsToShow.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (currentSection == ListSection.Saved) "No saved albums" else "No albums found",
                            style = Typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
