package ui.page

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialogProvider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.UndecoratedWindowAlertDialogProvider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import kotlinx.coroutines.*
import ui.widget.AlertDialog
import utils.getConfig
import utils.getLangText
import utils.toSpeed
import java.io.InputStreamReader
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
                    NavigationRailItem(
                        selected = navigationRailSelect == "page.main.navigationRail.home",
                        icon = { Icon(Icons.Default.Home, null) },
                        label = { Text(getLangText("page.main.navigationRail.home")) },
                        onClick = {
                            contentToggle("page.main.navigationRail.home")
                        },
                    )
                    NavigationRailItem(
                        selected = navigationRailSelect == "page.main.navigationRail.settings",
                        icon = { Icon(Icons.Default.Settings, null) },
                        label = { Text(getLangText("page.main.navigationRail.settings")) },
                        onClick = {
                            contentToggle("page.main.navigationRail.settings")
                        },
                    )
                    NavigationRailItem(
                        selected = navigationRailSelect == "page.main.navigationRail.about",
                        icon = { Icon(Icons.Default.Info, null) },
                        label = { Text(getLangText("page.main.navigationRail.about")) },
                        onClick = {
                            contentToggle("page.main.navigationRail.about")
                        },
                    )
                    NavigationRailItem(
                        selected = navigationRailSelect == "page.main.navigationRail.test",
                        icon = { Icon(Icons.Default.Build, null) },
                        label = { Text(getLangText("page.main.navigationRail.test")) },
                        onClick = {
                            contentToggle("page.main.navigationRail.test")
                        },
                    )
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
                            when(navigationRailSelect) {
                                "page.main.navigationRail.home" -> HomeContent(applicationScope, frameWindowScope)
                                "page.main.navigationRail.settings" -> SettingContent(applicationScope, frameWindowScope)
                                "page.main.navigationRail.about" -> AboutContent(applicationScope, frameWindowScope)
                                "page.main.navigationRail.test" -> TestContent(applicationScope, frameWindowScope)
                            }
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
private fun HomeContent(
    applicationScope: ApplicationScope,
    frameWindowScope: FrameWindowScope
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedCard(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 20.dp, start = 20.dp)
                .width(200.dp)
                .heightIn(10.dp, 200.dp)
        ) {
            Column(
                modifier = Modifier
                    .clickable(true) {

                    }
                    .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "原神！",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "你说的对，但是《烟神》是由理塘丁真自主研发的一款全新开放世界冒险游戏。游戏发生在一个被称作「理塘」的幻想世界，在这里，被神选中的人将被授予「尼古丁」，导引尼古丁之力。你将扮演一位名为「雪豹」的神秘角色，在自由的旅行中邂逅性格各异、能力独特的小马珍珠们，和他们一起击败强敌，找回失散的锐克五——同时，逐步发掘「烟神」的真相。",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                )
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 20.dp, end = 20.dp)
        ) {
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                onClick = {}
            ) {
                Text("原神启动")
            }
        }
    }
}

@Composable
private fun SettingContent(
    applicationScope: ApplicationScope,
    frameWindowScope: FrameWindowScope
) {

}

@Composable
private fun AboutContent(
    applicationScope: ApplicationScope,
    frameWindowScope: FrameWindowScope
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.Star,
                contentDescription = null
            )
            Text(getLangText("page.main.aboutContent.text"))
            Spacer(Modifier.requiredHeight(10.dp))
            Button(
                onClick = {
                    applicationScope.exitApplication()
                }
            ) {
                Text(getLangText("page.main.aboutContent.button"))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TestContent(
    applicationScope: ApplicationScope,
    frameWindowScope: FrameWindowScope
) {
    var alertDialogShow by remember { mutableStateOf(false) }
    var alertDialogContent by remember { mutableStateOf("") }


    Button(
        onClick = {
            val process = Runtime.getRuntime().exec("java -version")
            alertDialogContent = String(process.errorStream.readAllBytes())
            alertDialogShow = true
        }
    ) {
        Text("Runtime exec")
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
    }
}
