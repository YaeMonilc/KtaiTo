import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.window.MainWindow
import utils.getString
import utils.initLanguage

fun main() {

    initLanguage()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            resizable = false,
            title = getString("window.main.title")
        ) {
            App()
        }
    }
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainWindow()
    }
}
