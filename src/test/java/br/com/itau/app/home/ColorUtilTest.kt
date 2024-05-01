package br.com.itau.app.home

import br.com.itau.app.home.commons.getColorForIndex
import br.com.itau.app.ui_components.theme.Component
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorUtilTest {

    @Test
    fun getColorForIndex_ReturnsCorrectColor() {
        val colorPalette = listOf(
            Component.Color.lightGreen,
            Component.Color.brightBlue,
            Component.Color.orange,
            Component.Color.purple,
            Component.Color.red,
            Component.Color.green,
            Component.Color.middleGray
        )

        for (i in colorPalette.indices) {
            val color = getColorForIndex(i)
            assertEquals(colorPalette[i], color)
        }
    }

    @Test
    fun getColorForIndex_ReturnsCorrectColor_ForLargerIndices() {
        val colorPalette = listOf(
            Component.Color.lightGreen,
            Component.Color.brightBlue,
            Component.Color.orange,
            Component.Color.purple,
            Component.Color.red,
            Component.Color.green,
            Component.Color.middleGray
        )

        for (i in 0 until 20) {
            val expectedColor = colorPalette[i % colorPalette.size]
            val color = getColorForIndex(i)
            assertEquals(expectedColor, color)
        }
    }
}

