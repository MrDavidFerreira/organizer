package com.davfer.organizer.api.repository

import com.davfer.organizer.api.security.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, String> {

    //To lacate user by their username (email in this case)
    fun findOneByEmail(email: String): User?

}