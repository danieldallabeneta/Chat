package Controller;

import Interface.ObserverCadastro;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danie
 */
public class ControllerManutencaoCadastro {

    private List<ObserverCadastro> obsc = new ArrayList<>();
    private Socket socket;
    private ServerUtils server;

    public ControllerManutencaoCadastro() {
        server = ServerUtils.getInstance();
    }

    public void anexar(ObserverCadastro obs) {
        this.obsc.add(obs);
    }

    public void desanexar(ObserverCadastro obs) {
        this.obsc.remove(obs);
    }

    public ActionListener acaoCadastrar() {
        ActionListener acao = (ActionEvent e) -> {
            processaDadosCadastrar();
        };
        return acao;
    }

    private void processaDadosCadastrar() {
        Map<String, Object> dados = null;
        for (ObserverCadastro obs : this.obsc) {
            dados = obs.getDadosCadastro();
        }
        String[] validacao = validaCadastro(dados);
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ControllerManutencaoCadastro.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (validacao[1].equalsIgnoreCase("false")) {
            String[] dadosServer = processaDadosInsere(dados);
            if (dadosServer[1].equalsIgnoreCase("false")) {
                for (ObserverCadastro obs : this.obsc) {
                    obs.notificar("Não foi possível cadastrar o usuário.");
                }
            } else {
                for (ObserverCadastro obs : this.obsc) {
                    obs.notificar("Usuário cadastrado com sucesso!!");
                    obs.fecharTela();
                }
            }
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ControllerManutencaoCadastro.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            for (ObserverCadastro obs : this.obsc) {
                obs.notificar("Usuário informado já cadastrado");
            }
        }
    }

    public String[] validaCadastro(Map<String, Object> dadosTela) {
        String userName = (String) dadosTela.get("login");
        String[] dados = null;
        String texto = "GET;USER;" + userName;
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            dados = retorno.split(";");
            return dados;
        } catch (IOException ex) {
            System.out.println("Conexão não efetuada: " + ex.getMessage());
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

    public String[] processaDadosInsere(Map<String, Object> dadosTela) {
        String userName = (String) dadosTela.get("login");
        char[] senha = (char[]) dadosTela.get("senha");
        String[] dados = null;
        String texto = "INSERT;USER;" + userName + ";" + senha;
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            dados = retorno.split(";");
            return dados;
        } catch (IOException ex) {
            for (ObserverCadastro obs : this.obsc) {
                obs.notificar("Conexão não efetuada");
            }
        }
        return dados;
    }

}
