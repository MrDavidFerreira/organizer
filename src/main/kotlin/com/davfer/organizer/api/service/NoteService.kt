package com.davfer.organizer.api.service

import com.davfer.organizer.api.data.Note
import com.davfer.organizer.api.data.NoteDTO
import com.davfer.organizer.api.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service("Note service")
class NoteService {

    @Autowired
    lateinit var repository: NoteRepository

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    fun getNotes(): Iterable<NoteDTO> = repository.findAll().map { it -> NoteDTO(it) }

    /**
     * Saves a given entity. Use the returned instance for further operations as
     * the save operation might have changed the entity instance completely.
     *
     * @param noteDTO must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     */
    fun insertNote(noteDTO: NoteDTO): NoteDTO = NoteDTO(
        repository.save(
            Note(
                title = noteDTO.title,
                message = noteDTO.message,
                location = noteDTO.location
            )
        )
    )

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    fun deleteNote(id: String) = repository.deleteById(id)

    /**
     * Saves a given entity. Use the returned instance for further operations as
     * the save operation might have changed the entity instance completely.
     *
     * @param noteDto must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     */
    fun updateNote(noteDto: NoteDTO): NoteDTO {
        var note = repository.findById(noteDto.id).get()
        note.title = noteDto.title
        note.message = noteDto.message
        note.location = noteDto.location
        note.modified = Instant.now()
        note = repository.save(note)
        return NoteDTO(note)
    }
}