package com.example.grocerymanagement.presentation.adapter.chatbot
data class Message(val text: String,
                   val isUser: Boolean,
                   val isLoading: Boolean = false
)