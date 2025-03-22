package com.quest.web;

import org.example.quest.engine.Quest;
import org.example.quest.factory.QuestFactory;
import org.example.quest.logging.LoggerUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int questId = Integer.parseInt(request.getParameter(AppConstants.RequestParam.QUEST_ID));
        Quest quest = QuestFactory.getQuest(questId);
        String newName = request.getParameter(AppConstants.RequestParam.PLAYER_NAME);
        HttpSession session = request.getSession(true);
        String previousName = (String) session.getAttribute(AppConstants.Session.PLAYER_NAME);

        session.setAttribute(AppConstants.Session.PLAYER_NAME, newName);
        session.setAttribute(AppConstants.Session.INCORRECT_ANSWERS, new ArrayList<Integer>());

        if (previousName == null || !previousName.equals(newName)) {
            session.setAttribute(AppConstants.Session.GAMES_PLAYED_MAP, new HashMap<String, Integer>());
        }

        Map<String, Integer> gamesPlayedMap = (Map<String, Integer>) session.getAttribute(AppConstants.Session.GAMES_PLAYED_MAP);
        String questTitle = quest.getTitle();

        gamesPlayedMap.put(questTitle, gamesPlayedMap.getOrDefault(questTitle, 0) + 1);
        session.setAttribute(AppConstants.Session.GAMES_PLAYED_MAP, gamesPlayedMap);

        session.setAttribute(AppConstants.Session.QUEST, quest);
        session.setAttribute(AppConstants.Session.QUESTION_INDEX, 0);
        session.setAttribute(AppConstants.Session.SCORE, 0);

        LoggerUtil.logStartGame(newName, quest);

        response.sendRedirect(AppConstants.Pages.GAME);
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

        if (index >= quest.getQuestions().size()) {
            String playerName = (String) session.getAttribute(AppConstants.Session.PLAYER_NAME);
            int score = (int) session.getAttribute(AppConstants.Session.SCORE);
            LoggerUtil.logResult(playerName, quest, score);
            request.getRequestDispatcher(AppConstants.Pages.RESULT).forward(request, response);
        } else {
            request.setAttribute("question", quest.getQuestions().get(index));
            request.setAttribute("index", index);
            request.setAttribute("title", quest.getTitle());
            request.getRequestDispatcher(AppConstants.Pages.GAME_JSP).forward(request, response);
        }
    }
}
