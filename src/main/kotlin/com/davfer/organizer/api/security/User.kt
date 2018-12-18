package com.davfer.organizer.api.security

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
class User(
    @Column(unique = true, nullable = false)
    @NotNull
    @Email
    var email: String,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    var pwd: String,

    @NotBlank
    var firstName: String,

    @NotBlank
    var lastName: String,

    var roles: String,

    var enabled: Boolean = true,

    var accountNonExpired: Boolean = true,

    var accountNonLocked: Boolean = true,

    var credentialsNonExpired: Boolean = true
) : UserDetails {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(36)")
    var id: String? = null

    @CreationTimestamp
    open lateinit var created: Instant
    @UpdateTimestamp
    open lateinit var modified: Instant

    override fun getPassword(): String = pwd

    override fun isAccountNonExpired(): Boolean = accountNonExpired

    override fun isAccountNonLocked(): Boolean = accountNonLocked

    override fun isCredentialsNonExpired(): Boolean = credentialsNonExpired

    override fun getUsername(): String = email

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()
        roles
            .split(",")
            .forEach { it ->
                authorities.add(
                    SimpleGrantedAuthority(
                        it.trim()
                    )
                )
            }
        return authorities
    }

    override fun isEnabled(): Boolean = enabled
}