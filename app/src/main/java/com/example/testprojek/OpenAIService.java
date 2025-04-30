package com.example.testprojek;


import okhttp3.*;
import com.google.gson.JsonObject;
import java.io.IOException;

public class OpenAIService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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"; // Ganti dengan API key-mu
    private static final OkHttpClient client = new OkHttpClient();

    public interface OpenAIResponse {
        void onSuccess(String response);

        <ChatActivity> void onFailure(String error);
    }

    public static void sendMessage(String message, OpenAIResponse callback) {
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("model", "gpt-3.5-turbo"); // Bisa diganti ke model lain jika punya akses
        jsonBody.addProperty("messages", "[{\"role\": \"user\", \"content\": \"" + message + "\"}]");

        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JsonObject jsonResponse = new com.google.gson.JsonParser().parse(responseBody).getAsJsonObject();
                    String aiResponse = jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();
                    callback.onSuccess(aiResponse);
                } else {
                    callback.onFailure(response.message());
                }
            }
        });
    }
}
