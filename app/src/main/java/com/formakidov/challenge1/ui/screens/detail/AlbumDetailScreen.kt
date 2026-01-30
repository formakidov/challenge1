package com.formakidov.challenge1.ui.screens.detail

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.formakidov.challenge1.data.model.Album
import com.formakidov.challenge1.ui.components.AlbumImage
import com.formakidov.challenge1.ui.components.GenreChip
import com.formakidov.challenge1.ui.theme.Typography

@Composable
fun AlbumDetailScreen(
    album: Album,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xFFF2F2F7),
        topBar = {
            DetailTopBar(
                onBackClick = onBackClick,
                onShareClick = { shareAlbum(context, album.appleMusicUrl) }
            )
        }
    ) { padding ->
        DetailContent(
            album = album,
            contentPadding = padding
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            CircleActionButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                onClick = onBackClick
            )
        },
        actions = {
            CircleActionButton(
                icon = Icons.Default.Share,
                contentDescription = "Share",
                onClick = onShareClick
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun CircleActionButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(50))
            .size(40.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.Black
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetailContent(
    album: Album,
    contentPadding: PaddingValues
) {
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        AlbumImage(
            modifier = Modifier.size(256.dp),
            url = album.artworkUrl,
            cardRadius = 16.dp,
            imageRadius = 8.dp,
            imagePadding = 16.dp,
            rotation = -5f,
            elevation = 12.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = album.name,
            style = Typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ArtistPill(artistName = album.artistName)

        Spacer(modifier = Modifier.height(24.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            album.genres.forEach { genre ->
                GenreChip(text = genre)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { uriHandler.openUri(album.appleMusicUrl) },
            modifier = Modifier.wrapContentSize(),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 7.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007AFF)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "View on Apple Music",
                style = Typography.titleSmall.copy(color = Color.White)
            )
        }
    }
}

@Composable
private fun ArtistPill(artistName: String) {
    Surface(
        color = Color(0xFFE5E5EA),
        shape = RoundedCornerShape(50),
        modifier = Modifier.height(32.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "By: $artistName",
                style = Typography.bodyMedium.copy(fontSize = 15.sp),
                color = Color(0xFF8E8E93)
            )
        }
    }
}

private fun shareAlbum(context: Context, url: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}
