<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.example.quest.model.Quest" %>
<%@ page import="org.example.quest.model.Question" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>Результат</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="container">

<%
    Integer score = (Integer) session.getAttribute("score");
    Quest quest = (Quest) session.getAttribute("quest");
    String playerName = (String) session.getAttribute("playerName");
    Map<String, Integer> gamesPlayedMap = (Map<String, Integer>) session.getAttribute("gamesPlayedMap");
    String questTitle = quest.title();
    Integer gamesPlayed = gamesPlayedMap != null ? gamesPlayedMap.get(questTitle) : null;

    List<Integer> incorrectAnswers = (List<Integer>) session.getAttribute("incorrectAnswers");
    List<Question> questions = quest.questions();

    double percent = (double) score / quest.questions().size();
    boolean passed = percent >= 0.9;
%>

<h2>Квест завершено!</h2>
<p>Гравець: <b><%= playerName %></b></p>
<p>Квест: <%= questTitle %></p>
<p>Результат: <%= score %> із <%= quest.questions().size() %></p>
<p>Цей квест пройдено: <%= gamesPlayed %> раз(ів)</p>

<% if (incorrectAnswers != null && !incorrectAnswers.isEmpty()) { %>
    <h3>Питання з помилками:</h3>
    <ul>
    <% for (Integer i : incorrectAnswers) { %>
        <li><%= questions.get(i).text() %></li>
    <% } %>
    </ul>
<% } else { %>
    <p>Всі відповіді правильні!</p>
<% } %>

<h3 style="color: <%= passed ? "green" : "red" %>;">
    Тест <%= passed ? "успішно пройдено" : "не пройдено" %>
</h3>

<br/>
<a href="index.jsp">
    <button>Почати знову</button>
</a>

</div>
</body>
</html>
