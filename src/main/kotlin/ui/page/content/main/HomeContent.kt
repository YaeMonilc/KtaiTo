package ui.page.content.main

import GameConfigBuilder
import LaunchCommandBuilder
import MinecraftLauncherCore
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.FrameWindowScope
import entity.LauncherConfig
import entity.MemorySetting
import entity.OS
import entity.User
import ui.widget.AlertDialog
import utils.LibraryUtil
import utils.getLangText
import utils.getResourceByFileName
import java.io.File
import java.util.*

@Composable
fun HomeContent(
    applicationScope: ApplicationScope,
    frameWindowScope: FrameWindowScope,
    contentToggle: (name: String) -> Unit
) {

    var starting by remember { mutableStateOf(false) }

    var inputDialogShow by remember { mutableStateOf(false) }
    var javaPath by remember { mutableStateOf("C:\\Users\\Administrator\\.jdks\\corretto-17.0.7\\bin\\java.exe") }
    var minecraftPath by remember { mutableStateOf("C:\\Users\\Administrator\\Desktop\\MC\\.minecraft") }
    var version by remember { mutableStateOf("1.20.1") }
    var name by remember { mutableStateOf("YaeMonilc") }
    var inputDialogOk by remember {
        mutableStateOf(
            fun() {

            }
        )
    }

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
                onClick = {
                    inputDialogShow = true
                    inputDialogOk = fun() {
                        Thread {
                            val gameConfig = GameConfigBuilder(
                                javaPath = File(javaPath),
                                minecraftPath = File(minecraftPath),
                                version = version,
                                charset = "UTF-8",
                                os = OS.WNIDOWS
                            ).build()

                            val command = LaunchCommandBuilder(
                                config = gameConfig,
                                user = User(name, UUID.randomUUID().toString(), UUID.randomUUID().toString(), User.UserType.msa),
                                memorySetting = MemorySetting(),
                                launcherConfig = LauncherConfig(
                                    name = "由 KtaiTo",
                                    version = "1.0.0 驱动"
                                )
                            ).build()

                            if (LibraryUtil.check(gameConfig)) {
                                val minecraftLauncherCore = MinecraftLauncherCore(command)
                                println(command.command)
                                minecraftLauncherCore.launch()
                            }
                        }.start()

                        starting = true
                    }
                }
            ) {
                Text("原神启动")
            }
        }
    }
    Box {
        AlertDialog(
            visibility = starting,
            title = getLangText("common.tip"),
            icon = { Icon(Icons.Default.Info, null) },
            content = {
                Text("启动中！！")
            },
            footer = {},
            onDismissRequest = {  }
        )

        AlertDialog(
            visibility = inputDialogShow,
            icon = { Icon(Icons.Default.Info, null) },
            title = getLangText("common.tip"),
            content = {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                    value = javaPath,
                    onValueChange = {
                        javaPath = it
                    },
                    label = { Text("Java路径") },
                    enabled = true
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                    value = minecraftPath,
                    onValueChange = {
                        minecraftPath = it
                    },
                    label = { Text("Minecraft路径") },
                    enabled = true
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                    value = version,
                    onValueChange = {
                        version = it
                    },
                    label = { Text("启动版本") },
                    enabled = true
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = { Text("名称") },
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
            onDismissRequest = { inputDialogShow = false }
        )
    }
}