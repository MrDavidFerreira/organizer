package com.davfer.organizer.api.service

import com.davfer.organizer.api.repository.UserRepository
import com.davfer.organizer.api.security.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service("User service")
class UserService : UserDetailsService {

    @Autowired
    lateinit var repository: UserRepository

    val encoder = BCryptPasswordEncoder(11)

    override fun loadUserByUsername(email: String): User {
        return repository.findOneByEmail(email) ?: throw RuntimeException("User not found: $email")
    }

    fun saveMember(user: UserDTO): User {
        val member = Member(
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            pwd = encoder.encode(user.password),
            roles = "MEMBER"
        )
        return repository.save(member)
    }

    fun saveAdmin(user: UserDTO): User {
        val admin = Admin(
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            roles = "ADMIN, MEMBER",
            pwd = encoder.encode(user.password)
        )

        return repository.save(admin)
    }

    fun updateUser(toSave: User): User? {
        val user = repository.findOneByEmail(toSave.email)
        user?.let {
            if (!toSave.pwd.isEmpty()) {
                user.pwd = encoder.encode(toSave.password)
            }
            user.firstName = toSave.firstName
            user.lastName = toSave.lastName
            user.accountNonExpired = toSave.accountNonExpired
            user.accountNonLocked = toSave.accountNonLocked
            user.credentialsNonExpired = toSave.credentialsNonExpired
            user.modified = Instant.now()

            return repository.save(user)
        }
        return null
    }

    fun getUsers() = repository.findAll().map { it ->
        UserDetailsDTO(
            it.id!!,
            it.email,
            it.firstName,
            it.lastName,
            it.roles,
            it.enabled,
            it.accountNonExpired,
            it.accountNonLocked,
            it.credentialsNonExpired,
            it.created,
            it.modified
        )
    }

    fun deleteUser(id: String) = repository.deleteById(id)

}
