package ui.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import utils.toSpeed

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Dialog(
    visibility: Boolean,
    icon: @Composable () -> Unit = {},
    title: String = "",
    topButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
    footer: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val transitionState = remember { MutableTransitionState(false) }
    transitionState.targetState = visibility

    if (transitionState.currentState || transitionState.targetState || !transitionState.isIdle) {
        Popup(
            popupPositionProvider = object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset = IntOffset.Zero
            },
        ) {
            AnimatedVisibility(
                visibleState = transitionState,
                enter = fadeIn(
                    animationSpec = tween(200.toSpeed())
                ),
                exit = fadeOut(
                    animationSpec = tween(200.toSpeed())
                )
            ) {
                Card(
                    colors = CardDefaults.cardColors(Color.Transparent),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Box {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.32f))
                                .onClick {
                                    onDismissRequest()
                                }
                        )
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Card(
                                modifier = Modifier.widthIn(50.dp, 400.dp)
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

        }
    }
}

@Composable
fun AlertDialog(
    visibility: Boolean,
    icon: @Composable () -> Unit = {},
    title: String = "",
    topButton: @Composable () -> Unit = {},
    content: String = "",
    footer: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        visibility,
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
        },
        onDismissRequest
    )
}

@Composable
fun AlertDialog(
    visibility: Boolean,
    icon: @Composable () -> Unit = {},
    title: String = "",
    topButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
    footer: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        visibility,
        icon,
        title,
        topButton,
        content,
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
        },
        onDismissRequest
    )
}