package com.smarttek.chatgptbot.client;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGptClientImpl implements ChatGptClient {

    @Value("${api.token}")
    private String apiToken;
    @Value("${api.url}")
    private String urlCompletions;
    @Value("${api.model}")
    private String model;

    @Override
    public String getResponse(String msg) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiToken);

        HttpEntity<String> requestEntity = getHttpEntity(msg, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(urlCompletions, requestEntity, String.class);

        JSONObject responseJson = new JSONObject(responseEntity.getBody());
        JSONArray choices = responseJson.getJSONArray("choices");

        JSONObject firstChoice = choices.getJSONObject(0);
        JSONObject message = firstChoice.getJSONObject("message");
        return message.getString("content");
    }

    private HttpEntity<String> getHttpEntity(String msg, HttpHeaders headers) {
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful assistant.");

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", msg);

        JSONArray messages = new JSONArray();
        messages.put(systemMessage);
        messages.put(userMessage);

        JSONObject request = new JSONObject();
        request.put("model", model);
        request.put("messages", messages);
        request.put("max_tokens", 60);

        return new HttpEntity<>(request.toString(), headers);
    }
}
