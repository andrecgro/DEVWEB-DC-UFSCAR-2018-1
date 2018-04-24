/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.consultas.daos;

import br.ufscar.dc.consultas.beans.Medico;
import br.ufscar.dc.consultas.beans.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author andrerocha
 */
public class MedicoDAO {
    
    private final static String CRIAR_MEDICO_SQL = "insert into Medicos"
        + " (crm, especialidade, usuario)"
        + " values (?, ?, ?)";

    private final static String LISTAR_MEDICOS_SQL = "select"
       + " m.id as medicoId, m.crm, m.especialidade,"
       + " u.id as usuarioId, u.nome, u.nomeLogin, u.administrador"
       + " from Medicos m inner join Usuarios u on m.usuarioid = u.id";

    private final static String BUSCAR_MEDICO_SQL = "select"
        + " m.id as medicoId, m.crm, m.especialidade,"
        + " u.id as usuarioId, u.nome, u.nomeLogin, u.administrador"
        + " from Medicos m inner join Usuarios u on m.usuarioid = u.id"
        + " where crm=?";

        private final static String BUSCAR_MEDICO_ESPECIALIDADE_SQL = "select"
        + " m.id as medicoId, m.crm, m.especialidade,"
        + " u.id as usuarioId, u.nome, u.nomeLogin, u.administrador"
        + " from Medicos m inner join Usuarios u on m.usuarioid = u.id"
        + " where especialidade=?";

    DataSource dataSource;


    public MedicoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public Medico gravarMedico(Medico m) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(CRIAR_MEDICO_SQL, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, m.getCrm());
            ps.setString(2, m.getEspecialidade());
            ps.setInt(3, m.getUsuario().getId());
            ps.execute();


            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                m.setId(rs.getInt(1));
            }
        }
        return m;
    }
    public List<Medico> listarTodosMedicos() throws SQLException, NamingException {
        List<Medico> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTAR_MEDICOS_SQL)) {


            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medico m = new Medico();
                    Usuario u = new Usuario();
                    m.setId(rs.getInt("medicoId"));
                    m.setCrm(rs.getString("crm"));
                    m.setEspecialidade(rs.getString("especialidade"));
                    u.setId(rs.getInt("usuarioId"));
                    u.setNome(rs.getString("nome"));
                    u.setNomeLogin(rs.getString("nomeLogin"));
                    m.setUsuario(u);
                    ret.add(m);
                }
            }
        }
        return ret;
    }
    public List<Medico> buscarMedico(String crm) throws SQLException {
        List<Medico> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(BUSCAR_MEDICO_SQL)) {


                ps.setString(1, crm);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Medico m = new Medico();
                        Usuario u = new Usuario();
                        m.setId(rs.getInt("medicoId"));
                        m.setCrm(rs.getString("crm"));
                        m.setEspecialidade(rs.getString("especialidade"));
                        u.setId(rs.getInt("usuarioId"));
                        u.setNome(rs.getString("nome"));
                        u.setNomeLogin(rs.getString("nomeLogin"));
                        m.setUsuario(u);
                        ret.add(m);
                }
        }
        return ret;
        }
    }
    
    public List<Medico> listarTodosMedicosPorEspecialidade(String especialidade) throws SQLException {
        List<Medico> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(BUSCAR_MEDICO_ESPECIALIDADE_SQL)) {


                ps.setString(1, especialidade);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Medico m = new Medico();
                        Usuario u = new Usuario();
                        m.setId(rs.getInt("medicoId"));
                        m.setCrm(rs.getString("crm"));
                        m.setEspecialidade(rs.getString("especialidade"));
                        u.setId(rs.getInt("usuarioId"));
                        u.setNome(rs.getString("nome"));
                        u.setNomeLogin(rs.getString("nomeLogin"));
                        m.setUsuario(u);
                        ret.add(m);
                }
        }
        return ret;
        }
    }
}
