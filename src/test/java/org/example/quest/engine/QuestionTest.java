package org.example.quest.engine;

import org.example.quest.model.Question;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void testGetText() {
        Question q = new Question("Що таке інкапсуляція, наслідування та поліморфізм?", List.of("Принципи ООП", "Фреймворки"), 0);
        assertEquals("Що таке інкапсуляція, наслідування та поліморфізм?", q.text());
    }

    @Test
    void testGetOptions() {
        List<String> options = List.of("A", "B", "C");
        Question q = new Question("Питання?", options, 2);
        assertEquals(options, q.options());
    }

    @Test
    void testGetCorrectOptionIndex() {
        Question q = new Question("Щось?", List.of("X", "Y"), 1);
        assertEquals(1, q.correctOptionIndex());
    }

    @Test
    void testIsCorrectWhenCorrect() {
        Question q = new Question("2+2=?", List.of("3", "4", "5"), 1);
        assertTrue(q.isCorrect(1));
    }

    @Test
    void testIsCorrectWhenIncorrect() {
        Question q = new Question("2+2=?", List.of("3", "4", "5"), 1);
        assertFalse(q.isCorrect(0));
        assertFalse(q.isCorrect(2));
    }
}

