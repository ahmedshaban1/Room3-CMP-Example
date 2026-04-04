package org.room3.exmple

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Room3-Example",
    ) {
        App()
    }
}