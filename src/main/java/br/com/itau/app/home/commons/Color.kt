package br.com.itau.app.home.commons

import androidx.compose.ui.graphics.Color
import br.com.itau.app.ui_components.theme.Component

fun getColorForIndex(index: Int): Color {
    val colorPalette = listOf(
        Component.Color.lightGreen,
        Component.Color.brightBlue,
        Component.Color.orange,
        Component.Color.purple,
        Component.Color.red,
        Component.Color.green,
        Component.Color.middleGray
    )
    val colorIndex = index % colorPalette.size
    return colorPalette[colorIndex]
}
