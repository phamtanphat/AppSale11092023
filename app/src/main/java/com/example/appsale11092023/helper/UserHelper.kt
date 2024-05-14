package com.example.appsale11092023.helper

import com.example.appsale11092023.data.api.dto.UserDTO
import com.example.appsale11092023.data.model.User

object UserHelper {

    fun convertToUser(userDTO: UserDTO?): User? {
        userDTO ?: return null
        return User(
            email = userDTO.email,
            name = userDTO.name,
            phone = userDTO.phone,
            userGroup = userDTO.userGroup,
            registerDate = userDTO.registerDate,
            token = userDTO.token
        )
    }
}