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
        <link rel="stylesheet" type="text/css" href="estilo.css" />
        <title>SisCon - Sistema de Consultas</title>
    </head>
    <body>
        <h1 class="title">SisCon - Sistema de Consultas</h1>
        <div class="container container-forms">
            <h2>Cadastro de Consulta</h2>
            <div class="warning"><p>${mensagem}</p></div>
            
            <form  action="CadastrarConsultaServlet" method="post">
                <label for="crm"><b>CRM:</b></label><br/>
                <input type="text" placeholder="CRM" name="crm" required><br/>
                
                <label for="dataExame"><b>Data da Consulta:</b></label><br/>
                <input type="text" placeholder="Data Exame" value="" name="dataExame" required><br/>

                
                
                <button type="submit">Cadastrar Consulta</button><br/>
                <br/>
            </form>
        </div>
    </body>
</html>
