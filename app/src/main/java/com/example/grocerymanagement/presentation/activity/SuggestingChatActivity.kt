package com.example.grocerymanagement.presentation.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerymanagement.R
import com.example.grocerymanagement.presentation.adapter.chatbot.ChatAdapter
import com.example.grocerymanagement.presentation.viewModel.SuggestingChatViewModel
import io.noties.markwon.Markwon

class SuggestingChatActivity : AppCompatActivity() {

    private lateinit var recyclerViewChat: RecyclerView
    private lateinit var editTextPrompt: EditText
    private lateinit var buttonSend: Button
    private lateinit var chatAdapter: ChatAdapter

    private val viewModel: SuggestingChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggesting_box)

        // Khởi tạo Markwon
        val markwon = Markwon.create(this)

        // Khởi tạo adapter với danh sách rỗng ban đầu
        chatAdapter = ChatAdapter(mutableListOf(), markwon)

        // Khởi tạo các view
        recyclerViewChat = findViewById(R.id.recyclerViewChat)
        editTextPrompt = findViewById(R.id.editTextPrompt)
        buttonSend = findViewById(R.id.buttonSend)

        recyclerViewChat.layoutManager = LinearLayoutManager(this)
        recyclerViewChat.adapter = chatAdapter

        // Observe LiveData từ ViewModel
        viewModel.messages.observe(this, Observer { messages ->
            chatAdapter.updateMessages(messages)
            recyclerViewChat.scrollToPosition(messages.size - 1)
        })

        buttonSend.setOnClickListener {
            val prompt = editTextPrompt.text.toString().trim()
            if (prompt.isNotEmpty()) {
                viewModel.sendPrompt(prompt)
                editTextPrompt.text.clear()
            }
        }
    }
}
