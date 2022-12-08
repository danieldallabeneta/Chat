package Model;

/**
 *
 * @author Daniel Valdo Dallabeneta
 */
public class User {

    private int codigo;
    private String senha;
    private String userName;
    private String status;
    private int porta;
    private String ip;
    
    public User() {}

    public User(int codigo, String userName,String status, String ip, int porta) {
        this.codigo = codigo;
        this.userName = userName;
        this.status = status;
        this.ip = ip;
        this.porta = porta;
    }

    public User(int codigo,String userName, String senha, String status) {
        this.codigo = codigo;
        this.userName = userName;
        this.senha = senha;
        this.status = status;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public boolean isOnline(){
        return getStatus().equals("ONLINE");
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    
}
