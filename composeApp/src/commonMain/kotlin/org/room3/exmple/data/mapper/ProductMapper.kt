package org.room3.exmple.data.mapper

import org.room3.exmple.data.remote.dto.ProductDto
import org.room3.exmple.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        rating = rating,
        brand = brand,
        category = category,
        thumbnail = thumbnail,
        images = images
    )
}
