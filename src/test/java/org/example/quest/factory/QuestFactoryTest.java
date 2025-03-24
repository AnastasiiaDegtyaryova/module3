package org.example.quest.factory;

import org.example.quest.model.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuestFactoryTest {

    private final QuestFactory questFactory = QuestFactory.getInstance();

    @Test
    void testDesignPatternsQuestLoaded() {
        Quest quest = questFactory.getQuest(1);
        assertEquals("Патерни проєктування", quest.title());
        assertFalse(quest.questions().isEmpty());
    }

    @Test
    void testFullStackQuestLoaded() {
        Quest quest = questFactory.getQuest(4);
        assertEquals("FullStackSurvivalQuest: Виживання Java-розробника", quest.title());
    }

    @Test
    void testInvalidQuestThrowsException() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> questFactory.getQuest(99)
        );
        assertEquals("Невірний вибір квесту: 99", thrown.getMessage());
    }
}

