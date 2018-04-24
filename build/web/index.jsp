<%-- 
    Document   : index
    Created on : 12/04/2018, 11:21:40
    Author     : andrerocha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultas</title>
    </head>
    <body>
        <h1>Bem-vindo ao sistema de consultas.</h1>
        <hr>
        <div class="container">
            <div class="warning"><p>${mensagem}</p></div>
            <form  action="LoginServlet" method="post">
                <label for="login"><b>Usuário</b></label><br/>
                <input type="text" placeholder="Enter Username" name="login" required><br/>

                <label for="senha"><b>Senha</b></label><br/>
                <input type="password" placeholder="Enter Password" name="senha" required><br/>

                <button type="submit">Login</button><br/>
                <br/>
            </form>
            <a href="ListarMedicosServlet">Listar todos os médicos</a><br>
            <form action="ListarMedicosServlet" method="post">
                <label for="especialidade"><b>Listar médicos por especialidade:</b></label><br/>
                <!-- tentar listar especialidades a partir do BD -->
                <input type="text" placeholder="Especialidade" name="especialidade" required><br/>

                <button type="submit">Listar</button><br/>
            </form>
        </div>
    </body>
</html>
