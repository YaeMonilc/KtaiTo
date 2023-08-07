package ui.page.content.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.FrameWindowScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import ui.widget.AlertDialog
import utils.getLangText
import utils.toSpeed
import java.nio.charset.Charset

@Composable
fun TestContent(
    applicationScope: ApplicationScope,
    frameWindowScope: FrameWindowScope,
    contentToggle: (name: String) -> Unit
) {
    val areaShow = remember { mutableStateOf(true) }

    var alertDialogShow by remember { mutableStateOf(false) }
    var alertDialogContent by remember { mutableStateOf("") }

    var inputDialogShow by remember { mutableStateOf(false) }
    var inputDialogContent by remember { mutableStateOf("123") }
    var inputDialogOk by remember {
        mutableStateOf(
            fun() {

            }
        )
    }

    AnimatedVisibility(
        visible = areaShow.value,
        enter = fadeIn(
            animationSpec = tween(150.toSpeed())
        ),
        exit = fadeOut(
            animationSpec = tween(150.toSpeed())
        )
    ) {
        Row {
            Button(
                onClick = {
                    inputDialogOk = fun() {
                        runBlocking(Dispatchers.IO) {
                            val process = Runtime.getRuntime().exec(inputDialogContent)
                            alertDialogContent = String(process.errorStream.readAllBytes(), Charset.defaultCharset())
                            if (alertDialogContent.isNotBlank()) {
                                alertDialogShow = true
                            }
                        }
                    }
                    inputDialogShow = true
                }
            ) {
                Text("Runtime exec")
            }
            InnerArea(areaShow)
            Button(
                onClick = {
                    contentToggle("page.main.navigationRail.home")
                }
            ) {
                Text("To Home")
            }

        }
    }

    Box {
        AlertDialog(
            visibility = alertDialogShow,
            icon = { Icon(Icons.Default.Info, null) },
            title = getLangText("common.tip"),
            content = alertDialogContent,
            footer = {
                Button(
                    modifier = Modifier.padding(end = 10.dp),
                    onClick = {
                        alertDialogShow = false
                    }
                ) {
                    Text(getLangText("common.ok"))
                }
                Button(
                    onClick = {
                        alertDialogShow = false
                    }
                ) {
                    Text(getLangText("common.cancel"))
                }
            },
            onDismissRequest = { alertDialogShow = false }
        )

        AlertDialog(
            visibility = inputDialogShow,
            icon = { Icon(Icons.Default.Info, null) },
            title = getLangText("common.tip"),
            content = {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    value = inputDialogContent,
                    onValueChange = {
                        inputDialogContent = it
                    },
                    label = { Text("命令") },
                    enabled = true
                )
            },
            footer = {
                Button(
                    modifier = Modifier.padding(end = 10.dp),
                    onClick = {
                        inputDialogShow = false
                        inputDialogOk()
                    }
                ) {
                    Text(getLangText("common.ok"))
                }
                Button(
                    onClick = {
                        inputDialogShow = false
                    }
                ) {
                    Text(getLangText("common.cancel"))
                }
            },
            onDismissRequest = { alertDialogShow = false }
        )
    }
}

@Composable
fun InnerArea(show: MutableState<Boolean>) {
    Button(
        onClick = {
            show.value = false
        }
    ) {
        Text("Hide This Area")
    }
}