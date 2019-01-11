package com.davfer.organizer.api.reactor

import com.davfer.organizer.api.mail.MailMessage
import com.davfer.organizer.api.mail.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NotesCountNotificationServiceImpl : NotesCountNotificationService {

    @Autowired
    private lateinit var mailService: MailService

    override fun notify(notification: NotesCountNotification) {
        val to = "mrjdavidfg@gmail.com"
        val subject = "Notes count notification"
        val text = "Notes reached ${notification.notesCount} count."
        val message = MailMessage(to, subject, text)
        mailService.sendMessage(message)
    }

}