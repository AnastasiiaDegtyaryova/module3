package com.quest.web;

public final class AppConstants {

    private AppConstants() {}

    public static final class Session {
        public static final String PLAYER_NAME = "playerName";
        public static final String QUEST = "quest";
        public static final String SCORE = "score";
        public static final String QUESTION_INDEX = "questionIndex";
        public static final String INCORRECT_ANSWERS = "incorrectAnswers";
        public static final String GAMES_PLAYED_MAP = "gamesPlayedMap";
        public static final String LAST_ANSWER_CORRECT = "lastAnswerCorrect";
        public static final String SELECTED_ANSWER_INDEX = "selectedAnswerIndex";
    }

    public static final class RequestParam {
        public static final String QUEST_ID = "quest";
        public static final String PLAYER_NAME = "playerName";
        public static final String ANSWER = "answer";
    }

    public static final class Pages {
        public static final String GAME = "game";
        public static final String GAME_JSP = "game.jsp";
        public static final String RESULT = "result.jsp";
        public static final String INDEX = "index.jsp";
    }
}


