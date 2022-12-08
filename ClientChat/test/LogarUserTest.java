
import Controller.ControllerManutencaoMessage;
import Utils.ServerUtils;
import controller.ControllerManutencaoLogin;
import java.net.Socket;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;

/**
 *
 * @author danie
 */
public class LogarUserTest extends TestCase {

    private Socket socket;
    private String ip = "192.168.43.6";
    private int porta = 80;
    private static ServerUtils utils;
    private static ControllerManutencaoLogin cnt;
    private static ControllerManutencaoMessage cntMessage;

    /**
     * Altera situação do usuário corretamente;
     */
    public void testAlteraSituacaoUserCorreto() {
        utils = ServerUtils.getInstance();
        utils.setIp(ip);
        utils.setPorta(porta);
        cnt = new ControllerManutencaoLogin();
        cnt.setUser(1);
        String[] retorno = cnt.alteraStatus();
        assertEquals(retorno[0], "true");
    }

    /**
     * Não Altera situação do usuário corretamente;
     */
    public void testNaoAlteraSituacaoUserCorreto() {
        utils = ServerUtils.getInstance();
        utils.setIp(ip);
        utils.setPorta(porta);
        cntMessage = new ControllerManutencaoMessage(4);
        String[] retorno = cntMessage.alteraSituacaoUser();
        assertEquals(retorno[0], "false");
    }
    
}
