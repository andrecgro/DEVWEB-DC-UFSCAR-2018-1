<%-- 
    Document   : cadastrarMedico
    Created on : 24/04/2018, 12:53:46
    Author     : andrerocha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../estilo.css" />
        <title>SisCon - Sistema de Consultas</title>
    </head>
    <body>
        <h1 class="title">SisCon - Sistema de Consultas</h1>
        <div class="container container-forms">
            <h2>Cadastro de Médicos</h2>
            <div class="warning"><p>${mensagem}</p></div>
            
            <form  action="../CadastrarMedicoServlet" method="post">
                <label for="nome"><b>Nome:</b></label><br/>
                <input type="text" placeholder="Nome" name="nome" required><br/>

                <label for="crm"><b>CRM:</b></label><br/>
                <input type="text" placeholder="CRM" name="crm" required><br/>

                <label for="especialidade"><b>Especialidade:</b></label><br/>
                <input type="text" placeholder="Especialidade" name="especialidade" required><br/>
                
                <label for="senha"><b>Senha:</b></label><br/>
                <input type="password" placeholder="Senha" name="senha" required><br/>
                
                
                <label for="admin"><b>Administrador:</b></label><br/>
                <input type="radio" name="admin" value="true">Sim
                <input type="radio" name="admin" value="false" selected="true">Não<br>
                
                <button type="submit">Cadastrar Médico</button><br/>
                <br/>
            </form>
        </div>
    </body>
</html>
