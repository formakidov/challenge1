package com.formakidov.challenge1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.formakidov.challenge1.ui.screens.list.ListSection
import com.formakidov.challenge1.ui.theme.Typography

@Composable
fun SectionHeader(
    currentSection: ListSection,
    featuredCount: Int,
    savedCount: Int,
    onSectionChange: (ListSection) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SectionTab(
            text = "Featured",
            count = featuredCount,
            isSelected = currentSection == ListSection.Featured,
            onClick = { onSectionChange(ListSection.Featured) }
        )

        SectionTab(
            text = "Saved",
            count = savedCount,
            isSelected = currentSection == ListSection.Saved,
            onClick = { onSectionChange(ListSection.Saved) }
        )
    }
}

@Composable
private fun SectionTab(
    text: String,
    count: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val textColor = if (isSelected) Color.Black else Color(0xFF8E8E93)

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
        verticalAlignment = Alignment.Top,
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null, 
            onClick = onClick
        )
    ) {
        Text(
            text = text,
            style = Typography.headlineSmall.copy(
                color = textColor
            )
        )

        Box(
            modifier = Modifier
                .padding(5.dp)
                .clip(CircleShape)
                .background(Color(0xFFE5E5EA)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count.toString(),
                style = Typography.labelSmall
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SectionHeaderFeaturedPreview() {
    MaterialTheme {
        SectionHeader(
            currentSection = ListSection.Featured,
            featuredCount = 12,
            savedCount = 8,
            onSectionChange = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SectionHeaderSavedPreview() {
    MaterialTheme {
        SectionHeader(
            currentSection = ListSection.Saved,
            featuredCount = 12,
            savedCount = 8,
            onSectionChange = {}
        )
    }
}
