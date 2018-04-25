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
        <h1>Lista de Consultas</h1>
        <hr>
        <div class="container">
            
            <c:if test="${empty requestScope.listaConsultas}">
                Não há consultas!
            </c:if>
            <c:if test="${!empty requestScope.listaConsultas}">
                <table>
                    <tr>
                        <c:choose>
                            <c:when test="${sessionScope.tipo == 'medico'}">
                                <th>Nº</th>
                                <th>DATA</th>
                                <th>PACIENTE</th>
                                <th>CPF</th>
                                <th>SEXO</th>
                                <th>TELEFONE</th>
                                
                            </c:when>
                            
                            <c:when test="${sessionScope.tipo == 'paciente'}">
                                <th>Nº</th>
                                <th>DATA</th>
                                <th>MEDICO</th>
                                <th>CRM</th>
                                <th>ESPECIALIDADE</th>
                            </c:when>
                            <c:otherwise>Entrou no otherwise</c:otherwise>
                        </c:choose>
                    </tr>
                    <c:forEach items="${requestScope.listaConsultas}" var="consulta">
                        <tr>
                            <c:choose>
                                <c:when test="${sessionScope.tipo == 'medico'}">
                                    <td>${consulta.id}</td>
                                    <td>${consulta.dataExame}</td>
                                    <td>${consulta.paciente.usuario.nome}</td>
                                    <td>${consulta.paciente.cpf}</td>
                                    <td>${consulta.paciente.sexo}</td>
                                    <td>${consulta.paciente.telefone}</td>
                                </c:when>
                                <c:when test="${sessionScope.tipo == 'paciente'}">
                                    <td>${consulta.id}</td>
                                    <td>${consulta.dataExame}</td>
                                    <td>${consulta.medico.usuario.nome}</td>
                                    <td>${consulta.medico.crm}</td>
                                    <td>${consulta.medico.especialidade}</td>
                                </c:when>
                                <c:otherwise>Entrou no otherwise</c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </body>
</html>