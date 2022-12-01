package Interface;

import java.util.Map;

/**
 *
 * @author danie
 */
public interface ObserverCadastro {
    
    public void fecharTela();

    public Map<String, Object> getDadosCadastro();

    public void notificar(String mensagem);
}
