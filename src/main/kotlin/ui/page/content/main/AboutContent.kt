package ui.page.content.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.FrameWindowScope
import utils.getLangText

@Composable
fun AboutContent(
    applicationScope: ApplicationScope,
    frameWindowScope: FrameWindowScope,
    contentToggle: (name: String) -> Unit
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