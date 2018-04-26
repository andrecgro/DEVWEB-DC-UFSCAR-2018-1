/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.consultas.servlets;

import br.ufscar.dc.consultas.beans.Medico;
import br.ufscar.dc.consultas.beans.Usuario;
import br.ufscar.dc.consultas.daos.MedicoDAO;
import br.ufscar.dc.consultas.daos.UsuarioDAO;
import br.ufscar.dc.consultas.forms.CadastrarMedicoForm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "CadastrarMedicoServlet", urlPatterns = {"/CadastrarMedicoServlet"})
public class CadastrarMedicoServlet extends HttpServlet {

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
            throws ServletException, IOException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");
        
        CadastrarMedicoForm form = new CadastrarMedicoForm();
        
        MedicoDAO mdao = new MedicoDAO(dataSource);
        Medico m = new Medico();

        Usuario u = new Usuario();
        
        String mensagem;
        try{
            
            BeanUtils.populate(form, request.getParameterMap());
            String crm = form.getCrm().replaceAll("[\\-s.]", "");
            if (mdao.buscarMedico(crm) == null){

                u.setNomeLogin(crm);
                u.setSenha(form.getSenha());
                u.setAdmin(form.isAdmin());
                u.setNome(form.getNome());
                u.setTipo("medico");
                m.setCrm(crm);

                m.setEspecialidade(form.getEspecialidade());
                m.setUsuario(u);

                mdao.gravarMedico(m);

                mensagem = "Médico cadastrado com sucesso.";
                request.setAttribute("mensagem", mensagem);
                request.getRequestDispatcher("admin/cadastrarMedico.jsp").forward(request, response);


            }
            else{ 
                mensagem = "Médico já cadastrado.";
                request.setAttribute("mensagem", mensagem);
                request.getRequestDispatcher("admin/cadastrarMedico.jsp").forward(request, response);
            }
        }
        catch (Exception e) {
            request.setAttribute("mensagem", e.getLocalizedMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CadastrarMedicoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CadastrarMedicoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
