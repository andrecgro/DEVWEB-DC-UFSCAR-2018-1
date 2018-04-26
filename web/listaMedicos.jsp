<%@page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="estilo.css" />
    <title>SisCon - Sistema de Consultas</title>
    </head>
    <body>
        <h1 class="title">SisCon - Sistema de Consultas</h1>
        <div class="container container-table">
            <h2>Lista de Médicos</h2>
            <c:if test="${empty requestScope.listaMedicos}">
                Não há médicos!
            </c:if>
            <c:if test="${!empty requestScope.listaMedicos}">
                <table>
                    <tr>
                        <th class="esquerda">CRM</th>
                        <th>Nome</th>
                        <th>Especialidade</th>
                    </tr>
                    <c:forEach items="${requestScope.listaMedicos}" var="medico">
                        <tr>
                            <td>${medico.crm}</td>
                            <td class="esquerda">${medico.usuario.nome}</td>
                            <td>${medico.especialidade}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </body>
</html>