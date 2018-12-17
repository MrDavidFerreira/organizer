package com.davfer.organizer.api.data

import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.util.ProxyUtils
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "note")
@JsonInclude(JsonInclude.Include.NON_NULL)
class Note(
    var title: String,
    var message: String,
    var location: String = ""
) {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(36)")
    var id: String? = null
    @CreationTimestamp
    lateinit var created: Instant
    @UpdateTimestamp
    lateinit var modified: Instant

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as Note

        return this.id == other.id
    }

    override fun hashCode(): Int {
        return 31
    }

    override fun toString() = "${this.javaClass.name}[id=$id]"
}