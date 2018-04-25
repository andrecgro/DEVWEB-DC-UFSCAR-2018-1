/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.consultas.daos;

import br.ufscar.dc.consultas.beans.Consulta;
import br.ufscar.dc.consultas.beans.Medico;
import br.ufscar.dc.consultas.beans.Paciente;
import br.ufscar.dc.consultas.beans.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author andrerocha
 */
public class ConsultaDAO {
    
    private final static String CRIAR_CONSULTA_SQL = "INSERT INTO Consultas"
            + " (crmmedico, cpfpaciente, dataexame)"
            + " VALUES (?, ?, ?)";

    private final static String LISTAR_CONSULTAS_PACIENTE_SQL = "SELECT"
        + " c.id AS consultaId, c.dataexame,"
        + " m.crm, u.nome AS medicoNome, m.especialidade, p.cpf"
        + " FROM Consultas c INNER JOIN Medicos m ON c.crmmedico = m.crm"
        + " INNER JOIN Pacientes p on c.cpfpaciente = p.cpf"
        + " INNER JOIN Usuarios u on u.id = m.usuarioid"
        + " WHERE cpfpaciente=?";
    
    private final static String LISTAR_CONSULTAS_MEDICO_SQL = "SELECT"
        + " c.id AS consultaId, c.dataexame,"
        + " p.cpf, u.nome, p.telefone, p.sexo, p.datadenascimento"
        + " FROM Consultas c INNER JOIN Pacientes p ON c.cpfpaciente = p.cpf"
        + " INNER JOIN Medicos m on c.crmmedico = m.crm"
        + " INNER JOIN Usuarios u on u.id = p.usuarioid"
        + " WHERE crmmedico=?";
    
    DataSource dataSource;


    public ConsultaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public Consulta gravarConsulta(Consulta c) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
            
            PreparedStatement ps = con.prepareStatement(CRIAR_CONSULTA_SQL, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, c.getMedico().getCrm());
            ps.setString(2, c.getPaciente().getCpf());
            ps.setDate(3,  new java.sql.Date(c.getDataExame().getTime()));
            ps.execute();


            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                c.setId(rs.getInt(1));
            }
        }
        return c;
    }

    public List<Consulta> listarTodasConsultasPorPaciente(String cpf) throws SQLException, NamingException {
        List<Consulta> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTAR_CONSULTAS_PACIENTE_SQL)) {
            
                ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta c = new Consulta();
                    Medico m = new Medico();
                    Usuario u = new Usuario();
                    c.setId(rs.getInt("consultaId"));
                    c.setDataExame(new Date(rs.getDate("dataExame").getTime()));                    
                    m.setCrm(rs.getString("crm"));
                    u.setNome(rs.getString("medicoNome"));
                    m.setEspecialidade(rs.getString("especialidade"));
                    m.setUsuario(u);
                    c.setMedico(m);
                    ret.add(c);
                }
            }
        }
        return ret;
    }
    
    public List<Consulta> listarTodasConsultasPorMedico(String crm) throws SQLException, NamingException {
        List<Consulta> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTAR_CONSULTAS_MEDICO_SQL)) {

                ps.setString(1, crm);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta c = new Consulta();
                    Paciente p = new Paciente();
                    Usuario u = new Usuario();
                    c.setId(rs.getInt("consultaId"));
                    c.setDataExame(new Date(rs.getDate("dataExame").getTime()));
                    u.setNome(rs.getString("nome"));
                    p.setCpf(rs.getString("cpf"));
                    p.setSexo(rs.getString("sexo"));
                    p.setTelefone(rs.getString("telefone"));
                    p.setUsuario(u);
                    c.setPaciente(p);
                    ret.add(c);
                }
            }
        }
        return ret;
    }
    
}
