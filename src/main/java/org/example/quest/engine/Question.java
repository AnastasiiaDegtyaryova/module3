package org.example.quest.engine;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Question {
    private final String text;
    private final List<String> options;
    private final int correctOptionIndex;

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

