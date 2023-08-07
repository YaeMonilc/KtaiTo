package ui.page

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.FrameWindowScope
import kotlinx.coroutines.*
import ui.page.content.main.AboutContent
import ui.page.content.main.HomeContent
import ui.page.content.main.SettingContent
import ui.page.content.main.TestContent
import ui.widget.AlertDialog
import utils.getConfig
import utils.getLangText
import utils.toSpeed
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun MainPage(
    applicationScope: ApplicationScope,
    frameWindowScope: FrameWindowScope
) {
    var windowClosing by remember { mutableStateOf(false) }
    var windowOffset by remember { mutableStateOf(Offset.Zero) }

    var backgroundImage by remember { mutableStateOf("/image/page.main.navigationRail.home.png") }
    var backgroundImageShow by remember { mutableStateOf(true) }
    val backgroundImageAnimatedToggle = fun(image: String) {
        GlobalScope.launch {
            backgroundImageShow = false
            delay(300.toSpeed().toLong())
            backgroundImage = "/image/$image.png"
            backgroundImageShow = true
        }
    }

    var navigationRailSelect by remember { mutableStateOf("page.main.navigationRail.home") }
    val navigationRailItemShow = mutableStateMapOf(
        "page.main.navigationRail.home" to true,
        "page.main.navigationRail.settings" to true,
        "page.main.navigationRail.about" to true,
        "page.main.navigationRail.test" to true
    )
    var contentShow by remember { mutableStateOf(true) }
    val contentToggle = fun(name: String) {
        if (navigationRailSelect == name)
            return
        GlobalScope.launch {
            contentShow = false
            backgroundImageAnimatedToggle(name)
            delay(150.toSpeed().toLong())
            navigationRailSelect = name
            contentShow = true
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
           modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            TopAppBar(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectDragGestures (
                            onDrag = { _: PointerInputChange, dragAmount: Offset ->
                                windowOffset += dragAmount
                                frameWindowScope.window.setLocation(
                                    (frameWindowScope.window.x + windowOffset.x).roundToInt(),
                                    (frameWindowScope.window.y + windowOffset.y).roundToInt()
                                )
                            }
                        )
                    },
                title = { Text(getLangText("page.main.topAppBar.title")) },
                actions = {
                    IconButton(
                        onClick = { windowClosing = true }
                    ) {
                        Icon(Icons.Filled.Close, null)
                    }
                }
            )
            Row {
                NavigationRail {

                    AnimatedVisibility(
                        visible = navigationRailItemShow["page.main.navigationRail.home"] ?: false,
                        enter = fadeIn(
                            animationSpec = tween(150.toSpeed())
                        ),
                        exit = fadeOut(
                            animationSpec = tween(150.toSpeed())
                        )
                    ) {
                        NavigationRailItem(
                            selected = navigationRailSelect == "page.main.navigationRail.home",
                            icon = { Icon(Icons.Default.Home, null) },
                            label = { Text(getLangText("page.main.navigationRail.home")) },
                            onClick = {
                                contentToggle("page.main.navigationRail.home")
                            }
                        )
                    }

                    AnimatedVisibility(
                        visible = navigationRailItemShow["page.main.navigationRail.settings"] ?: false,
                        enter = fadeIn(
                            animationSpec = tween(150.toSpeed())
                        ),
                        exit = fadeOut(
                            animationSpec = tween(150.toSpeed())
                        )
                    ) {
                        NavigationRailItem(
                            selected = navigationRailSelect == "page.main.navigationRail.settings",
                            icon = { Icon(Icons.Default.Settings, null) },
                            label = { Text(getLangText("page.main.navigationRail.settings")) },
                            onClick = {
                                contentToggle("page.main.navigationRail.settings")
                            }
                        )
                    }

                    AnimatedVisibility(
                        visible = navigationRailItemShow["page.main.navigationRail.about"] ?: false,
                        enter = fadeIn(
                            animationSpec = tween(150.toSpeed())
                        ),
                        exit = fadeOut(
                            animationSpec = tween(150.toSpeed())
                        )
                    ) {
                        NavigationRailItem(
                            selected = navigationRailSelect == "page.main.navigationRail.about",
                            icon = { Icon(Icons.Default.Info, null) },
                            label = { Text(getLangText("page.main.navigationRail.about")) },
                            onClick = {
                                contentToggle("page.main.navigationRail.about")
                            }
                        )
                    }

                    AnimatedVisibility(
                        visible = navigationRailItemShow["page.main.navigationRail.test"] ?: false,
                        enter = fadeIn(
                            animationSpec = tween(150.toSpeed())
                        ),
                        exit = fadeOut(
                            animationSpec = tween(150.toSpeed())
                        )
                    ) {
                        NavigationRailItem(
                            selected = navigationRailSelect == "page.main.navigationRail.test",
                            icon = { Icon(Icons.Default.Build, null) },
                            label = { Text(getLangText("page.main.navigationRail.test")) },
                            onClick = {
                                contentToggle("page.main.navigationRail.test")
                            },
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(MaterialTheme.shapes.extraSmall.topStart.toPx(Size.Zero, Density(0.8F)).roundToInt(), 0, 0, 0)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        androidx.compose.animation.AnimatedVisibility(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .width(500.dp)
                                .height(400.dp)
                                .alpha(getConfig("page.main.background.alpha").toFloat()),
                            visible = backgroundImageShow,
                            enter = slideInHorizontally(
                                initialOffsetX = {
                                    it / 2
                                },
                                animationSpec = tween(350.toSpeed())
                            ),
                            exit = slideOutHorizontally(
                                targetOffsetX = {
                                    it
                                },
                                animationSpec = tween(350.toSpeed())
                            )
                        ) {
                            Image(
                                painter = painterResource(backgroundImage),
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )
                        }
                        androidx.compose.animation.AnimatedVisibility(
                            modifier = Modifier
                                .padding(10.dp),
                            visible = contentShow,
                            enter = fadeIn(
                                animationSpec = tween(150.toSpeed())
                            ),
                            exit = fadeOut(
                                animationSpec = tween(150.toSpeed())
                            )
                        ) {
                            PageController(
                                navigationRailSelect,
                                applicationScope,
                                frameWindowScope,
                                contentToggle
                            )
                        }
                    }
                }
            }
        }

        Box {
            AlertDialog(
                visibility = windowClosing,
                icon = { Icon(Icons.Default.Info, null) },
                title = getLangText("common.tip"),
                content = getLangText("application.closing.text"),
                footer = {
                    Button(
                        modifier = Modifier.padding(end = 10.dp),
                        onClick = {
                            applicationScope.exitApplication()
                        }
                    ) {
                        Text(getLangText("common.ok"))
                    }
                    Button(
                        onClick = {
                            windowClosing = false
                        }
                    ) {
                        Text(getLangText("common.cancel"))
                    }
                },
                onDismissRequest = { windowClosing = false }
            )
        }
    }
}

@Composable
private fun PageController(
    navigationRailSelect: String,
    applicationScope: ApplicationScope,
    frameWindowScope: FrameWindowScope,
    contentToggle: (name: String) -> Unit
) {
    when(navigationRailSelect) {
        "page.main.navigationRail.home" -> HomeContent(applicationScope, frameWindowScope, contentToggle)
        "page.main.navigationRail.settings" -> SettingContent(applicationScope, frameWindowScope, contentToggle)
        "page.main.navigationRail.about" -> AboutContent(applicationScope, frameWindowScope, contentToggle)
        "page.main.navigationRail.test" -> TestContent(applicationScope, frameWindowScope, contentToggle)
    }
}
