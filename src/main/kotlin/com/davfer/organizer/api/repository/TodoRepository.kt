package com.davfer.organizer.api.repository

import com.davfer.organizer.api.data.Todo
import org.springframework.data.repository.CrudRepository

interface TodoRepository : CrudRepository<Todo, String>