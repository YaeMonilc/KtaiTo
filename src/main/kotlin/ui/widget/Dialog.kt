package ui.widget

import androidx.compose.foundation.*
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Dialog(
    icon: @Composable () -> Unit = {},
    title: String = "title",
    topButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
    footer: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0, 0, 0, 150))
                .onClick {
                    onDismissRequest()
                }
        )
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .widthIn(200.dp, 380.dp)
                .heightIn(100.dp, 420.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Box {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .height(30.dp)
                    ) {
                        icon()
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .fillMaxHeight(),
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

                footer()
            }
        }
    }
}

@Composable
fun AlertDialog(
    icon: @Composable () -> Unit = {},
    title: String = "title",
    topButton: @Composable () -> Unit = {},
    content: String,
    footer: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
) {

    Dialog(
        icon,
        title,
        topButton,
        {
            Text(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = content
            )
        },
        {
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                ) {
                    footer()
                }
            }
        },
        onDismissRequest,
    )
}