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
            <h2>Cadastro de Paciente</h2>
            <div class="warning"><p>${mensagem}</p></div>
            
            <form  action="../CadastrarPacienteServlet" method="post">
                <label for="nome"><b>Nome:</b></label><br/>
                <input type="text" placeholder="Nome" name="nome" required><br/>

                <label for="cpf"><b>CPF:</b></label><br/>
                <input type="text" placeholder="CPF" name="cpf" required><br/>

                <label for="telefone"><b>Telefone:</b></label><br/>
                <input type="text" placeholder="Telefone" name="telefone" required><br/>
                
                
                <label for="dataDeNascimento"><b>Data de Nascimento:</b></label><br/>
                <input type="text" placeholder="DD/MM/AAAA" name="dataDeNascimento" value="" required><br/>

                
                <label for="sexo"><b>Sexo:</b></label><br/>
                <input type="radio" name="sexo" value="M">Masculino
                <input type="radio" name="sexo" value="F" selected="true">Feminino<br>
                
                <label for="senha"><b>Senha:</b></label><br/>
                <input type="password" placeholder="Senha" name="senha" required><br/>
                
                
                <label for="admin"><b>Administrador:</b></label><br/>
                <input type="radio" name="admin" value="true">Sim
                <input type="radio" name="admin" value="false" selected="true">Não<br>
                
                <button type="submit">Cadastrar Paciente</button><br/>
                <br/>
            </form>
        </div>
    </body>
</html>
