package org.example.quest.engine;

import java.util.List;

public interface Quest {
    String getTitle();
    List<Question> getQuestions();
}