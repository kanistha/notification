package org.notification.service

import org.notification.model.User
import org.notification.repository.UserRepository
import org.springframework.stereotype.Service


@Service
class UserService(private val userRepository: UserRepository) {

    fun getUser(id: Long): User {
        return userRepository.findById(id).get()
    }

    fun saveUser(user: User): User {
        return userRepository.save(user)
    }

}
