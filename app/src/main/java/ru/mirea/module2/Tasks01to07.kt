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

// Задание 3. Строка находится в res/values/strings.xml.
@Composable
fun TextStylesScreen() {
    val definition = stringResource(R.string.compose_definition)
    ScrollContent {
        Text("Зелёный курсив · 16 sp", style = MaterialTheme.typography.labelLarge)
        Text(definition, color = Color.Green, fontSize = 16.sp,
            fontStyle = FontStyle.Italic, textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
        Text("Одна строка с многоточием", style = MaterialTheme.typography.labelLarge)
        Text(definition, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text("Отступ первой строки · 48 sp", style = MaterialTheme.typography.labelLarge)
        Text(definition, fontSize = 24.sp, color = Color.Black,
            textDecoration = TextDecoration.Underline,
            style = TextStyle(textIndent = TextIndent(firstLine = 48.sp)),
            modifier = Modifier.fillMaxWidth().background(Color.Green))
    }
}

// Задание 4.
@Composable
fun StyledButtonScreen() {
    Button(onClick = {}, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp), border = BorderStroke(2.dp, Color.Gray),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray, contentColor = Color.Black)) {
        Text("Нажми на меня", fontSize = 18.sp)
    }
}

// Задание 5. 100 - 16 - 16 = 68 dp для круглого компонента.
@Composable
fun InitialsScreen() {
    Box(Modifier.fillMaxWidth().height(100.dp).background(Color.LightGray).padding(vertical = 16.dp),
        contentAlignment = Alignment.Center) {
        Surface(Modifier.fillMaxHeight().aspectRatio(1f), shape = CircleShape, color = Color(0xFFFF5722)) {
            Box(contentAlignment = Alignment.Center) { Text("КФ", color = Color.Black) }
        }
    }
}

// Задание 6. Исходный ресурс 48 x 48 dp и второй вариант с растяжением.
@Composable
fun OriginalCircle() {
    Box(Modifier.size(240.dp, 120.dp).background(Color.Black)) {
        Image(painterResource(R.drawable.circle), "Оранжевый круг",
            modifier = Modifier.size(48.dp).align(Alignment.TopEnd))
    }
}

@Composable
fun StretchedCircle() {
    Box(Modifier.size(240.dp, 120.dp).background(Color.Blue), contentAlignment = Alignment.Center) {
        Image(painterResource(R.drawable.circle), "Фиолетовый круг",
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(Color(0xFF800080)))
    }
}

@Composable
fun CircleScreen() {
    ScrollContent {
        Text("Исходный размер · правый верхний угол")
        OriginalCircle()
        Text("Растяжение и изменение цвета")
        StretchedCircle()
    }
}

data class Contact(val name: String, val patronymic: String, val surname: String,
    val phone: String, val address: String)

val sampleContacts = listOf(
    Contact("Евгений", "Андреевич", "Лукашин", "+7 495 95 95 95", "г. Москва, 3-я улица Строителей, д. 25, кв. 12"),
    Contact("Василий", "Егорович", "Кузякин", "—", "Ивановская область, дер. Крутово, д. 4"),
    Contact("Людмила", "Прокофьевна", "Калугина", "+7 495 788 78 78", "Москва, Большая Никитская, д. 43, кв. 290")
)

@Composable
fun ContactColumn(contact: Contact, centered: Boolean = false, modifier: Modifier = Modifier) {
    val alignment = if (centered) Alignment.CenterHorizontally else Alignment.Start
    val textAlign = if (centered) TextAlign.Center else TextAlign.Start
    Column(modifier, horizontalAlignment = alignment) {
        listOf("Имя: ${contact.name}", "Отчество: ${contact.patronymic}",
            "Фамилия: ${contact.surname}", "Мобильный телефон: ${contact.phone}",
            "Адрес: ${contact.address}").forEach {
            Text(it, textAlign = textAlign, fontSize = 14.sp, lineHeight = 18.sp)
        }
    }
}

@Composable
fun ContactList() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        sampleContacts.forEach { contact ->
            ContactColumn(contact, centered = true,
                modifier = Modifier.fillMaxWidth().background(Color.LightGray).padding(8.dp))
        }
    }
}

@Composable
fun ContactRow() {
    Row(Modifier.fillMaxWidth().background(Color.LightGray).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        ContactColumn(sampleContacts.first(), centered = true, modifier = Modifier.weight(1f))
        Image(painterResource(R.drawable.star), "Избранный контакт", modifier = Modifier.size(28.dp))
    }
}

@Composable
fun ContactsScreen() {
    ScrollContent {
        Text("Контакт · Column", style = MaterialTheme.typography.labelLarge)
        ContactColumn(sampleContacts.first())
        Text("Список контактов · Column", style = MaterialTheme.typography.labelLarge)
        ContactList()
        Text("Контакт с изображением · Row", style = MaterialTheme.typography.labelLarge)
        ContactRow()
    }
}

@Preview(showBackground = true)
@Composable
fun TextStylesPreview() { ModuleTheme { TextStylesScreen() } }

@Preview(showBackground = true, widthDp = 411)
@Composable
fun ButtonPreview() { ModuleTheme { StyledButtonScreen() } }

@Preview(showBackground = true, widthDp = 411)
@Composable
fun InitialsPreview() { ModuleTheme { InitialsScreen() } }

@Preview(showBackground = true)
@Composable
fun CirclesPreview() { ModuleTheme { CircleScreen() } }

@Preview(showBackground = true, widthDp = 411, heightDp = 900)
@Composable
fun ContactsPreview() { ModuleTheme { ContactsScreen() } }
