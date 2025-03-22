package org.example.quest.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class StaticQuest implements Quest {
    private final String title;
    private final List<Question> questions;
}


