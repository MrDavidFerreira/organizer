package com.davfer.organizer.api.service

import com.davfer.organizer.api.data.Todo
import com.davfer.organizer.api.data.TodoDTO
import com.davfer.organizer.api.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service("Todo service")
class TodoService {

    @Autowired
    lateinit var repository: TodoRepository

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    fun getTodos(): Iterable<TodoDTO> = repository.findAll().map { it -> TodoDTO(it) }

    /**
     * Saves a given entity. Use the returned instance for further operations as
     * the save operation might have changed the entity instance completely.
     *
     * @param todoDTO must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     */
    fun insertTodo(todoDTO: TodoDTO): TodoDTO = TodoDTO(
        repository.save(
            Todo(
                title = todoDTO.title,
                message = todoDTO.message,
                location = todoDTO.location,
                schedule = todoDTO.schedule
            )
        )
    )

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    fun deleteTodo(id: String) = repository.deleteById(id)

    /**
     * Saves a given entity. Use the returned instance for further operations as
     * the save operation might have changed the entity instance completely.
     *
     * @param todoDto must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     */
    fun updateTodo(todoDto: TodoDTO): TodoDTO {
        var todo = repository.findById(todoDto.id).get()
        todo.title = todoDto.title
        todo.message = todoDto.message
        todo.location = todoDto.location
        todo.schedule = todoDto.schedule
        todo.modified = Instant.now()
        todo = repository.save(todo)
        return TodoDTO(todo)
    }
}