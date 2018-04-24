/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.consultas.servlets;

import br.ufscar.br.consultas.forms.LoginForm;
import br.ufscar.dc.consultas.beans.Usuario;
import br.ufscar.dc.consultas.daos.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author andrerocha
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Resource(name = "jdbc/ConsultasDBLocal")
    DataSource dataSource;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        LoginForm form = new LoginForm();
        UsuarioDAO udao = new UsuarioDAO(dataSource);
        Usuario u = new Usuario();
        try {
            BeanUtils.populate(form, request.getParameterMap());
            
            u = udao.buscarUsuario(form.getLogin(), form.getSenha());
            request.getSession().setAttribute("login", form.getLogin());
            request.getSession().setAttribute("admin", u.isAdmin());
            request.getSession().setAttribute("tipo", u.getTipo());
            if(u.getTipo().equals("medico")){        
                request.getSession().setAttribute("crm", u.getNomeLogin());
            }
            else if(u.getTipo().equals("paciente")){
                request.getSession().setAttribute("cpf", u.getNomeLogin());
            }
            request.getRequestDispatcher("loggedIn.jsp").forward(request, response);
            
        } catch (Exception e) {
            String mensagem;
            if(u == null){
                mensagem = "Usuário ou senha inválidos.";
                request.setAttribute("mensagem", mensagem);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            else{
                request.setAttribute("mensagem", e.getLocalizedMessage());
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
