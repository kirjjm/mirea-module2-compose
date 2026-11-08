package ru.mirea.module2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val initialTask = intent.getIntExtra("task", 1).coerceIn(1, 10)
        setContent { ModuleTheme { ModuleApp(initialTask) } }
    }
}

@Composable
fun ModuleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF415F91), secondary = Color(0xFF565F71),
            background = Color.White, surface = Color.White
        ), content = content
    )
}

@Composable
fun ModuleApp(initialTask: Int = 1) {
    var task by rememberSaveable { mutableIntStateOf(initialTask) }
    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().safeDrawingPadding()) {
            Text("Модуль 2 · Jetpack Compose", style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp, 12.dp))
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                (1..10).forEach { number ->
                    FilterChip(selected = task == number, onClick = { task = number },
                        label = { Text("$number") }, modifier = Modifier.testTag("task_$number"))
                }
            }
            HorizontalDivider()
            Text("Задание $task · ${taskNames[task - 1]}", style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp, 12.dp))
            key(task) {
                Box(Modifier.weight(1f).fillMaxWidth()) {
                    when (task) {
                        1 -> Hello(BuildConfig.HELLO_NAME)
                        2 -> Hello(BuildConfig.HELLO_NAME)
                        3 -> Text("Задание 3")
                        4 -> Text("Задание 4")
                        5 -> Text("Задание 5")
                        6 -> Text("Задание 6")
                        7 -> Text("Задание 7")
                        8, 9 -> Text("Задание 8")
                        10 -> Text("Задание 10")
                    }
                }
            }
        }
    }
}

val taskNames = listOf("Приветствие", "Предпросмотр", "Оформление текста", "Кнопка",
    "Инициалы", "Векторное изображение", "Контакты", "Трекер воды", "UI тесты", "Корзина")

@Composable
fun ScrollContent(content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp), content = content)
}
