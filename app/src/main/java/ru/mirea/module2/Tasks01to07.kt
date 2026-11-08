package ru.mirea.module2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Задание 1. Nullable-параметр обработан до построения Text.
@Composable
fun Hello(name: String?) {
    Text(if (name == null) "Имя не задано" else "Привет, $name!",
        modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.headlineSmall)
}

// Задание 2. Три независимых макета с системным UI.
@Preview(name = "portrait", showSystemUi = true, device = "spec:width=411dp,height=891dp,dpi=420")
@Preview(name = "landscape", showSystemUi = true, device = "spec:width=891dp,height=411dp,dpi=420")
@Preview(name = "round", showSystemUi = true, showBackground = true,
    backgroundColor = 0xFFFFFF00,
    device = "spec:width=200dp,height=200dp,dpi=320,isRound=true")
@Composable
fun HelloPreview() { ModuleTheme { Hello("Кирилл") } }

@Preview(name = "null", showBackground = true)
@Composable
fun HelloNullPreview() { ModuleTheme { Hello(null) } }

