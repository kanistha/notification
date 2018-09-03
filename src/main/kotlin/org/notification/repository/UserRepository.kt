package org.notification.repository

import org.notification.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long>