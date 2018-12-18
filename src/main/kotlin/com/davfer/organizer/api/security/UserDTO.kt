package com.davfer.organizer.api.security

/**
 * For creating new users in the system.
 * Only these four fields will be provided.
 * The other fields will be auto-populated, or will use default values.
 */
data class UserDTO(
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String
)