package org.notification.controller

import org.notification.model.User
import org.notification.event.UserEvent
import org.notification.service.UserService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users")
class UserController(
        private val userService: UserService,
        private val userEvent: UserEvent
) {


    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): User {
       return userService.getUser(id)
    }

    @PostMapping
    fun getUser(@RequestBody user: User): User {

        val saveUser = userService.saveUser(user)

        userEvent.send(saveUser)

        return saveUser
    }

}

