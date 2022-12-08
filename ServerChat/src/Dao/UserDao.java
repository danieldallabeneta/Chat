package Dao;

import Interface.InterfaceDao;
import Model.User;
import dao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Valdo Dallabeneta
 */
public class UserDao implements InterfaceDao {

    private UserDao() {};
    private static UserDao instance;
    private Connection connection;
    
    public synchronized static UserDao getInstance() {
        if (instance == null){
            instance            = new UserDao();
            instance.connection = Conexao.conectar();
            instance.createTable();
        }
        return instance;
    } 
        
    @Override
    public void createTable() {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS dsd.\"tbuser\""
                        +"(codigo SERIAL,"                
                        +" username  character varying(100) NOT NULL,"
                        + "senha character varying(100) NOT NULL,"
                        + "status character varying(10) NOT NULL,"
                        + "ip character varying(20),"
                        + "porta character varying(10),"
                        + "CONSTRAINT login_id PRIMARY KEY (codigo))";

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.execute(sqlCreate);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
    
    @Override
    public boolean insert(Object modelo) {
        User model = (User)modelo;
        String sql = "INSERT INTO dsd.\"tbuser\"(username, senha, status)\n" +
                     "VALUES (?,?,?)";
        PreparedStatement pstmt;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, model.getUserName());
            pstmt.setString(2, model.getSenha());
            pstmt.setString(3, model.getStatus());

            pstmt.execute();
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } 
    }
    
    @Override
    public boolean update(Object modelo) {
        User model = (User)modelo;
        
        String sql = "UPDATE dsd.\"tbuser\"\n"
                       +"SET senha = ?\n"
                     +"WHERE codigo = ?\n"
                       +"AND username = ?";
        PreparedStatement pstmt;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, model.getSenha());
            pstmt.setInt   (2, model.getCodigo());
            pstmt.setString(3, model.getUserName());

            pstmt.execute();
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean updateStatus(User user) {
        
        String sql = "UPDATE dsd.\"tbuser\"\n"
                       +"SET status = ?, ip = ?, porta = ?\n"
                     +"WHERE codigo = ?";
        PreparedStatement pstmt;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getStatus());
            pstmt.setString(2, user.getIp());
            pstmt.setInt   (3, user.getPorta());
            pstmt.setInt   (4, user.getCodigo());

            pstmt.execute();
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public User get(int codigo) {        
        User login = null;

        String sql = "SELECT *\n" +
                       "FROM dsd.\"tbuser\"\n" +
                      "WHERE codigo = ?";
        PreparedStatement pstmt;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, codigo);
            ResultSet resultado = pstmt.executeQuery();

            while (resultado.next()) {
                int cod      = resultado.getInt("codigo");
                String nome  = resultado.getString("username");
                String senha = resultado.getString("senha");
                String ip    = resultado.getString("ip");
                int porta    = resultado.getInt("porta");
                login = new User (cod, nome, senha, ip, porta);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } 
        return login;
    }
    
    public User get(String nome) {        
        User login = null;

        String sql = "SELECT *\n" +
                       "FROM dsd.\"tbuser\"\n" +
                      "WHERE username = ?";
        PreparedStatement pstmt;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nome);
            ResultSet resultado = pstmt.executeQuery();

            while (resultado.next()) {
                int cod       = resultado.getInt("codigo");
                String log    = resultado.getString("username");
                String senha  = resultado.getString("senha");
                String status = resultado.getString("status");
                login = new User (cod, log, senha, status);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } 
        return login;
    }
    
    @Override
    public boolean delete(Object modelo) {
        User model = (User)modelo;
        
        String sql = "DELETE "
                      +"FROM dsd.\"tbuser\"\n" 
                     +"WHERE codigo = ?\n"
                       +"AND username = ?";
        PreparedStatement pstmt;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt   (1, model.getCodigo());
            pstmt.setString(2, model.getUserName());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } 
    }
    
    public List<Object> getAll() {
        
        List<Object> login = new ArrayList<>();

        String sql = "SELECT * "
                     + "FROM dsd.tbuser\n"
                    + "ORDER BY username";
        
        Statement stmt;

        try {
            stmt = connection.createStatement();
            ResultSet resultado = stmt.executeQuery(sql);

            while (resultado.next()) {
                int cod       = resultado.getInt("codigo");
                String nome   = resultado.getString("username");
                String status = resultado.getString("status");
                String ip     = resultado.getString("ip");
                int porta     = resultado.getInt("porta");
                
                User log = new User(cod, nome, status, ip, porta);
                login.add(log);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } 
        return login;
    }
    
    public boolean existsUser(String nome, String senha){
        boolean exists = false;

        String sql = "SELECT exists ( SELECT 1\n" +
                       "FROM dsd.\"tbuser\"\n" +
                      "WHERE username = ?\n" +
                      "AND senha = ?)";
        PreparedStatement pstmt;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nome);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                exists = resultado.getBoolean("exists");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } 
        return exists;
    }

}
