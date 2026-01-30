package com.formakidov.challenge1.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// todo typography must be refactored a bit to setup defaults here and customs in-place
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(700),
        fontSize = 34.sp,
        lineHeight = 41.sp,
        letterSpacing = 0.4.sp,
        color = Color(0xFF000000)
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(510),
        fontSize = 32.sp,
        lineHeight = 52.sp,
        color = Color(0xFF141414)
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(400),
        fontSize = 24.sp,
        lineHeight = 22.sp,
        color = Color(0xFF000000)
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(700),
        fontSize = 22.sp,
        lineHeight = 28.sp,
        color = Color(0xFF000000)
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp,
        color = Color(0x993C3C43)
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(400),
        fontSize = 17.sp,
        lineHeight = 22.sp,
        color = Color(0x993C3C43)
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(600),
        fontSize = 13.sp,
        color = Color(0xFF000000)
    )
)
