package org.example.quest.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.quest.model.Quest;
import org.example.quest.model.Question;
import org.example.quest.model.StaticQuest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuestFactory {

    private static QuestFactory instance;

    private final ObjectMapper mapper;

    private QuestFactory() {
        this.mapper = new ObjectMapper();
    }

    public static QuestFactory getInstance() {
        if (instance == null) {
            instance = new QuestFactory();
        }
        return instance;
    }

    public Quest getQuest(int id) {
        return switch (id) {
            case 1 -> loadFromJson("quests/design_patterns.json");
            case 2 -> loadFromJson("quests/web_technologies.json");
            case 3 -> loadFromJson("quests/dev_tools.json");
            case 4 -> {
                List<Question> allQuestions = new ArrayList<>();
                allQuestions.addAll(loadFromJson("quests/design_patterns.json").questions());
                allQuestions.addAll(loadFromJson("quests/web_technologies.json").questions());
                allQuestions.addAll(loadFromJson("quests/dev_tools.json").questions());
                yield new StaticQuest("FullStackSurvivalQuest: Виживання Java-розробника", allQuestions);
            }
            default -> throw new IllegalArgumentException("Невірний вибір квесту: " + id);
        };
    }

    private Quest loadFromJson(String resourcePath) {
        try (InputStream is = QuestFactory.class.getClassLoader().getResourceAsStream(resourcePath)) {
            StaticQuest data = mapper.readValue(is, StaticQuest.class);
            return new StaticQuest(data.title(), data.questions());
        } catch (IOException e) {
            throw new IllegalStateException("Не вдалося завантажити квест: " + e.getMessage(), e);
        }
    }
}


