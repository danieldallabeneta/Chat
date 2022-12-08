package Model;

/**
 * 
 * @author Daniel Valdo Dallabeneta
 */
public class ModelMessage {

    private int codigo;
    private User user;
    private String content;

    public ModelMessage(int codigo, User user, String content) {
        this.codigo = codigo;
        this.user = user;
        this.content = content;
    }

    public ModelMessage(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
