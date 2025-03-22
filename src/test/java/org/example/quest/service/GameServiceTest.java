package org.example.quest.service;

import org.example.quest.engine.Question;
import org.example.quest.engine.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    private HttpSession session;
    private Quest quest;
    private List<Question> questions;

    @BeforeEach
    void setUp() {
        session = mock(HttpSession.class);

        questions = List.of(
                new Question("2+2=?", List.of("3", "4", "5"), 1)
        );

        quest = mock(Quest.class);
        when(quest.getTitle()).thenReturn("Тестовий квест");
        when(quest.getQuestions()).thenReturn(questions);

        when(session.getAttribute("quest")).thenReturn(quest);
        when(session.getAttribute("questionIndex")).thenReturn(0);
        when(session.getAttribute("score")).thenReturn(0);
        when(session.getAttribute("incorrectAnswers")).thenReturn(new ArrayList<>());
    }

    @Test
    void testCorrectAnswerIncreasesScore() {
        boolean result = GameService.checkAnswer(session, 1);
        assertTrue(result);
        verify(session).setAttribute("score", 1);
    }

    @Test
    void testIncorrectAnswerAddedToList() {
        List<Integer> incorrect = new ArrayList<>();
        when(session.getAttribute("incorrectAnswers")).thenReturn(incorrect);

        boolean result = GameService.checkAnswer(session, 0);
        assertFalse(result);
        assertEquals(List.of(0), incorrect);
    }

    @Test
    void testMoveToNextQuestionIncrementsIndex() {
        when(session.getAttribute("questionIndex")).thenReturn(2);
        GameService.moveToNextQuestion(session);
        verify(session).setAttribute("questionIndex", 3);
    }
}

