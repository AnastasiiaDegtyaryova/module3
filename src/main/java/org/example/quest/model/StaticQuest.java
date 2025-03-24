package org.example.quest.model;

import java.util.List;

public record StaticQuest(String title, List<Question> questions) implements Quest {
}


