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
        <link rel="stylesheet" type="text/css" href="estilo.css" />
        <title>SisCon - Sistema de Consultas</title>
    </head>
    <body>
        <div class="container container-login">
            <div class="warning-login"><p>${mensagem}</p></div>
            <h1>SisCon - Sistema de Consultas</h1>
            <form  action="LoginServlet" method="post"><br/>
                <input type="text" placeholder="Nome de usuário" name="login" required><br/>

                <input type="password" placeholder="Senha" name="senha" required><br/>

                <button type="submit">Login</button><br/>
                <br/>
            </form>
            <form action="ListarMedicosServlet" method="get">
                <input type="submit" value="Listar Médicos" 
                     name="Submit" id="frm1_submit" />
            </form>
            <form class="form-listar" action="ListarMedicosServlet" method="post">
                <label for="especialidade"><b>Listar médicos por especialidade:</b></label><br/>
                <!-- tentar listar especialidades a partir do BD -->
                <input type="text" placeholder="Especialidade" name="especialidade" required><br/>

                <button type="submit">Listar</button><br/>
            </form>
        </div>
    </body>
</html>
