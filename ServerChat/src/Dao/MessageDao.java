package Dao;

import Interface.InterfaceDao;
import Model.ModelMessage;
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
 * @author danie
 */
public class MessageDao implements InterfaceDao {

    private MessageDao() {
    }
    ;
    private static MessageDao instance;
    private Connection connection;

    public synchronized static MessageDao getInstance() {
        if (instance == null) {
            instance = new MessageDao();
            instance.connection = Conexao.conectar();
            instance.createTable();
        }
        return instance;
    }

    @Override
    public void createTable() {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS dsd.\"tbmessage\""
                + "(codigo SERIAL,"
                + " user_id integer NOT NULL,"
                + "content character varying(250) NOT NULL,"
                + "CONSTRAINT message_id PRIMARY KEY (codigo),"
                + "FOREIGN KEY (user_id) REFERENCES dsd.\"tbuser\" (codigo))";

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
        ModelMessage model = (ModelMessage) modelo;
        String sql = "INSERT INTO dsd.\"tbmessage\"(user_id, content)\n"
                + "VALUES (?,?)";
        PreparedStatement pstmt;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, model.getUser().getCodigo());
            pstmt.setString(2, model.getContent());

            pstmt.execute();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Object modelo) {
        return false;
    }

    @Override
    public ModelMessage get(int codigo) {
        ModelMessage message = null;
        return message;
    }

    public ModelMessage get(String nome) {
        ModelMessage message = null;
        return message;
    }

    @Override
    public boolean delete(Object modelo) {
        return false;
    }

    public List<Object> getAll() {

        List<Object> login = new ArrayList<>();

        String sql = "SELECT * "
                   + "FROM dsd.tbmessage\n"
                   + "ORDER BY codigo desc\n"
                   + "LIMIT 500\n";

        Statement stmt;

        try {
            stmt = connection.createStatement();
            ResultSet resultado = stmt.executeQuery(sql);

            while (resultado.next()) {
                int cod = resultado.getInt("codigo");
                int user_cod = resultado.getInt("user_id");
                User usuario = UserDao.getInstance().get(user_cod);
                String mensagem = resultado.getString("content");

                ModelMessage msg = new ModelMessage(cod, usuario, mensagem);
                login.add(msg);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return login;
    }
}
