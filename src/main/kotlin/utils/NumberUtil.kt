package utils

import kotlin.math.roundToInt

fun Int.toSpeed() = (this / getConfig("application.animatedSpeed").toFloat()).roundToInt()