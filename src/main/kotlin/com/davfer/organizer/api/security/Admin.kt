package com.davfer.organizer.api.security

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(value = "ADMIN")
class Admin(
    email: String,
    pwd: String,
    firstName: String,
    lastName: String,
    roles: String,
    enabled: Boolean = true,
    accountNonExpired: Boolean = true,
    accountNonLocked: Boolean = true,
    credentialsNonExpired: Boolean = true
) : User(
    email,
    pwd,
    firstName,
    lastName,
    roles,
    enabled,
    accountNonExpired,
    accountNonLocked,
    credentialsNonExpired
)