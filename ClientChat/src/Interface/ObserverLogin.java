package Interface;

import java.util.Map;

/**
 *
 * @author danie
 */
public interface ObserverLogin {
    
    public void fecharTela();

    public Map<String, Object> getDadosLogin();

    public void notificar(String mensagem);
    
}
