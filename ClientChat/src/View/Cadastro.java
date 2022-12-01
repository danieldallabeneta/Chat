package View;

import Controller.ControllerManutencaoCadastro;
import Interface.ObserverCadastro;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author danie
 */
public class Cadastro extends javax.swing.JFrame implements ObserverCadastro{

    ControllerManutencaoCadastro cnt;
    
    public Cadastro() {
        cnt = new ControllerManutencaoCadastro();
        cnt.anexar(this);
        initComponents();
        addAcaoBotaoCadastro();
    }
    
    private void addAcaoBotaoCadastro() {
        btCadastro.addActionListener(cnt.acaoCadastrar());
    }
    
    @Override
    public Map<String, Object> getDadosCadastro() {
        Map<String, Object> dados = new HashMap<>();
        String login = tfLogn.getText();
        char[] senha = tfsenha.getPassword();
        dados.put("login", login);
        dados.put("senha", senha);
        
        return dados;
    }
    
    @Override
    public void fecharTela() {
        this.dispose();
    }

    @Override
    public void notificar(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.INFORMATION_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfLogn = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfsenha = new javax.swing.JPasswordField();
        btCadastro = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastre-se");

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Californian FB", 0, 36)); // NOI18N
        jLabel1.setText("Login ");

        tfLogn.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        tfLogn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfLogn.setToolTipText("Informe seu Login");

        jLabel2.setFont(new java.awt.Font("Californian FB", 0, 36)); // NOI18N
        jLabel2.setText("Senha ");

        tfsenha.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        tfsenha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfsenha.setToolTipText("Informe a sua Senha");

        btCadastro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btCadastro.setText("Cadastrar");
        btCadastro.setToolTipText("Click para cadastrar");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/chat1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tfsenha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfLogn, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(81, 81, 81)
                                    .addComponent(jLabel2))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(84, 84, 84)
                                    .addComponent(jLabel1)))
                            .addComponent(jLabel4)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(btCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel4)
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfLogn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfsenha, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCadastro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField tfLogn;
    private javax.swing.JPasswordField tfsenha;
    // End of variables declaration//GEN-END:variables

}
