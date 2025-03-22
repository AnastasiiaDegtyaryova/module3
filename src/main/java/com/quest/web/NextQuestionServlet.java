package com.quest.web;

import org.example.quest.engine.Quest;
import org.example.quest.service.GameService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/next")
public class NextQuestionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        GameService.moveToNextQuestion(session);
        Quest quest = (Quest) session.getAttribute(AppConstants.Session.QUEST);
        int index = (int) session.getAttribute(AppConstants.Session.QUESTION_INDEX);

        if (index >= quest.getQuestions().size()) {
            response.sendRedirect(AppConstants.Pages.RESULT);
        } else {
            response.sendRedirect(AppConstants.Pages.GAME);
        }
    }
}
