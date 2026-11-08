package ru.mirea.module2

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

data class Cart(val products: List<Product>) {
    val totalSum: Int get() = products.sumOf { it.price }
}
data class Product(val id: Int, val name: String, val price: Int)

fun initialProducts() = listOf(Product(0, "Товар #1", 100), Product(1, "Товар #2", 150), Product(2, "Товар #3", 56))

private val ProductListSaver = listSaver<List<Product>, String>(
    save = { products -> products.map { "${it.id};${it.name};${it.price}" } },
    restore = { rows -> rows.map { row ->
        val parts = row.split(';', limit = 3)
        Product(parts[0].toInt(), parts[1], parts[2].toInt())
    } }
)

// Задание 10. Ни сумма, ни количество не изменяются при построении UI.
@Composable
fun ShoppingCartScreen(priceProvider: () -> Int = { Random.nextInt(1, 100) },
    deliveryNotice: ((Int) -> Unit)? = null) {
    var products by rememberSaveable(stateSaver = ProductListSaver) { mutableStateOf(initialProducts()) }
    var nextId by rememberSaveable { mutableIntStateOf(3) }
    val cart = Cart(products)
    val context = LocalContext.current
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Корзина магазина", style = MaterialTheme.typography.headlineSmall)
        products.forEach { product -> key(product.id) {
            Text("${product.name} — ${product.price} рублей", modifier = Modifier.testTag("product_${product.id}"))
        } }
        Text("Товаров: ${products.size}", modifier = Modifier.testTag("product_count"))
        Text("Товаров на сумму: ${cart.totalSum} рублей", modifier = Modifier.testTag("cart_total"),
            style = MaterialTheme.typography.titleMedium)
        AddProductSection {
            val product = Product(nextId, "Товар #${nextId + 1}", priceProvider())
            nextId++
            val updatedProducts = products + product
            products = updatedProducts
            val updatedTotal = Cart(updatedProducts).totalSum
            // Toast вызывается в ответ на действие, а не при рекомпозиции.
            if (updatedTotal > 500) {
                if (deliveryNotice != null) deliveryNotice(updatedTotal)
                else Toast.makeText(context, "Доставка бесплатная!", Toast.LENGTH_SHORT).show()
            }
        }
        RemoveProductSection(products.isNotEmpty()) { products = products.dropLast(1) }
    }
}

@Composable
fun AddProductSection(onAdd: () -> Unit) {
    Button(onClick = onAdd, modifier = Modifier.testTag("add_product")) { Text("Добавить товар") }
}

@Composable
fun RemoveProductSection(hasProducts: Boolean, onRemove: () -> Unit) {
    if (hasProducts) {
        OutlinedButton(onClick = onRemove, modifier = Modifier.testTag("remove_product")) { Text("Удалить товар") }
    }
}

@Preview(showBackground = true, widthDp = 411)
@Composable
fun CartPreview() { ModuleTheme { ShoppingCartScreen() } }
