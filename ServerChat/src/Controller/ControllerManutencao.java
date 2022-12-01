package Controller;

import Dao.MessageDao;
import Dao.UserDao;
import Model.ModelMessage;
import Model.User;
import Utils.EnumUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author danie
 */
public class ControllerManutencao {

    private UserDao dadosUser;
    private MessageDao dadosMensagem;
    private Socket socket;

    public ControllerManutencao() {
        dadosUser = UserDao.getInstance();
        dadosMensagem = MessageDao.getInstance();
    }

    public String processaDadosRecebidos(String dadosProcess) {
        String[] dados = dadosProcess.split(";");
        switch (dados[0]) {
            case "INSERT":
                if (dados[1].equals("USER")) {//ok
                    return insereUsuario(dados);
                }
                if (dados[1].equals("MESSAGE")) {
                    return insereMensagem(dados);
                }
            case "ALTER":
                if (dados[1].equals("STATUS")) {
                    return alteraUser(dados);
                }
            case "LIST":
                if (dados[1].equals("USER")) {
                    return listUser();
                }
                if (dados[1].equals("MESSAGE")) {
                    return listMessage();
                }
            case "GET":
                if (dados[1].equals("USER")) {//ok
                    return buscaUser(dados);
                }
        }
        return "Ação não encontrada";
    }

    private String insereUsuario(String[] dados) {
        String nome = dados[2];
        String senha = dados[3];
        String status = "OFFLINE";

        User newUser = new User(0, nome, senha, status);
        if (dadosUser.insert(newUser)) {
            return "true";
        } else {
            return "false";
        }
    }

    private String insereMensagem(String[] dados) {
        int codigo = Integer.parseInt(dados[2]);
        User user = dadosUser.get(codigo);
        String mensagem = dados[3];

        ModelMessage novaMensagem = new ModelMessage(user, mensagem);
        if (dadosMensagem.insert(novaMensagem)) {
            String texto = listMessage();
//            comunicarHost(EnumUtils.MESSAGE);
            return texto;
        } else {
            return "false";
        }
    }

    private String listMessage() {
        String lista = "";
        int cont = dadosMensagem.getAll().size();
        for (int i = 0; i < dadosMensagem.getAll().size(); i++) {
            ModelMessage model = (ModelMessage) dadosMensagem.getAll().get(cont - 1);
            lista = lista + model.getUser().getUserName() + "-" + model.getContent() + ";";
            cont--;
        }
        return lista;
    }

    private String listUser() {
        String lista = "";
        for (Object user : dadosUser.getAll()) {
            User model = (User) user;
            lista = lista + model.getUserName() + "-" + model.getStatus() + ";";
        }
        return lista;
    }

    private String buscaUser(String[] dados) {
        String nome = dados[2];
        User usuario = dadosUser.get(nome);
        String texto = "false";
        if (usuario != null) {
            texto = usuario.getCodigo() + ";" + usuario.getUserName() + ";" + usuario.getSenha() + ";" + usuario.getStatus();
        }
        return texto;
    }

    private String alteraUser(String[] dados) {
        int codigo = Integer.parseInt(dados[2]);
        String status = dados[3];
        String ip = dados[4];
        int porta = Integer.parseInt(dados[5]);
        if (dadosUser.updateStatus(codigo, status, ip, porta)) {
//            comunicarHost(EnumUtils.USER);
            return "true";
        } else {
            return "false";
        }
    }

    private void comunicarHost(int tipo) {
        List<Object> users = dadosUser.getAll();
        String texto = "";
        if (tipo == EnumUtils.USER) {
            texto = EnumUtils._USER + ";" + listUser();
        } else {
            texto = EnumUtils._MESSAGE + ";" + listMessage();
        }
        for (Object user : users) {
            User client = (User) user;
            if (client.isOnline()) {
                comunicaClient(client, texto);
            }
        }
    }

    private void comunicaClient(User user, String texto) {
        try {
            socket = new Socket(user.getIp(), user.getPorta());
            enviaDados(texto);
            socket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void enviaDados(String texto) throws IOException {
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        pr.println(texto);
        pr.flush();
    }
    
    
}
