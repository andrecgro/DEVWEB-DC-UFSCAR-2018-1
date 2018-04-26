<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <h2>Lista de Consultas</h2>
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
                            <c:otherwise>ADMIN</c:otherwise>
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
                                <c:otherwise>ADMIN</c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </body>
</html>