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
    private String porta;
    private String ip;

    public User() {
    }

    public User(int codigo, String userName, String status) {
        this.codigo = codigo;
        this.userName = userName;
        this.status = status;
    }

    public User(String userName, String senha, String status) {
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

    public boolean isOnline() {
        return getStatus().equals("online");
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private boolean isSenhaOk(String password) {
        int pass = password.length();
        int senha = this.senha.length();
        if (pass != senha) {
            return false; // se for diferente, retorna falso
        } else {
            for (int i = 0; i < password.length(); i++) {
                // verifica cada char em pass
                if (this.senha.charAt(i) != password.charAt(i)) {
                    return false; // se for diferente, retorna falso
                }
            }
        }
        return true;
    }

    private boolean isLoginOk(String login) {
        return login.equals(this.getUserName());
    }

    public boolean verificaLogin(User user) {
        return isSenhaOk(user.getSenha()) && isLoginOk(user.getUserName());
    }
}
