/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.consultas.daos;

import br.ufscar.dc.consultas.beans.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author miguel
 */
public class UsuarioDAO {
    private final static String CRIAR_USUARIO_SQL = "insert into Usuarios"
            + " (nome, nomeLogin, senha, administrador, tipo)"
            + " values (?, ?, ?, ?, ?)";


    private final static String BUSCAR_USUARIO_SQL = "select"
            + " nome, nomeLogin, senha, administrador, tipo"
            + " from Usuarios"
            + " where nomeLogin=? AND senha= ?";
    
    DataSource dataSource;


    public UsuarioDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public Usuario gravarUsuario(Usuario u) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(CRIAR_USUARIO_SQL, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getNomeLogin());
            ps.setString(3, u.getSenha());
            ps.setBoolean(4, u.isAdmin());
            ps.setString(5, u.getTipo());
            ps.execute();


            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                u.setId(rs.getInt(1));
            }
        }
        return u;
    }


    public Usuario buscarUsuario(String login, String senha) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(BUSCAR_USUARIO_SQL)) {
            ps.setString(1, login);
            ps.setString(2, senha);


            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    Usuario u = new Usuario();
                    u.setNome(rs.getString("nome"));
                    u.setNomeLogin(rs.getString("nomeLogin"));
                    u.setSenha(rs.getString("senha"));
                    u.setAdmin(rs.getBoolean("administrador"));
                    u.setTipo(rs.getString("tipo"));
                    return u;
                }
                else{
                    return null;
                }
            }
        }
    }
}