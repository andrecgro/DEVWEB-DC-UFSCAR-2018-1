/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.consultas.servlets;

import br.ufscar.dc.consultas.beans.Consulta;
import br.ufscar.dc.consultas.beans.Medico;
import br.ufscar.dc.consultas.beans.Paciente;
import br.ufscar.dc.consultas.beans.Usuario;
import br.ufscar.dc.consultas.daos.ConsultaDAO;
import br.ufscar.dc.consultas.daos.MedicoDAO;
import br.ufscar.dc.consultas.daos.PacienteDAO;
import br.ufscar.dc.consultas.forms.CadastrarConsultaForm;
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
import javax.sql.DataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author andrerocha
 */
@WebServlet(name = "CadastrarConsultaServlet", urlPatterns = {"/CadastrarConsultaServlet"})
public class CadastrarConsultaServlet extends HttpServlet {

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
        CadastrarConsultaForm form = new CadastrarConsultaForm();
        try {
            BeanUtils.populate(form, request.getParameterMap());
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CadastrarConsultaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CadastrarConsultaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConsultaDAO cdao = new ConsultaDAO(dataSource);
        MedicoDAO mdao = new MedicoDAO(dataSource);
        
        Consulta c = new Consulta();
        Medico m = new Medico();
        Paciente p = new Paciente();
        
        String mensagem;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataExame = null;
        try{
            System.out.println("ENTROU NO CATCH");
            System.out.println(form.getDataExame());
            dataExame = sdf.parse(form.getDataExame());
        }catch(ParseException e){
            System.out.println("ENTROU NO CATCH");
            request.setAttribute("mensagem", e.getLocalizedMessage());
            request.getRequestDispatcher("erro.jsp").forward(request,response);
        }
        try{
            String crm = form.getCrm().replaceAll("[\\-s.]", "");
            String cpf = (String) request.getSession().getAttribute("cpf");
            cpf = cpf.replaceAll("[\\-s.]", "");
            if (!cdao.checaConsultaCpfCrmData( cpf, crm, form.getDataExame())){
                
            System.out.println("primeiro if");
                if(!mdao.buscarMedico(crm).equals(null)){
                    System.out.println("segundo if");
                    c.setMedico(mdao.buscarMedico(crm));
                    p.setCpf(cpf);
                    c.setPaciente(p);
                    c.setDataExame(dataExame);
                    cdao.gravarConsulta(c);
                    mensagem = "Consulta cadastrada com sucesso.";
                }
                else{
                    mensagem = "Não existe médico com o CRM digitado.";
                }

                request.setAttribute("mensagem", mensagem);
                request.getRequestDispatcher("cadastrarConsulta.jsp").forward(request, response);


            }
            else{ 
                mensagem = "Médico ou paciente já possuem consultas para esta data.";
                request.setAttribute("mensagem", mensagem);
                request.getRequestDispatcher("cadastrarConsulta.jsp").forward(request, response);
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
