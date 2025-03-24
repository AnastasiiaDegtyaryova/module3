<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>FullStack Survival Quest</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="container">

    <h1 style="text-align: center;">Ласкаво просимо до <span style="color: #3498db;">FullStack Survival Quest</span>!</h1>

    <p style="font-size: 18px;">
        Це інтерактивна гра, у якій ти зможеш перевірити свої знання про патерни, веб-технології, тестування та фреймворки.
        Обери квест, введи своє ім’я — і вперед до перемоги!
    </p>

    <form action="game" method="post">
        <label style="display: block; margin-bottom: 15px;">
            Введіть ваше ім’я: <br/>
            <input type="text" name="playerName" required style="padding: 8px; width: 100%; max-width: 300px;">
        </label>

        <label style="display: block; margin-bottom: 15px;">
            Оберіть квест:
            <select name="quest" style="padding: 8px; width: 100%; max-width: 300px;">
                <option value="1">Патерни проєктування</option>
                <option value="2">Веб-технології</option>
                <option value="3">Maven, JUnit, Mockito, Логування</option>
                <option value="4">FullStackSurvivalQuest</option>
            </select>
        </label>

        <button type="submit">Почати гру</button>
    </form>

</div>
</body>
</html>

