package com.example.appsale11092023.helper

import com.example.appsale11092023.data.api.dto.CartDTO
import com.example.appsale11092023.data.model.Cart

object CartHelper {
    fun parseCartDTO(cartDTO: CartDTO?): Cart {
        return Cart(
            id = cartDTO?.id ?: "",
            idUser = cartDTO?.idUser ?: "",
            price = cartDTO?.price ?: 0,
            dateCreated = cartDTO?.dateCreated ?: "",
            listProduct = cartDTO?.listProductDTO?.map { ProductHelper.convertToProduct(it) } ?: emptyList()
        )
    }
}