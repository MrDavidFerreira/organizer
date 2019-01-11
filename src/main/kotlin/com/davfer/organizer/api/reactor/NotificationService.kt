package com.davfer.organizer.api.reactor

interface NotificationService<in T> {
    fun notify(notification: T)
}