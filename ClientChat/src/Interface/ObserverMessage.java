package Interface;

import java.util.Map;

/**
 *
 * @author danie
 */
public interface ObserverMessage {
    
     public void fecharTela();

    public Map<String, Object> getDadosMessage();

    public void notificar(String mensagem);
    public void setMessage(String texto);
    public void setUser(String texto);
    public void setCampoSend();
    public void stopServer();
    
}
