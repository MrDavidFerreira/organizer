package com.davfer.organizer.api.mail

interface MailService {
    fun sendMessage(message: MailMessage)
}