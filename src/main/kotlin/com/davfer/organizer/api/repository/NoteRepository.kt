package com.davfer.organizer.api.repository

import com.davfer.organizer.api.data.Note
import org.springframework.data.repository.CrudRepository

interface NoteRepository : CrudRepository<Note, String> {
    fun findByTitle(title: String): Iterable<Note>
}