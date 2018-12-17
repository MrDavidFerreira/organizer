package com.davfer.organizer.api.data

import java.time.Instant

data class TodoDTO(
    var title: String,
    var message: String,
    var schedule: Long,
    var location: String = ""
) {
    lateinit var id: String
    lateinit var created: Instant
    lateinit var modified: Instant

    constructor(todo: Todo) : this(
        todo.title,
        todo.message,
        todo.schedule,
        todo.location
    ) {
        id = todo.id ?: ""
        created = todo.created
        modified = todo.modified
    }
}