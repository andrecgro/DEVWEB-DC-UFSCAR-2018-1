<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Consultas</title>
        <link rel="stylesheet" type="text/css" href="estilo.css" />
    </head>
    <body>
        <h1>Médicos Cadastrados</h1>
        <hr>
        <div class="container">
            
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