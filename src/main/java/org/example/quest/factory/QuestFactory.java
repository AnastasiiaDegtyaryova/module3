package org.example.quest.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.quest.engine.Quest;
import org.example.quest.engine.Question;
import org.example.quest.engine.StaticQuest;
import org.example.quest.model.QuestData;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuestFactory {

    public static Quest getQuest(int id) {
        try {
            return switch (id) {
                case 1 -> loadFromJson("quests/design_patterns.json");
                case 2 -> loadFromJson("quests/web_technologies.json");
                case 3 -> loadFromJson("quests/dev_tools.json");
                case 4 -> {
                    List<Question> allQuestions = new ArrayList<>();
                    allQuestions.addAll(loadFromJson("quests/design_patterns.json").getQuestions());
                    allQuestions.addAll(loadFromJson("quests/web_technologies.json").getQuestions());
                    allQuestions.addAll(loadFromJson("quests/dev_tools.json").getQuestions());
                    yield new StaticQuest("FullStackSurvivalQuest: Виживання Java-розробника", allQuestions);
                }
                default -> throw new IllegalArgumentException("Невірний вибір квесту: " + id);
            };
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося завантажити квест: " + e.getMessage(), e);
        }
    }

    private static Quest loadFromJson(String resourcePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = QuestFactory.class.getClassLoader().getResourceAsStream(resourcePath);

        if (is == null) {
            throw new IllegalArgumentException("Файл не знайдено: " + resourcePath);
        }

        QuestData data = mapper.readValue(is, QuestData.class);
        return new StaticQuest(data.getTitle(), data.getQuestions());
    }
}


