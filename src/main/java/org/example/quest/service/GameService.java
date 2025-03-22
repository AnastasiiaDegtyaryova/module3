package org.example.quest.service;

import com.quest.web.AppConstants;
import org.example.quest.engine.Quest;
import org.example.quest.engine.Question;
import org.example.quest.logging.LoggerUtil;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GameService {

    public static boolean checkAnswer(HttpSession session, int userAnswer) {
        Quest quest = (Quest) session.getAttribute(AppConstants.Session.QUEST);
        int index = (int) session.getAttribute(AppConstants.Session.QUESTION_INDEX);
        int score = (int) session.getAttribute(AppConstants.Session.SCORE);

        List<Question> questions = quest.getQuestions();
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
        LoggerUtil.logAnswer(index, current, userAnswer, correct);
        return correct;
    }

    public static boolean moveToNextQuestion(HttpSession session) {
        int index = (int) session.getAttribute(AppConstants.Session.QUESTION_INDEX);
        Quest quest = (Quest) session.getAttribute(AppConstants.Session.QUEST);

        index++;
        session.setAttribute(AppConstants.Session.QUESTION_INDEX, index);

        LoggerUtil.logNextQuestion(index);
        return index < quest.getQuestions().size();
    }
}

