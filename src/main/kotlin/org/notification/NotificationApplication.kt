package org.notification

import org.notification.model.User
import org.notification.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class NotificationApplication {

    @Bean
    fun init(repository: UserRepository) = CommandLineRunner {
        repository.save(User(1, "foo", "foo@mail.com"))
        repository.save(User(2, "boo", "boo@mail.com"))
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(NotificationApplication::class.java, *args)
}



