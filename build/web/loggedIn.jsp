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
        <title>JSP Page</title>
    </head>
    <body>
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
                    <div class="container">
                        
                        <a class="methodButtons ${sessionScope.tipo}" href="admin/cadastrarMedico.jsp">CADASTRAR MÉDICO</a>
                        <br>
                        <a class="methodButtons ${sessionScope.tipo}" href="admin/cadastrarPaciente.jsp">CADASTRAR PACIENTE</a>
                    </div>
                </c:when>
                <c:when test = "${sessionScope.tipo == 'medico'}">
                    <div class="container">
                        <a class="methodButtons ${sessionScope.tipo}" href="listarConsultasServlet?crm=${sessionScope.crm}&tipo=${sessionScope.tipo}">LISTAR CONSULTAS</a>
                    </div>
                </c:when>
                <c:when test = "${sessionScope.tipo == 'paciente'}">
                    <div class="container">
                        <a class="methodButtons ${sessionScope.tipo}" href="cadastrarConsulta.jsp">CADASTRAR CONSULTAS</a>
                        <br>
                        <a class="methodButtons ${sessionScope.tipo}" href="listarConsultasServlet?cpf=${sessionScope.cpf}&tipo=${sessionScope.tipo}">LISTAR CONSULTAS</a>
                    </div>
                </c:when>
                <c:otherwise>
                   <div class="warning"><p>Sem permissões para acessar funcionalidades extras</p></div>
                </c:otherwise>
            </c:choose>
        <%}%>
        
    </body>
</html>
