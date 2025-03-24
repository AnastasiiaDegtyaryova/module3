package org.example.quest.model;

import java.util.List;

public interface Quest {
    String title();
    List<Question> questions();
}