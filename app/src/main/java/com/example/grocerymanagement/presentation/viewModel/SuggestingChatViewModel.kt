package com.example.grocerymanagement.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grocerymanagement.domain.chatbot.PromptSender
import com.example.grocerymanagement.presentation.adapter.chatbot.Message

class SuggestingChatViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>(emptyList())
    val messages: LiveData<List<Message>> = _messages

    fun sendPrompt(prompt: String) {
        val currentMessages = _messages.value?.toMutableList() ?: mutableListOf()
        currentMessages.add(Message(prompt, isUser = true))
        _messages.value = currentMessages

        PromptSender.send(prompt, object : PromptSender.PromptCallback {
            override fun onSuccess(responseText: String) {
                val updatedMessages = _messages.value?.toMutableList() ?: mutableListOf()
                updatedMessages.add(Message(responseText, isUser = false))
                _messages.postValue(updatedMessages)
            }

            override fun onError(errorMessage: String) {
                val updatedMessages = _messages.value?.toMutableList() ?: mutableListOf()
                updatedMessages.add(Message(errorMessage, isUser = false))
                _messages.postValue(updatedMessages)
            }
        })
    }
}
