package ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AlertDialog(
    visibility: Boolean,
    icon: @Composable () -> Unit = {},
    title: String = "title",
    topButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
    footer: @Composable () -> Unit
) {
    if (visibility) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0, 0, 0, 80))
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .widthIn(320.dp, 450.dp)
                    .heightIn(100.dp, 540.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                ) {
                    Box {
                        Row(
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            icon()
                            Text(
                                modifier = Modifier.padding(start = 5.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                text = title
                            )
                        }
                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            topButton()
                        }
                    }

                    Box(
                        modifier = Modifier.padding(10.dp)
                    ){
                        content()
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        footer()
                    }
                }
            }
        }
    }
}