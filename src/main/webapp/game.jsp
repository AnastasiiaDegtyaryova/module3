<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.example.quest.engine.Question" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>Питання</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="container">

    <h2><c:out value="${title}" /></h2>
    <p><b>Питання ${index + 1}:</b> <c:out value="${question.text}" /></p>

    <form action="question" method="post">
        <c:forEach var="option" items="${question.options}" varStatus="loop">
            <c:set var="selected" value="${loop.index == sessionScope.selectedAnswerIndex}" />
            <c:set var="isCorrect" value="${loop.index == question.correctOptionIndex}" />

            <label for="option${loop.index}"
                <c:if test="${not empty sessionScope.lastAnswerCorrect}">
                    class="
                        <c:choose>
                            <c:when test="${selected && isCorrect}">correct</c:when>
                            <c:when test="${selected && !isCorrect}">incorrect</c:when>
                        </c:choose>
                    "
                </c:if>
            >
                <input type="radio" id="option${loop.index}" name="answer" value="${loop.index}" required
                       <c:if test="${not empty sessionScope.lastAnswerCorrect}">disabled</c:if>
                       <c:if test="${selected}">checked</c:if>
                >
                <c:out value="${option}" />
            </label><br/>
        </c:forEach>

        <br/>

        <c:if test="${empty sessionScope.lastAnswerCorrect}">
            <button type="submit">Відповісти</button>
        </c:if>
    </form>

    <c:if test="${not empty sessionScope.lastAnswerCorrect}">
        <p class="feedback" style="color: ${sessionScope.lastAnswerCorrect ? 'green' : 'red'};">
            <c:out value="${sessionScope.lastAnswerCorrect ? 'Правильно!' : 'Неправильно!'}" />
        </p>

        <form action="next" method="post">
            <button type="submit">Наступне питання</button>
        </form>

        <% session.removeAttribute("lastAnswerCorrect"); %>
        <% session.removeAttribute("selectedAnswerIndex"); %>
    </c:if>

</div>
</body>
</html>
