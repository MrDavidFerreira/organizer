package com.davfer.organizer.api.mail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class MailServiceImpl : MailService {

    @Autowired
    lateinit var sender: JavaMailSender

    override fun sendMessage(message: MailMessage) {
        var toSend = SimpleMailMessage()

        with(toSend) {
            setTo(message.to)
            setSubject(message.subject)
            setText(message.text)
        }
        sender.send(toSend)
    }
}