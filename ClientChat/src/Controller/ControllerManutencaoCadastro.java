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
        Boolean sucesso = false;
        validaCadastro(dados);
        
        sucesso = processaDadosInsere(dados);
        if(sucesso){
            for (ObserverCadastro obs : this.obsc) {
                obs.fecharTela();
            }
        }
    }

    private boolean validaCadastro(Map<String, Object> dadosTela) {
        String userName = (String)dadosTela.get("login");
        String texto = "GET;USER;"+userName;
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            String[] dados = retorno.split(";");
            if(dados[0].equals("false")){
                socket.close();
                return true;
            } else {
                socket.close();
                for (ObserverCadastro obs : this.obsc) {
                    obs.notificar("Usuário informado já cadastrado");
                }
                return false;
            }

        } catch (IOException ex) {
            System.out.println("Conexão não efetuada: " + ex.getMessage());
        }
        return false;
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

    private boolean processaDadosInsere(Map<String, Object> dadosTela) {
        String userName = (String)dadosTela.get("login");
        char[] senha = (char[])dadosTela.get("senha");
        
        String texto = "INSERT;USER;"+userName+";"+senha;
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            String[] dados = retorno.split(";");
            if(dados[0].equals("false")){
                socket.close();
                return false;
            } else {
                socket.close();
                for (ObserverCadastro obs : this.obsc) {
                    obs.notificar("Usuário cadastrado com sucesso!!");
                }
                return true;
            }

        } catch (IOException ex) {
            for (ObserverCadastro obs : this.obsc) {
                obs.notificar("Conexão não efetuada");
            }
        }
        return false;   
    }
    
}
