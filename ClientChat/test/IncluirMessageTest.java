
import Controller.ControllerManutencaoMessage;
import Utils.ServerUtils;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;

/**
 *
 * @author danie
 */
public class IncluirMessageTest extends TestCase {

    private String ip = "192.168.43.6";
    private int porta = 80;
    private static ServerUtils utils;
    private static ControllerManutencaoMessage cnt;

    /**
     * Insere uma nova mensagem no banco de dados;
     */
    public void testInsereMensagemBanco() {
        utils = ServerUtils.getInstance();
        utils.setIp(ip);
        utils.setPorta(porta);
        cnt = new ControllerManutencaoMessage(4);
        String message = "Inserir mensagem teste";
        String[] retorno = cnt.atualizaMessageServer(message);
        String texto = "";
        for (int i = 1; i < retorno.length; i++) {
            String[] sDados = retorno[i].split("-");
            texto = texto + sDados[0] + "\n      " + sDados[1] + "\n";
        }
        if(texto.equals("")){
            assertEquals(retorno[0], "false");
        } else {
            assertEquals(retorno[0], "MESSAGE");
        }
        System.out.println(texto);
    }
    
    /**
     * Não insere uma nova mensagem no banco de dados;
     */
    public void testNaoInsereMensagemBanco() {
        utils = ServerUtils.getInstance();
        utils.setIp(ip);
        utils.setPorta(porta);
        cnt = new ControllerManutencaoMessage(40);
        String message = "Não deve inserir mensagem teste";
        
        String[] retorno = cnt.atualizaMessageServer(message);
        assertEquals(retorno[0], "false");
    }
    
    /**
     * Não insere uma nova mensagem no banco de dados;
     */
    public void testListMensagemBanco() {
        utils = ServerUtils.getInstance();
        utils.setIp(ip);
        utils.setPorta(porta);
        cnt = new ControllerManutencaoMessage(40);
        
        String[] retorno = cnt.atualizaMessageServer("");
        String texto = "";
        for (int i = 0; i < retorno.length; i++) {
            String[] sDados = retorno[i].split("-");
            texto = texto + sDados[0] + "\n      " + sDados[1] + "\n";
        }
        System.out.println(texto);
        if(texto.equals("")){
            assertEquals(retorno[0], "false");
        } else {
            assertNotNull(retorno[0]);
        }
    }
    
    /**
     * Não insere uma nova mensagem no banco de dados;
     */
    public void testListUserBanco() {
        utils = ServerUtils.getInstance();
        utils.setIp(ip);
        utils.setPorta(porta);
        cnt = new ControllerManutencaoMessage(40);
        
        String[] retorno = cnt.carregaCampoUser();
        String texto = "";
        for (int i = 0; i < retorno.length; i++) {
            String[] sDados = retorno[i].split("-");
            texto = texto + sDados[0] + "\n      " + sDados[1] + "\n";
        }
        if(texto.equals("")){
            assertEquals(retorno[0], "");
        } else {
            assertNotNull(retorno[0]);
        }
        
        System.out.println(texto);
    }

}
