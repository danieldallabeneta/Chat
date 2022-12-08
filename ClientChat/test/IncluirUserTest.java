import Controller.ControllerManutencaoCadastro;
import Utils.ServerUtils;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author danie
 */
public class IncluirUserTest extends TestCase {
    
    private String ip = "192.168.43.6";
    private int porta = 80;
    private static ServerUtils utils;
    private static ControllerManutencaoCadastro cnt;
    
    /**
     * Inclui um usuário no servidor corretamente;
     */
    public void testIncluiUserCorreto() {
        utils = ServerUtils.getInstance();
        utils.setIp(ip);
        utils.setPorta(porta);
        cnt = new ControllerManutencaoCadastro();       
        String[] retorno = getRetornoServer("daniel", "123");
        assertEquals(retorno[0], "INSERT");
        assertEquals(retorno[1], "TRUE");
    }
    
    public String[] getRetornoServer(String nome, String senha){        
        char[] s1 = senha.toCharArray();
        Map<String, Object> dados = new HashMap<>();
        dados.put("login", nome);
        dados.put("senha", s1);
        return cnt.processaDadosInsere(dados);
    }
        
    /**
     * Retorna que o usuário ainda não existe
     */
    public void testValidaUsuarioNaoExiste() {
        utils = ServerUtils.getInstance();
        utils.setIp(ip);
        utils.setPorta(porta);
        cnt = new ControllerManutencaoCadastro();
        Map<String, Object> dados = new HashMap<>();
        dados.put("login", "Usuario-Teste");
        
        String[] retorno = cnt.validaCadastro(dados);
        assertEquals(retorno[0], "GET");
        assertEquals(retorno[1], "FALSE");
    } 
    
    /**
     * Retorna que o usuário já existe no banco
     */
    public void testValidaUsuarioExiste() {
        utils = ServerUtils.getInstance();
        utils.setIp(ip);
        utils.setPorta(porta);
        cnt = new ControllerManutencaoCadastro();
        Map<String, Object> dados = new HashMap<>();
        dados.put("login", "daniel");
        
        String[] retorno = cnt.validaCadastro(dados);
        assertEquals(retorno[1], "daniel");
    } 
    
    
}
