package com.quest.web;

import org.example.quest.factory.QuestFactory;
import org.example.quest.model.Quest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/game")
public class GameServlet extends BaseServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServlet.class);

    private QuestFactory questFactory;

    @Override
    public void init() {
        questFactory = QuestFactory.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String newName = getPlayerName(request);
        HttpSession session = request.getSession(true);
        Map<String, Integer> gamesPlayedMap = setUpGameMapIfNeeded(session, newName);
        Quest quest = getQuest(request);
        String questTitle = quest.title();
        gamesPlayedMap.put(questTitle, gamesPlayedMap.getOrDefault(questTitle, 0) + 1);

        setUpSession(session, gamesPlayedMap, newName, quest);

        LOGGER.info("Гравець '{}' починає квест: '{}'", newName, quest.title());
        response.sendRedirect(AppConstants.Pages.GAME);
    }

    private Map<String, Integer> setUpGameMapIfNeeded(HttpSession session, String newName) {
        String previousName = getSessionStringParameter(session, AppConstants.Session.PLAYER_NAME);
        if (previousName == null || !previousName.equals(newName)) {
            final HashMap<String, Integer> gameMap = new HashMap<>();
            session.setAttribute(AppConstants.Session.GAMES_PLAYED_MAP, gameMap);
            return gameMap;
        } else {
            return getSessionParameter(session, AppConstants.Session.GAMES_PLAYED_MAP, Map.class);
        }
    }

    private Quest getQuest(HttpServletRequest request) {
        int questId = Integer.parseInt(request.getParameter(AppConstants.RequestParam.QUEST_ID));
        return questFactory.getQuest(questId);
    }

    private void setUpSession(
            final HttpSession session,
            Map<String, Integer> gamesPlayedMap,
            String newName,
            Quest quest
    ) {
        session.setAttribute(AppConstants.Session.GAMES_PLAYED_MAP, gamesPlayedMap);
        session.setAttribute(AppConstants.Session.PLAYER_NAME, newName);
        session.setAttribute(AppConstants.Session.INCORRECT_ANSWERS, new ArrayList<Integer>());
        session.setAttribute(AppConstants.Session.QUEST, quest);
        session.setAttribute(AppConstants.Session.QUESTION_INDEX, 0);
        session.setAttribute(AppConstants.Session.SCORE, 0);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Quest quest = (Quest) session.getAttribute(AppConstants.Session.QUEST);
        Integer index = (Integer) session.getAttribute(AppConstants.Session.QUESTION_INDEX);

        if (quest == null || index == null) {
            response.sendRedirect(AppConstants.Pages.INDEX);
            return;
        }

        if (index >= quest.questions().size()) {
            String playerName = (String) session.getAttribute(AppConstants.Session.PLAYER_NAME);
            int score = (int) session.getAttribute(AppConstants.Session.SCORE);
            logResult(playerName, quest, score);
            request.getRequestDispatcher(AppConstants.Pages.RESULT).forward(request, response);
        } else {
            request.setAttribute("question", quest.questions().get(index));
            request.setAttribute("index", index);
            request.setAttribute("title", quest.title());
            request.getRequestDispatcher(AppConstants.Pages.GAME_JSP).forward(request, response);
        }
    }

    private void logResult(String playerName, Quest quest, int score) {
        int total = quest.questions().size();
        double percent = (double) score / total * 100;
        boolean passed = percent >= 90;

        int roundedPercent = (int) Math.round(percent);
        LOGGER.info("Результат для '{}': {}/{} ({}%) - {}",
                playerName, score, total, roundedPercent,
                passed ? "Успішно пройдено" : "Не пройдено");
    }
}
