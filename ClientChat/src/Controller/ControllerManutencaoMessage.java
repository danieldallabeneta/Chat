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

    public ControllerManutencaoMessage(int user) {
        this.user = user;
        this.server = ServerUtils.getInstance();
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
        String message = (String) dadosTela.get("msg");
        String texto = "INSERT;MESSAGE;" + user + ";" + message;
        carregaCampoMessage(texto);
    }

    public void carregaCampoMessage(String texto) {
        if (texto.equals("")) {
            texto = "LIST;MESSAGE";
        }
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            String[] dados = retorno.split(";");
            if (dados[0].equals("false")) {
                socket.close();
                for (ObserverMessage obs : this.obsc) {
                    obs.notificar("Conexão não efetuada");
                }
            } else {
                socket.close();
                atualizaMensagem(dados);
            }

        } catch (IOException ex) {
            for (ObserverMessage obs : this.obsc) {
                obs.notificar("Conexão não efetuada");
            }
        }
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
        if (dados.length > 0 && !dados[0].equals("")){
            for (int i = 0; i < dados.length; i++) {
                String[] sDados = dados[i].split("-");
                texto = texto + sDados[0] + "\n      " + sDados[1] + "\n";
            }
            for (ObserverMessage obs : this.obsc) {
                obs.setMessage(texto);
                obs.setCampoSend();
            }
        }
    }

    public void carregaCampoUser() {
        String texto = "LIST;USER";
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            String[] dados = retorno.split(";");
            if (dados[0].equals("false")) {
                socket.close();
                for (ObserverMessage obs : this.obsc) {
                    obs.notificar("Conexão não efetuada");
                }
            } else {
                socket.close();
                atualizaUser(dados);
            }

        } catch (IOException ex) {
            for (ObserverMessage obs : this.obsc) {
                obs.notificar("Conexão não efetuada");
            }
        }
    }

    public void atualizaUser(String[] dados) {
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
        if (alteraSituacaoUser()) {
            for (ObserverMessage obs : this.obsc) {
//                obs.stopServer();
                obs.fecharTela();
            }
        } else {
            for (ObserverMessage obs : this.obsc) {
                obs.notificar("Conexão não efetuada");
            }
        }
    }

    public boolean alteraSituacaoUser() {
        String texto = "ALTER;STATUS;" + user + ";OFFLINE;" + "null" + ";" + 0;
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            String[] dados = retorno.split(";");
            if (dados[0].equals("false")) {
                socket.close();
                for (ObserverMessage obs : this.obsc) {
                    obs.notificar("Conexão não efetuada");
                }
                return false;
            } else {
                socket.close();
                return true;
            }
        } catch (IOException ex) {
            return false;
        }
    }

}
