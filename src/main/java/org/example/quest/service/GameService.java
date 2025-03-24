package org.example.quest.service;

import com.quest.web.AppConstants;
import org.example.quest.model.Quest;
import org.example.quest.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.List;

public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private static GameService gameService;

    private GameService() {
    }

    public static GameService getInstance() {
        if (gameService == null) {
            gameService = new GameService();
        }
        return gameService;
    }

    public boolean checkAnswer(HttpSession session, int userAnswer) {
        Quest quest = (Quest) session.getAttribute(AppConstants.Session.QUEST);
        int index = (int) session.getAttribute(AppConstants.Session.QUESTION_INDEX);
        int score = (int) session.getAttribute(AppConstants.Session.SCORE);

        List<Question> questions = quest.questions();
        Question current = questions.get(index);

        boolean correct = current.isCorrect(userAnswer);
        if (correct) {
            session.setAttribute(AppConstants.Session.SCORE, score + 1);
        } else {
            List<Integer> incorrectAnswers = (List<Integer>) session.getAttribute(AppConstants.Session.INCORRECT_ANSWERS);
            if (incorrectAnswers != null) {
                incorrectAnswers.add(index);
            }
        }
        logAnswer(index, current, userAnswer, correct);
        return correct;
    }

    private void logAnswer(int index, Question question, int selectedAnswer, boolean correct) {
        if (correct) {
            LOGGER.info("Правильна відповідь на питання {}: '{}'", index + 1, question.text());
        } else {
            LOGGER.warn("Неправильна відповідь на питання {}: '{}'. Вибрано: {}", index + 1, question.text(), selectedAnswer);
        }
    }

    public boolean moveToNextQuestion(HttpSession session) {
        int index = (int) session.getAttribute(AppConstants.Session.QUESTION_INDEX);
        Quest quest = (Quest) session.getAttribute(AppConstants.Session.QUEST);

        index++;
        session.setAttribute(AppConstants.Session.QUESTION_INDEX, index);

        LOGGER.debug("Перехід до наступного питання: {}", index);
        return index < quest.questions().size();
    }
}

