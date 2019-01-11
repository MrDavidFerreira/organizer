package com.davfer.organizer.api.reactor

import com.davfer.organizer.api.mail.MailMessage
import com.davfer.organizer.api.mail.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TodosCountNotificationServiceImpl : TodosCountNotificationService {

    @Autowired
    private lateinit var mailService: MailService

    override fun notify(notification: TodosCountNotification) {
        val to = "mrjdavidfg@gmail.com"
        val subject = "Todos count notification"
        val text = "Todos reached ${notification.todosCount} count."
        val message = MailMessage(to, subject, text)
        mailService.sendMessage(message)
    }

}