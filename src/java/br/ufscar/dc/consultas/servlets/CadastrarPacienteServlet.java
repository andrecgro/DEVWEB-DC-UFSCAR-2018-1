/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.consultas.servlets;

import br.ufscar.dc.consultas.beans.Paciente;
import br.ufscar.dc.consultas.beans.Usuario;
import br.ufscar.dc.consultas.daos.PacienteDAO;
import br.ufscar.dc.consultas.forms.CadastrarPacienteForm;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "CadastrarPacienteServlet", urlPatterns = {"/CadastrarPacienteServlet"})
public class CadastrarPacienteServlet extends HttpServlet {

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

        
        CadastrarPacienteForm form = new CadastrarPacienteForm();
        try {
            BeanUtils.populate(form, request.getParameterMap());
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CadastrarPacienteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        PacienteDAO pdao = new PacienteDAO(dataSource);
        Paciente p = new Paciente();

        Usuario u = new Usuario();
        
        String mensagem;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNascimento = null;
        try{
            dataNascimento = sdf.parse(form.getDataDeNascimento());
        }catch(ParseException e){
            request.setAttribute("mensagem", e.getLocalizedMessage());
            request.getRequestDispatcher("erro.jsp").forward(request,response);
        }

        try{
            System.out.println("ENTROU NO TRY");
            String cpf = form.getCpf();
            System.out.println(form.getCpf());
            System.out.println(cpf);
            cpf = cpf.replaceAll("[\\-s.]", "");
            System.out.println(cpf);
            if (pdao.buscarPaciente(cpf) == null){
                u.setNomeLogin(cpf);
                u.setSenha(form.getSenha());
                u.setAdmin(form.isAdmin());
                u.setNome(form.getNome());
                u.setTipo("paciente");
                p.setCpf(cpf);
                p.setSexo(form.getSexo());
                p.setTelefone(form.getTelefone());
                p.setDataDeNascimento(dataNascimento);
                p.setUsuario(u);

                pdao.gravarPaciente(p);

                mensagem = "Paciente cadastrado com sucesso.";
                request.setAttribute("mensagem", mensagem);
                request.getRequestDispatcher("admin/cadastrarPaciente.jsp").forward(request, response);


            }
            else{ 
                mensagem = "Paciente já cadastrado.";
                request.setAttribute("mensagem", mensagem);
                request.getRequestDispatcher("admin/cadastrarPaciente.jsp").forward(request, response);
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
