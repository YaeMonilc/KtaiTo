package ui.window

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.WindowState
import ui.page.MainPage
import ui.widget.RoundShadowWindow
import utils.getLangText

@Composable
fun MainWindow(applicationScope: ApplicationScope) {
    RoundShadowWindow(
        title = getLangText("application.title"),
        state = WindowState(
            width = 845.dp,
            height = 505.dp
        ),
        resizable = false,
        onCloseRequest = applicationScope::exitApplication
    ) {
        MaterialTheme {
            MainPage(applicationScope, this@RoundShadowWindow)
        }
    }
}