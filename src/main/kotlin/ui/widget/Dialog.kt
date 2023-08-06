package ui.widget

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialogProvider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.PopupAlertDialogProvider
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.DialogWindowScope
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider

@Composable
fun Dialog(
    icon: @Composable () -> Unit = {},
    title: String = "",
    topButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
    footer: @Composable () -> Unit,
) {
    Popup(
        popupPositionProvider = object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset = IntOffset.Zero
        },
        focusable = true,
    ) {
        Card(
            colors = CardDefaults.cardColors(Color.Transparent),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.32f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.widthIn(200.dp, 400.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Box(

                        ) {
                            Row(
                                modifier = Modifier.align(Alignment.CenterStart)
                            ) {
                                icon()
                                Text(
                                    modifier = Modifier.padding(start = 5.dp),
                                    text = title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Box(
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                topButton()
                            }
                        }
                        Column {
                            content()
                        }
                        Row {
                            footer()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlertDialog(
    icon: @Composable () -> Unit = {},
    title: String = "",
    topButton: @Composable () -> Unit = {},
    content: String = "",
    footer: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
) {

    Dialog(
        icon,
        title,
        topButton,
        {
            Text(
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                text = content
            )
        },
        {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                ) {
                    footer()
                }
            }
        }
    )
}