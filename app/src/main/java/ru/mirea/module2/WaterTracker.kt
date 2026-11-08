package ru.mirea.module2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val DAILY_GOAL_ML = 1500
const val PORTION_ML = 250

fun nextStreak(waterMl: Int, currentStreak: Int): Int =
    if (waterMl >= DAILY_GOAL_ML) currentStreak + 1 else 0

// Задания 8 и 9. Состояние переживает рекомпозицию и пересоздание Activity.
@Composable
fun WaterTracker() {
    var waterCount by rememberSaveable { mutableIntStateOf(0) }
    var successfulDays by rememberSaveable { mutableIntStateOf(0) }
    Column(Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("Трекер воды", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF415F91))
        Spacer(Modifier.height(48.dp))
        Text("$waterCount мл", modifier = Modifier.testTag("water_amount"),
            fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color(0xFF565F71))
        Spacer(Modifier.height(24.dp))
        Button(onClick = { waterCount += PORTION_ML }, modifier = Modifier.testTag("add_water")) {
            Text("+250 мл", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(16.dp))
        OutlinedButton(onClick = {
            successfulDays = nextStreak(waterCount, successfulDays)
            waterCount = 0
        }, modifier = Modifier.testTag("finish_day")) { Text("Завершить день") }
        Spacer(Modifier.height(24.dp))
        Text("Дней подряд с нормой 1500 мл: $successfulDays",
            modifier = Modifier.testTag("day_streak"), style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true, widthDp = 411, heightDp = 750)
@Composable
fun WaterPreview() { ModuleTheme { WaterTracker() } }
