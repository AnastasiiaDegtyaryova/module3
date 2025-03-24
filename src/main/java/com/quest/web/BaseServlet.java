package com.quest.web;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.InvalidParameterException;

public abstract class BaseServlet extends HttpServlet {

    protected String getPlayerName(HttpServletRequest request) {
        return request.getParameter("playerName");
    }

    protected Integer getIntegerName(HttpServletRequest request, String name) {
        final String parameter = request.getParameter(name);
        if (StringUtils.isNumeric(parameter)) {
            return Integer.parseInt(parameter);
        }
        throw new InvalidParameterException("Parameter " + name + " is not a number");
    }

    @SuppressWarnings("unchecked")
    protected String getSessionStringParameter(HttpSession session, String parameterName) {
        return (String) session.getAttribute(parameterName);
    }

    protected <T> T getSessionParameter(HttpSession session, String parameterName, Class<T> clazz) {
        return clazz.cast(session.getAttribute(parameterName));
    }
}
