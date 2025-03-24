package org.example.quest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public record Question(String text, List<String> options, int correctOptionIndex) {
    @JsonCreator
    public Question(
            @JsonProperty("text") String text,
            @JsonProperty("options") List<String> options,
            @JsonProperty("correctOptionIndex") int correctOptionIndex) {
        this.text = text;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public boolean isCorrect(int userChoice) {
        return userChoice == correctOptionIndex;
    }
}

