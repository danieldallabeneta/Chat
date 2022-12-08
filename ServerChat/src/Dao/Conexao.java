package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Daniel Valdo Dallabeneta
 */
public class Conexao {
    
    private static Connection connection = null;
    
    static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "*Pi1202*";
    
    
    public static Connection conectar() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexão realizada!");    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
    
    public static void desconectar(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
