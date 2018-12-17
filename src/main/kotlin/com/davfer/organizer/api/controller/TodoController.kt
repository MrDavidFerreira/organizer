package com.davfer.organizer.api.controller

import com.davfer.organizer.api.data.TodoDTO
import com.davfer.organizer.api.service.TodoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todos")
class TodoController {

    @Autowired
    private lateinit var service: TodoService

    /**
     * Get todos
     */
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTodos(): Iterable<TodoDTO> = service.getTodos()

    /**
     * Insert item.
     */
    @PutMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun insertTodo(@RequestBody todo: TodoDTO): TodoDTO = service.insertTodo(todo)

    /**
     * Remove item by Id.
     */
    @DeleteMapping(
        value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun deleteTodo(@PathVariable(name = "id") id: String) = service.deleteTodo(id)

    /**
     * Update item.
     */
    @PostMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateTodo(@RequestBody todo: TodoDTO): TodoDTO = service.updateTodo(todo)

}