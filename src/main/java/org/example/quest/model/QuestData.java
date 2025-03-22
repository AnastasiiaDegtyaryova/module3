package org.example.quest.model;

import lombok.Data;
import org.example.quest.engine.Question;
import java.util.List;

@Data
public class QuestData {
    private String title;
    private List<Question> questions;
}

