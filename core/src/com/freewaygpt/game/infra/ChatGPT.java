package com.freewaygpt.game.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.freewaygpt.game.entity.QuestionModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ChatGPT extends OpenAiService {
    public ChatGPT() {
        super("");
    }

    public void promptBuilder(String prompt) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .model("text-davinci-003")
                .echo(true)
                .maxTokens(256)
                .build();



//        for (CompletionChoice response:this.createCompletion(completionRequest).getChoices()) {
//            CompletionChoice response = createCompletion(completionRequest).getChoices().get(0);
            CompletionChoice response = createCompletion(completionRequest).getChoices().get(0);
            String gptResponse = response.getText();

            System.out.println(response);
            System.out.println("=========================");
            System.out.println(gptResponse);

            Gson gson = new Gson();

            JsonObject questionModel = gson.fromJson(gptResponse, JsonObject.class);
//            QuestionModel questionModel = gson.fromJson(gptResponse, QuestionModel.class);
            System.out.println(questionModel.get("question"));
            System.out.println(questionModel.get("correct_answer"));
            System.out.println(questionModel.get("answer"));
//        }

    }
}
