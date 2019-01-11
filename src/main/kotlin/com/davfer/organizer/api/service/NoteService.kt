package com.davfer.organizer.api.service

import com.davfer.organizer.api.data.Note
import com.davfer.organizer.api.data.NoteDTO
import com.davfer.organizer.api.reactor.NotesCountNotification
import com.davfer.organizer.api.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.bus.Event
import reactor.bus.EventBus
import java.time.Instant

@Service("Note service")
class NoteService {

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    lateinit var repository: NoteRepository

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var eventBus: EventBus

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
    fun insertNote(note: NoteDTO): NoteDTO {
        val result = NoteDTO(
            repository.save(
                Note(
                    title = note.title,
                    message = note.message,
                    location = note.location
                )
            )
        )
        val count = getNotes().count()
        if (count > 10) {
            val notification = NotesCountNotification(count)
            eventBus.notify("notesCountNotificationConsumer", Event.wrap(notification))
        }
        return result
    }

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

    fun findByTitle(title: String): Iterable<NoteDTO> {
        return repository.findByTitle(title).map { it -> NoteDTO(it) }
    }
}