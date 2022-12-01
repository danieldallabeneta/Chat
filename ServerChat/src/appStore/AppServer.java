package appStore;

import Controller.ControllerManutencao;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author danie
 */
public class AppServer{

    private static ServerSocket server;
    private static Socket socket;
    private static PrintWriter pr;
    private static ControllerManutencao cnt;
        
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Informa a porta que serviço irá operar: ");
        int porta = sc.nextInt();

        if (porta > 0) {
            System.out.println("Servidor iniciado na porta " + porta);
            server = new ServerSocket(porta);
            server.setReuseAddress(true);
            cnt = new ControllerManutencao();
            while (true) {
                recebendoDados();
            }
        } else {
            System.out.println("Informe uma porta válida");
        }
    }
    
    private static void recebendoDados() throws IOException {
        socket = server.accept();
        String ipCliente = socket.getInetAddress().getHostAddress();
        System.out.println("Conectado com o Cliente IP " + ipCliente);
        
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        String msg = bf.readLine();

        String retorno = cnt.processaDadosRecebidos(msg);
        
        enviarDados(retorno);

    }

    public static void enviarDados(String msg) throws IOException {
        pr = new PrintWriter(socket.getOutputStream());
        pr.println(msg);
        pr.flush();
    }

}
