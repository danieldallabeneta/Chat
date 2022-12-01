package Server;

import Interface.ObserverMessage;
import static Server.ServerClient.tratar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danie
 */
public class ServerClient {

    private static List<ObserverMessage> obsc = new ArrayList<>();

    private Socket socket;
    private static int porta;
    private String ip;
    private boolean run = true;
    public static ThreadServer server;

    public ServerClient(String ip, int porta, ObserverMessage tela) {
        this.ip = ip;
        this.porta = porta;
        this.obsc.add(tela);
        server();
    }

    public synchronized static void server() {
        server = new ThreadServer(porta);
        server.start();
    }

    public void stopServer() {
        server.stopServer();
    }

    public synchronized static void tratar(final Socket socket) {
        (new Thread() {
            @Override
            public void run() {
                BufferedReader in = null;
                try {
                    // Lê a mensagem
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = null;
                    line = in.readLine();
                    // Quebra a mensagem
                    String dados[] = line.split(";");
                    if (dados[0].equals("MESSAGE")) {
                        atualizaMensagem(dados);
                    } else if (dados[0].equals("USER")) {
                        atualizaUser(dados);
                    }
                    socket.close();

                } catch (IOException ex) {
                    Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

//    @Override
//    public void run() {
//        try {
//            refreshMensagem();
//            refreshUser();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//    private static void refreshMensagem(String[] dados) {
//        String texto = "LIST;MESSAGE";
//        try {
//            socket = new Socket(ip, porta);
//            enviaDados(texto);
//            String retorno = retornoServer();
//            String[] dados = retorno.split(";");
//            if (dados[0].equals("false")) {
//                socket.close();
//                for (ObserverMessage obs : obsc) {
//                    obs.notificar("Conexão não efetuada");
//                }
//            } else {
//                socket.close();
//                atualizaMensagem(dados);
//            }
//
//        } catch (IOException ex) {
//            for (ObserverMessage obs : obsc) {
//                obs.notificar("Conexão não efetuada");
//            }
//        }
//    }
//    private static void refreshMensagem() {
//        String texto = "LIST;MESSAGE";
//        try {
//            socket = new Socket(ip, porta);
//            enviaDados(texto);
//            String retorno = retornoServer();
//            String[] dados = retorno.split(";");
//            if (dados[0].equals("false")) {
//                socket.close();
//                for (ObserverMessage obs : obsc) {
//                    obs.notificar("Conexão não efetuada");
//                }
//            } else {
//                socket.close();
//                atualizaMensagem(dados);
//            }
//
//        } catch (IOException ex) {
//            for (ObserverMessage obs : obsc) {
//                obs.notificar("Conexão não efetuada");
//            }
//        }
//    }
//    private void enviaDados(String texto) throws IOException {
//        PrintWriter pr = new PrintWriter(socket.getOutputStream());
//        pr.println(texto);
//        pr.flush();
//    }
//
//    private String retornoServer() throws IOException {
//        InputStreamReader in = new InputStreamReader(socket.getInputStream());
//        BufferedReader bf = new BufferedReader(in);
//        String str = bf.readLine();
//        return str;
//    }
    private static void atualizaMensagem(String[] dados) {
        String texto = "";
        for (int i = 0; i < (dados.length); i++) {
            String[] sDados = dados[i].split("-");
            texto = texto + sDados[0] + "\n      " + sDados[1] + "\n";
        }
        for (ObserverMessage obs : obsc) {
            obs.setMessage(texto);
        }
    }

    private static void atualizaUser(String[] dados) {
        String texto = "";
        for (int i = 0; i < dados.length; i++) {
            String[] sDados = dados[i].split("-");
            texto = texto + sDados[0] + " - " + sDados[1] + "\n";
        }
        for (ObserverMessage obs : obsc) {
            obs.setUser(texto);
        }
    }

//    private void refreshUser() {
//        String texto = "LIST;USER";
//        try {
//            socket = new Socket(ip, porta);
//            enviaDados(texto);
//            String retorno = retornoServer();
//            String[] dados = retorno.split(";");
//            if (dados[0].equals("false")) {
//                socket.close();
//                for (ObserverMessage obs : obsc) {
//                    obs.notificar("Conexão não efetuada");
//                }
//            } else {
//                socket.close();
//                atualizaUser(dados);
//            }
//
//        } catch (IOException ex) {
//            for (ObserverMessage obs : obsc) {
//                obs.notificar("Conexão não efetuada");
//            }
//        }
//    }
}

class ThreadServer extends Thread {

    public ServerSocket ss;
    private int porta;

    public ThreadServer(int porta) {
        this.porta = porta;
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(porta + 1);
            // Servidor fica ouvindo
            while (true && !ss.isClosed()) {
                Socket s = ss.accept();

                // Coloca a thread para dormir para evitar concorrencia
                try {
                    int t = (int) (Math.random() * 2000);
                    Thread.sleep(t);
                } catch (Exception e) {
                }

                // Trata a requisição em outra thread enquanto o servidor volta a ouvir
                tratar(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        try {
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
