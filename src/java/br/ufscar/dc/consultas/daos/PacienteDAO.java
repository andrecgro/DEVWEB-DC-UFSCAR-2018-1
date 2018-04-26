/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.consultas.daos;

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
public class PacienteDAO {

    private final static String CRIAR_PACIENTE_SQL = "INSERT INTO Pacientes"
            + " (cpf, datadenascimento, sexo, telefone, usuarioid)"
            + " VALUES (?, ?, ?, ?, ?)";
    
    private final static String LISTAR_PACIENTES_SQL = "SELECT"
        + " p.id AS pacienteID, p.cpf, p.dataDeNascimento, p.sexo, p.telefone,"
        + " u.id AS usuarioId, u.nome, u.nomeLogin, u.administrador"
        + " FROM Pacientes p INNER JOIN Usuarios u ON p.usuarioid = u.id";


    private final static String BUSCAR_PACIENTE_SQL = "SELECT"
        + " p.id AS pacienteID, p.cpf, p.dataDeNascimento, p.sexo, p.telefone,"
        + " u.id AS usuarioId, u.nome, u.nomeLogin, u.administrador"
        + " FROM Pacientes p INNER JOIN Usuarios u ON p.usuarioid = u.id"
        + " WHERE cpf=?";
    
    DataSource dataSource;


    public PacienteDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public Paciente gravarPaciente(Paciente p) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(CRIAR_PACIENTE_SQL, Statement.RETURN_GENERATED_KEYS);) {
            UsuarioDAO udao = new UsuarioDAO(dataSource);
            Usuario u = udao.gravarUsuario(p.getUsuario());
            ps.setString(1, p.getCpf());

            ps.setDate(2, new java.sql.Date(p.getDataDeNascimento().getTime()));

            ps.setString(3, p.getSexo());
            ps.setString(4, p.getTelefone());
            ps.setInt(5, u.getId());
            ps.execute();


            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                p.setId(rs.getInt(1));
            }
        }
        return p;
    }


    public List<Paciente> listarTodosPacientes() throws SQLException, NamingException {
        List<Paciente> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTAR_PACIENTES_SQL)) {


            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Paciente m = new Paciente();
                    Usuario u = new Usuario();
                    m.setId(rs.getInt("pacienteId"));
                    m.setCpf(rs.getString("cpf"));
                    m.setDataDeNascimento(rs.getDate("dataDeNascimento"));
                    m.setSexo(rs.getString("sexo"));
                    m.setTelefone(rs.getString("telefone"));
                    u.setId(rs.getInt("usuarioId"));
                    u.setNome(rs.getString("nome"));
                    u.setNomeLogin(rs.getString("nomeLogin"));
                    u.setSenha(rs.getString("senha"));
                    u.setAdmin(rs.getBoolean("admin"));
                    m.setUsuario(u);
                    ret.add(m);
                }
            }
        }
        return ret;
    }
    
    
    public Paciente buscarPaciente(String cpf) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(BUSCAR_PACIENTE_SQL)) {

            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    Usuario u = new Usuario();
                    Paciente p = new Paciente();
                    u.setNome(rs.getString("nome"));
                    u.setNomeLogin(rs.getString("nomeLogin"));
                    p.setId(rs.getInt("pacienteId"));
                    p.setCpf(rs.getString("cpf"));
                    p.setTelefone(rs.getString("telefone"));
                    p.setDataDeNascimento(rs.getDate("dataDeNascimento"));
                    p.setSexo(rs.getString("sexo"));
                    p.setUsuario(u);
                    return p;
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
    
}
