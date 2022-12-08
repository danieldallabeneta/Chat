package Controller;

import Interface.ObserverMessage;
import Utils.ServerUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author danie
 */
public class ControllerManutencaoMessage {

    private List<ObserverMessage> obsc = new ArrayList<>();
    private Socket socket;
    private ServerUtils server;
    private int user;
    private boolean init;

    public ControllerManutencaoMessage(int user) {
        this.user = user;
        this.server = ServerUtils.getInstance();
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void anexar(ObserverMessage obs) {
        this.obsc.add(obs);
    }

    public void desanexar(ObserverMessage obs) {
        this.obsc.remove(obs);
    }

    public ActionListener acaoEnviar() {
        ActionListener acao = (ActionEvent e) -> {
            processaDadosEnviar();
        };
        return acao;
    }

    public void processaDadosEnviar() {
        Map<String, Object> dadosTela = null;
        for (ObserverMessage obs : this.obsc) {
            dadosTela = obs.getDadosMessage();
        }
        if (!dadosTela.get("msg").equals("")) {
            String message = (String) dadosTela.get("msg");
            String[] dadosServer = atualizaMessageServer(message);
            if (!dadosServer[0].equals("false")) {
                atualizaMensagem(dadosServer);
            }
        }
    }

    public String[] atualizaMessageServer(String message) {
        String texto = "";
        if (message.equals("")) {
            texto = "LIST;MESSAGE";
        } else {

            texto = "INSERT;MESSAGE;" + user + ";" + message;
        }
        String[] dados = null;
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            dados = retorno.split(";");
            socket.close();
            if (init) {
                atualizaMensagem(dados);
                init = false;
            }
        } catch (IOException ex) {
            for (ObserverMessage obs : this.obsc) {
                obs.notificar("Conexão não efetuada");
            }
        }
        return dados;
    }

    private void enviaDados(String texto) throws IOException {
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        pr.println(texto);
        pr.flush();
    }

    private String retornoServer() throws IOException {
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        String str = bf.readLine();
        return str;
    }

    public void atualizaMensagem(String[] dados) {
        String texto = "";
        int inicio = 0;
        if (dados[0].equalsIgnoreCase("MESSAGE")) {
            inicio = 1;
        }
        if (!dados[0].equalsIgnoreCase("false")) {
            for (int i = inicio; i < dados.length; i++) {
                String[] sDados = dados[i].split("-");
                texto = texto + sDados[0] + "\n      " + sDados[1] + "\n";
            }
            for (ObserverMessage obs : this.obsc) {
                obs.setMessage(texto);
                obs.setCampoSend();
            }
        }
    }

    public String[] carregaCampoUser() {
        String texto = "LIST;USER";
        String[] dados = null;
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            dados = retorno.split(";");
            socket.close();
        } catch (IOException ex) {
            for (ObserverMessage obs : this.obsc) {
                obs.notificar("Conexão não efetuada");
            }
        }
        return dados;
    }

    public void atualizaUser() {
        String[] dados = carregaCampoUser();
        String texto = "";
        for (int i = 0; i < dados.length; i++) {
            String[] sDados = dados[i].split("-");
            texto = texto + sDados[0] + " - " + sDados[1] + "\n";
        }
        for (ObserverMessage obs : this.obsc) {
            obs.setUser(texto);
        }
    }

    public ActionListener acaoSair() {
        ActionListener acao = (ActionEvent e) -> {
            processaDadosSair();
        };
        return acao;

    }

    private void processaDadosSair() {
        String[] dados = alteraSituacaoUser();
        if (dados[0].equals("false")) {
            for (ObserverMessage obs : this.obsc) {
                obs.notificar("Conexão não efetuada");
            }
        } else {
            for (ObserverMessage obs : this.obsc) {
                obs.stopServer();
                obs.fecharTela();
            }
        }
    }

    public String[] alteraSituacaoUser() {
        String texto = "ALTER;STATUS;" + user + ";OFFLINE;" + "null" + ";" + 0;
        String[] dados = null;
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            dados = retorno.split(";");
            socket.close();

        } catch (IOException ex) {
        }
        return dados;
    }

}
