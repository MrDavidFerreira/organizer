package com.davfer.organizer.api.controller

import com.davfer.organizer.api.data.NoteDTO
import com.davfer.organizer.api.service.NoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/notes")
class NoteController {

    @Autowired
    private lateinit var service: NoteService

    /**
     * Get notes.
     */
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getNotes(): Iterable<NoteDTO> = service.getNotes()

    /**
     * Insert item.
     */
    @PutMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun insertNote(@RequestBody note: NoteDTO): NoteDTO = service.insertNote(note)


    /**
     * Remove item by Id.
     */
    @DeleteMapping(
        value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun deleteNote(@PathVariable(name = "id") id: String) = service.deleteNote(id)

    /**
     * Update item.
     */
    @PostMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateNote(@RequestBody note: NoteDTO): NoteDTO = service.updateNote(note)
}