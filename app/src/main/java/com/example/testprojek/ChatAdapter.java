package com.example.testprojek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private final List<ChatMessage> chatMessages;

    public ChatAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage message = chatMessages.get(position);
        if (message.isUser()) {
            holder.userTextView.setVisibility(View.VISIBLE);
            holder.botTextView.setVisibility(View.GONE);
            holder.userTextView.setText(message.getText());
        } else {
            holder.botTextView.setVisibility(View.VISIBLE);
            holder.userTextView.setVisibility(View.GONE);
            holder.botTextView.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userTextView, botTextView;

        ViewHolder(View itemView) {
            super(itemView);
            userTextView = itemView.findViewById(R.id.textUser);
            botTextView = itemView.findViewById(R.id.textBot);
        }
    }
}