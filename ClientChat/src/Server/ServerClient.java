package Server;

import Interface.ObserverMessage;
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
public class ServerClient extends Thread {

    private static List<ObserverMessage> obsc = new ArrayList<>();
    private ServerSocket ss;
    private Socket socket;
    private static int porta;
    private String ip;

    public ServerClient(String ip, int porta, ObserverMessage tela) {
        this.ip = ip;
        this.porta = porta;
        this.obsc.add(tela);
    }

    @Override
    public void run() {
        try {
            int portaServer = porta + 1;
            ss = new ServerSocket(portaServer);
            ss.setReuseAddress(true);
            System.out.println("Servidor iniciado na porta " + portaServer);
            // Servidor fica ouvindo
            while (true && !ss.isClosed()) {
                Socket s = ss.accept();
                System.out.println("Servidor recebeu info");
                tratar(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tratar(final Socket socket) {
        (new Thread() {
            @Override
            public void run() {
                BufferedReader in = null;
                try {
                    // Lê a mensagem
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = in.readLine();
                    // Quebra a mensagem
                    String dados[] = line.split(";");
                    if (dados[0].equals("MESSAGE")) {
                        String texto = "";
                        for (int i = 1; i < (dados.length); i++) {
                            String[] sDados = dados[i].split("-");
                            texto = texto + sDados[0] + "\n      " + sDados[1] + "\n";
                        }
                        for (ObserverMessage obs : obsc) {
                            obs.setMessage(texto);
                        }
                    } else if (dados[0].equals("USER")) {
                        String texto = "";
                        for (int i = 1; i < dados.length; i++) {
                            String[] sDados = dados[i].split("-");
                            texto = texto + sDados[0] + " - " + sDados[1] + "\n";
                        }
                        for (ObserverMessage obs : obsc) {
                            obs.setUser(texto);
                        }
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

    public void stopServer() {
        try {
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
