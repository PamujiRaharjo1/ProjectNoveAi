package com.example.testprojek;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AichatActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private Button btnSend;
    private RecyclerView recyclerViewChat;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aichat);

        editTextMessage = findViewById(R.id.editTextMessage);
        btnSend = findViewById(R.id.btnSend);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(chatAdapter);

        btnSend.setOnClickListener(view -> {
            String userMessage = editTextMessage.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                addMessage(new ChatMessage(userMessage, true)); // Pesan user
                editTextMessage.setText("");

                // Kirim ke OpenAI
                OpenAIService.sendMessage(userMessage, new OpenAIService.OpenAIResponse() {
                    @Override
                    public void onSuccess(String response) {
                        runOnUiThread(() -> addMessage(new ChatMessage(response, false))); // Pesan AI
                    }

                    @Override
                    public <ChatActivity> void onFailure(String error) {
                        runOnUiThread(() -> Toast.makeText(AichatActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });
    }

    private void addMessage(ChatMessage message) {
        chatMessages.add(message);
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        recyclerViewChat.scrollToPosition(chatMessages.size() - 1);

    }
}