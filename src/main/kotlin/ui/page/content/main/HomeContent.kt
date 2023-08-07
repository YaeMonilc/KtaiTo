package ui.page.content.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.FrameWindowScope

@Composable
fun HomeContent(
    applicationScope: ApplicationScope,
    frameWindowScope: FrameWindowScope,
    contentToggle: (name: String) -> Unit
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
                onClick = {
                    Runtime.getRuntime().exec("cmd /c start https://yuanshen.com")
                }
            ) {
                Text("原神启动")
            }
        }
    }
}