package com.example.appsale11092023.helper

import com.example.appsale11092023.data.api.dto.ProductDTO
import com.example.appsale11092023.data.model.Product

object ProductHelper {
    fun convertToProduct(productDTO: ProductDTO?): Product? {
        productDTO ?: return null
        return Product(
            id = productDTO.id,
            name = productDTO.name,
            address = productDTO.address,
            price = productDTO.price,
            image = productDTO.image,
            quantity = productDTO.quantity,
            gallery = productDTO.gallery,
            dateCreated = productDTO.dateCreated,
            dateUpdated = productDTO.dateUpdated,
            v = productDTO.v
        )
    }
}