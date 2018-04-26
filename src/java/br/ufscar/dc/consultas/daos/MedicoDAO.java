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
    
    private final static String CRIAR_MEDICO_SQL = "INSERT INTO Medicos"
        + " (crm, especialidade, usuarioid)"
        + " VALUES (?, ?, ?)";

    private final static String LISTAR_MEDICOS_SQL = "SELECT"
       + " m.id AS medicoId, m.crm, m.especialidade,"
       + " u.id AS usuarioId, u.nome, u.nomeLogin"
       + " FROM Medicos m INNER JOIN Usuarios u ON m.usuarioid = u.id";

    private final static String BUSCAR_MEDICO_SQL = "SELECT"
        + " m.id AS medicoId, m.crm, m.especialidade,"
        + " u.id AS usuarioId, u.nome, u.nomeLogin"
        + " FROM Medicos m INNER JOIN Usuarios u ON m.usuarioid = u.id"
        + " WHERE crm=?";

    private final static String BUSCAR_MEDICO_ESPECIALIDADE_SQL = "SELECT"
        + " m.id AS medicoId, m.crm, m.especialidade,"
        + " u.id AS usuarioId, u.nome, u.nomeLogin"
        + " FROM Medicos m INNER JOIN Usuarios u ON m.usuarioid = u.id"
        + " WHERE especialidade=?";

    DataSource dataSource;


    public MedicoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public Medico gravarMedico(Medico m) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(CRIAR_MEDICO_SQL, Statement.RETURN_GENERATED_KEYS);) {
            
            UsuarioDAO udao = new UsuarioDAO(dataSource);
            Usuario u = udao.gravarUsuario(m.getUsuario());
            ps.setString(1, m.getCrm());
            ps.setString(2, m.getEspecialidade());
            ps.setInt(3, u.getId());
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
    public Medico buscarMedico(String crm) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(BUSCAR_MEDICO_SQL)) {

            ps.setString(1, crm);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    Usuario u = new Usuario();
                    Medico m = new Medico();
                    u.setNome(rs.getString("nome"));
                    u.setNomeLogin(rs.getString("nomeLogin"));
                    m.setId(rs.getInt("medicoId"));
                    m.setCrm(rs.getString("crm"));
                    m.setEspecialidade(rs.getString("especialidade"));
                    m.setUsuario(u);
                    return m;
                }
                else{
                    return null;
                }
            }
            catch(Exception e){
                System.out.println(e);
                return null;
            }
        }
        catch (Exception e){
            System.out.println(e);
            return null;
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
