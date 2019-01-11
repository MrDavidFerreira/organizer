package com.davfer.organizer.api

import com.davfer.organizer.api.reactor.NotesCountNotificationConsumer
import com.davfer.organizer.api.reactor.TodosCountNotificationConsumer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import reactor.Environment
import reactor.bus.EventBus
import reactor.bus.selector.Selectors.`$`

@SpringBootApplication
@EnableEurekaClient
class ApiApplication : CommandLineRunner {

    @Autowired
    private lateinit var eventBus: EventBus

    @Autowired
    private lateinit var notesCountNotificationConsumer: NotesCountNotificationConsumer

    @Autowired
    private lateinit var todosCountNotificationConsumer: TodosCountNotificationConsumer

    @Bean
    fun env() = Environment.initializeIfEmpty().assignErrorJournal()

    @Bean
    fun createEventBus(env: Environment) = EventBus.create(env, Environment.THREAD_POOL)

    override fun run(vararg args: String) {
        eventBus.on(`$`("notesCountNotificationConsumer"), notesCountNotificationConsumer)
        eventBus.on(`$`("todosCountNotificationConsumer"), todosCountNotificationConsumer)
    }
}

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
