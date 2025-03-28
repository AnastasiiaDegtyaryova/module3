package com.quest.web;

import org.example.quest.model.Quest;
import org.example.quest.service.GameService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {

    private GameService gameService;

    @Override
    public void init() {
        gameService = GameService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String answerParam = request.getParameter(AppConstants.RequestParam.ANSWER);

        if (answerParam == null) {
            response.sendRedirect(AppConstants.Pages.GAME);
            return;
        }

        int userAnswer = Integer.parseInt(answerParam);
        HttpSession session = request.getSession();

        Quest quest = (Quest) session.getAttribute(AppConstants.Session.QUEST);
        Integer index = (Integer) session.getAttribute(AppConstants.Session.QUESTION_INDEX);

        if (index >= quest.questions().size()) {
            response.sendRedirect(AppConstants.Pages.RESULT);
            return;
        }

        boolean correct = gameService.checkAnswer(session, userAnswer);
        session.setAttribute(AppConstants.Session.LAST_ANSWER_CORRECT, correct);
        session.setAttribute(AppConstants.Session.SELECTED_ANSWER_INDEX, userAnswer);
        response.sendRedirect(AppConstants.Pages.GAME);
    }
}

