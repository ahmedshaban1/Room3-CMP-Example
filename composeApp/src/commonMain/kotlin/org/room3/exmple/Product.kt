package org.room3.exmple

data class Product(
    val id: Int,
    val title: String,
    val isFavorite: Boolean = false
)

val sampleProducts = listOf(
    Product(1, "Wireless Headphones", false),
    Product(2, "Smart Watch", true),
    Product(3, "Laptop Stand", false),
    Product(4, "Mechanical Keyboard", true),
    Product(5, "USB-C Hub", false),
    Product(6, "Monitor Light Bar", true),
    Product(7, "Portable Charger", false),
    Product(8, "Bluetooth Speaker", true),
)
