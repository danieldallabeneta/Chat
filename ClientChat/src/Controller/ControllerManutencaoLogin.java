package controller;

import Interface.ObserverLogin;
import Model.User;
import Utils.ServerUtils;
import View.Cadastro;
import View.Message;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danie
 */
public class ControllerManutencaoLogin {

    private List<ObserverLogin> obsc = new ArrayList<>();
    private int user;
    private Socket socket;
    private ServerUtils server;

    public ControllerManutencaoLogin() {
        server = ServerUtils.getInstance();
    }

    public void anexar(ObserverLogin obs) {
        this.obsc.add(obs);
    }

    public void desanexar(ObserverLogin obs) {
        this.obsc.remove(obs);
    }

    public MouseListener acaoCadastrar() {
        MouseListener acao = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                Cadastro novaTela = new Cadastro();
                novaTela.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        };
        return acao;
    }

    public ActionListener acaoConfirmar() {
        ActionListener acao = (ActionEvent e) -> {
            processaDadosInsere();
        };
        return acao;
    }

    public ActionListener acaoCancelar() {
        ActionListener acao = (ActionEvent e) -> {
            this.fecharTela();
        };
        return acao;
    }

    private void processaDadosInsere() {
        Map<String, Object> dados = null;
        for (ObserverLogin obs : this.obsc) {
            dados = obs.getDadosLogin();
        }
        Boolean sucesso = false;

        sucesso = validaCadastro(dados);

        if (sucesso) {
            alteraStatus();
            Message tela = new Message(user);
            tela.setVisible(true);
            for (ObserverLogin obs : this.obsc) {
                obs.fecharTela();
            }
        } else {
            for (ObserverLogin obs : this.obsc) {
                obs.notificar("Usuário e Senha não conferem!!!");
            }
        }
    }

    private void fecharTela() {
        for (ObserverLogin obs : this.obsc) {
            obs.fecharTela();
        }
    }

    private boolean validaCadastro(Map<String, Object> dadosTela) {
        String userName = (String) dadosTela.get("login");

        String texto = "GET;USER;" + userName;
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            String[] dados = retorno.split(";");
            if (dados[0].equals("false")) {
                socket.close();
                return false;
            } else {
                socket.close();
                User user = new User();
                user.setUserName(dados[1]);
                user.setSenha(dados[2]);
                this.user = Integer.parseInt(dados[0]);
                return user.verificaLogin(user);
            }

        } catch (IOException ex) {
            for (ObserverLogin obs : this.obsc) {
                obs.notificar("Conexão não efetuada");
            }
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

    private boolean alteraStatus() {
        String ipLocal = getIpLocal();

        String texto = "ALTER;STATUS;" + user + ";ONLINE;" + ipLocal + ";" + server.getPorta();
        try {
            socket = new Socket(server.getIp(), server.getPorta());
            enviaDados(texto);
            String retorno = retornoServer();
            String[] dados = retorno.split(";");
            if (dados[0].equals("true")) {
                socket.close();
                return true;
            } else {
                socket.close();
                for (ObserverLogin obs : this.obsc) {
                    obs.notificar("Conexão não efetuada");
                }
                return false;
            }

        } catch (IOException ex) {
            for (ObserverLogin obs : this.obsc) {
                obs.notificar("Conexão não efetuada");
            }
        }
        return false;
    }

    private String getIpLocal() {
        String ipHost = "";
        try {
            ipHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ControllerManutencaoLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ipHost;
    }

}
