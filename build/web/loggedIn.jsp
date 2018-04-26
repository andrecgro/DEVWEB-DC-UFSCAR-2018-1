<%-- 
    Document   : loggedIn
    Created on : 20/04/2018, 08:18:18
    Author     : andrerocha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="estilo.css" />
        <title>SisCon - Sistema de Consultas</title>
    </head>
    <body>
        <h1 class="title">SisCon - Sistema de Consultas</h1>
        <%
        if (session.getAttribute("login") == null)
        {
          String address = "/index.jsp";
          
          RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
          
          dispatcher.forward(request,response);
        }
        else{%>
            <c:choose>
                <c:when test = "${sessionScope.tipo == 'admin'}">
                    <div class="container container-forms loggedIn">
                        
                        <a class="methodButtons ${sessionScope.tipo}" href="admin/cadastrarMedico.jsp">CADASTRAR MÉDICO</a>
                        
                        <a class="methodButtons ${sessionScope.tipo}" href="admin/cadastrarPaciente.jsp">CADASTRAR PACIENTE</a>
                    </div>
                </c:when>
                <c:when test = "${sessionScope.tipo == 'medico'}">
                    <div class="container container-forms loggedIn">
                        <a class="methodButtons ${sessionScope.tipo}" href="listarConsultasServlet?tipo=${sessionScope.tipo}&crm=${sessionScope.crm}">LISTAR CONSULTAS</a>
                    </div>
                </c:when>
                <c:when test = "${sessionScope.tipo == 'paciente'}">
                    <div class="container container-forms loggedIn">
                        <a class="methodButtons ${sessionScope.tipo}" href="cadastrarConsulta.jsp">CADASTRAR CONSULTAS</a>
                        
                        <a class="methodButtons ${sessionScope.tipo}" href="listarConsultasServlet?tipo=${sessionScope.tipo}&cpf=${sessionScope.cpf}">LISTAR CONSULTAS</a>
                    </div>
                </c:when>
                <c:otherwise>
                   <div class="warning"><p>Sem permissões para acessar funcionalidades extras</p></div>
                </c:otherwise>
            </c:choose>
        <%}%>
        
    </body>
</html>
