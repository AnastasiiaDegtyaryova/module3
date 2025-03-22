package org.example.quest.factory;

import org.example.quest.engine.Quest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuestFactoryTest {

    @Test
    void testDesignPatternsQuestLoaded() {
        Quest quest = QuestFactory.getQuest(1);
        assertEquals("Патерни проєктування", quest.getTitle());
        assertFalse(quest.getQuestions().isEmpty());
    }

    @Test
    void testFullStackQuestLoaded() {
        Quest quest = QuestFactory.getQuest(4);
        assertEquals("FullStackSurvivalQuest: Виживання Java-розробника", quest.getTitle());
    }

    @Test
    void testInvalidQuestThrowsException() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> QuestFactory.getQuest(99)
        );
        assertEquals("Невірний вибір квесту: 99", thrown.getMessage());
    }
}

