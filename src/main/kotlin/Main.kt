import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import ui.widget.RoundShadowWindow
import ui.window.MainWindow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    Thread.setDefaultUncaughtExceptionHandler { t, e ->
        val thread = Thread {
            application {
                RoundShadowWindow(
                    onCloseRequest = { },
                    alwaysOnTop = true,
                    state = WindowState(
                        height = 1200.dp,
                        position = WindowPosition(0.dp, -50.dp)
                    )
                ) {
                    val scrollState = rememberScrollState()
                    Text(
                        modifier = Modifier.verticalScroll(scrollState),
                        text = e.stackTraceToString()
                    )
                }

            }
        }
        //thread.start()
    }

    application {
        MainWindow(this@application)
    }
}


