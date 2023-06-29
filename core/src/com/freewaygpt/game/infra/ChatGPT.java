package com.freewaygpt.game.infra;

import com.freewaygpt.game.entity.QuestionModel;
import com.google.gson.Gson;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ChatGPT extends OpenAiService {
    public ChatGPT() {
        super("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");
    }

    public QuestionModel generateQuestion(String prompt) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .model("text-davinci-003")
                .maxTokens(256)
                .build();

            CompletionChoice response = createCompletion(completionRequest).getChoices().get(0);
            String gptResponse = response.getText();

            Gson gson = new Gson();

            return gson.fromJson(gptResponse, QuestionModel.class);
    }
}
