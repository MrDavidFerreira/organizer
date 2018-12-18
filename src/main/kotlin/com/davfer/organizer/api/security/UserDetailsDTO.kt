package com.davfer.organizer.api.security

import java.time.Instant

/**
 * For returning a list of available users in the system.
 */
data class UserDetailsDTO(
    val id: String,
    var email: String,
    var firstName: String,
    var lastName: String,
    var roles: String,
    var enabled: Boolean,
    var accountNonExpired: Boolean,
    var accountNonLocked: Boolean,
    var credentialsNonExpired: Boolean,
    var created: Instant,
    var modified: Instant
)