package org.example.quest.logging;

import org.example.quest.engine.Question;
import org.example.quest.engine.Quest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    public static void logStartGame(String playerName, Quest quest) {
        logger.info("Гравець '{}' починає квест: '{}'", playerName, quest.getTitle());
    }

    public static void logAnswer(int index, Question question, int selectedAnswer, boolean correct) {
        if (correct) {
            logger.info("Правильна відповідь на питання {}: '{}'", index + 1, question.getText());
        } else {
            logger.warn("Неправильна відповідь на питання {}: '{}'. Вибрано: {}", index + 1, question.getText(), selectedAnswer);
        }
    }

    public static void logNextQuestion(int nextIndex) {
        logger.debug("Перехід до наступного питання: {}", nextIndex + 1);
    }

    public static void logResult(String playerName, Quest quest, int score) {
        int total = quest.getQuestions().size();
        double percent = (double) score / total * 100;
        boolean passed = percent >= 90;

        int roundedPercent = (int) Math.round(percent);
        logger.info("Результат для '{}': {}/{} ({}%) - {}",
                playerName, score, total, roundedPercent,
                passed ? "Успішно пройдено" : "Не пройдено");
    }

    public static void logError(String message, Exception e) {
        logger.error("Помилка: {}", message, e);
    }

    public static void logGameRestart(String playerName) {
        logger.info("Гравець '{}' почав гру знову", playerName);
    }
}

