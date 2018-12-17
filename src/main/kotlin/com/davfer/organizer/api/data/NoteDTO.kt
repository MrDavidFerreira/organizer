package com.davfer.organizer.api.data

import java.time.Instant

data class NoteDTO(
    var title: String,
    var message: String,
    var location: String = ""
) {
    lateinit var id: String
    lateinit var created: Instant
    lateinit var modified: Instant

    constructor(note: Note) : this(
        note.title,
        note.message,
        note.location
    ) {
        id = note.id ?: ""
        created = note.created
        modified = note.modified
    }
}