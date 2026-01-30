package com.formakidov.challenge1.ui.screens.list

import androidx.compose.runtime.Composable
import com.formakidov.challenge1.data.model.Album
import org.koin.androidx.compose.koinViewModel

@Composable
fun AlbumListScreen(
    onAlbumClick: (Album) -> Unit,
    viewModel: AlbumListViewModel = koinViewModel()
) {

}
