package com.formakidov.challenge1.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.formakidov.challenge1.ui.theme.ChipBorderColor
import com.formakidov.challenge1.ui.theme.GenreChipBackgroundColor
import com.formakidov.challenge1.ui.theme.GenreChipBorderColor
import com.formakidov.challenge1.ui.theme.GenreChipContentColor
import com.formakidov.challenge1.ui.theme.SavedChipBackgroundColor
import com.formakidov.challenge1.ui.theme.SavedChipContentColor
import com.formakidov.challenge1.ui.theme.Typography
import com.formakidov.challenge1.ui.theme.UnsavedChipBackgroundColor
import com.formakidov.challenge1.ui.theme.UnsavedChipContentColor


@Composable
fun GenreChip(text: String) {
    Chip(
        text = text,
        icon = Icons.Default.MusicNote,
        backgroundColor = GenreChipBackgroundColor,
        borderColor = GenreChipBorderColor,
        contentColor = GenreChipContentColor
    )
}

@Composable
fun SaveChip(isSaved: Boolean, onClick: () -> Unit) {
    if (isSaved) {
        Chip(
            text = "Saved",
            icon = Icons.Default.Favorite,
            backgroundColor = SavedChipBackgroundColor,
            borderColor = ChipBorderColor,
            contentColor = SavedChipContentColor,
            onClick = onClick
        )
    } else {
        Chip(
            text = "Add",
            icon = Icons.Default.FavoriteBorder,
            backgroundColor = UnsavedChipBackgroundColor,
            borderColor = ChipBorderColor,
            contentColor = UnsavedChipContentColor,
            onClick = onClick
        )
    }
}

@Composable
private fun Chip(
    text: String,
    icon: ImageVector,
    backgroundColor: Color,
    borderColor: Color,
    contentColor: Color,
    onClick: (() -> Unit)? = null
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 2.dp, top = 2.dp, end = 8.dp, bottom = 2.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = Typography.labelSmall.copy(
                    fontSize = 12.sp,
                    color = contentColor
                )
            )
        }
    }
}

@Preview
@Composable
private fun GenreChipPreview() {
    GenreChip(text = "Rock")
}

@Preview
@Composable
private fun SaveChipSavedPreview() {
    SaveChip(isSaved = true, onClick = {})
}

@Preview
@Composable
private fun SaveChipUnsavedPreview() {
    SaveChip(isSaved = false, onClick = {})
}
