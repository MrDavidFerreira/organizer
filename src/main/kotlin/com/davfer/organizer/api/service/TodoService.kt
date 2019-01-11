package com.davfer.organizer.api.service

import com.davfer.organizer.api.data.Todo
import com.davfer.organizer.api.data.TodoDTO
import com.davfer.organizer.api.reactor.TodosCountNotification
import com.davfer.organizer.api.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.bus.Event
import reactor.bus.EventBus
import java.time.Instant
import java.util.*

@Service("Todo service")
class TodoService {

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    lateinit var repository: TodoRepository

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var eventBus: EventBus

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
    fun insertTodo(todo: TodoDTO): TodoDTO {
        val result = TodoDTO(
            repository.save(
                Todo(
                    title = todo.title,
                    message = todo.message,
                    location = todo.location,
                    schedule = todo.schedule

                )
            )
        )
        val count = getTodos().count()
        if (count > 10) {
            val notification = TodosCountNotification(count)
            eventBus.notify("todosCountNotificationConsumer", Event.wrap(notification))
        }
        return result
    }

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

    fun getScheduledLaterThan(date: Date): Iterable<TodoDTO> {
        return repository.findByScheduleGreaterThan(date.time).map { it -> TodoDTO(it) }
    }
}