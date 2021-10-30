/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import static Home.Login_Form.DATABASE_URL;
import static Home.Login_Form.JDBC_DRIVER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.util.Date;

/**
 *
 * @author ADS
 */
    public class CreateAccount extends javax.swing.JFrame {

    /**
     * Creates new form CreateAccount
     */
    String nom, prenom, cin,lieuNaiss, genre, situation, email, password, num, type;
    Date dateNaiss;
    int defaultPasswordLength = 10;
    
    public CreateAccount() {
       
    }
    
    public String name;
    public  CreateAccount(String name){
        
        initComponents();
        this.name = name;
        
    }
    
    boolean validation(){

        nom = txt_nom.getText();
        prenom = txt_prenom.getText();
        cin = txt_cin.getText();
        dateNaiss = txt_dateNaiss.getDate();
        lieuNaiss = txt_lieuNaiss.getText();
        genre = String.valueOf(cmb_genre.getSelectedItem());
        situation = String.valueOf(cmb_situation.getSelectedItem());
        email = txt_email.getText();
        password = txt_password.getText();
        num = txt_numero.getText();
        
        
        if(nom.equals("")){
            JOptionPane.showMessageDialog(this, "please enter first name");
            return false;
        }
        
        if(prenom.equals("")){
            JOptionPane.showMessageDialog(this, "please enter last name");
            return false;
        }
        
        if(cin.equals("")){
            JOptionPane.showMessageDialog(this, "please enter cin");
            return false;
        }
        
        if(dateNaiss == null){
            JOptionPane.showMessageDialog(this, "please enter a date");
            return false;
        }
        
        if(lieuNaiss.equals("")){
            JOptionPane.showMessageDialog(this, "please enter lieu de naissance");
            return false;
        }
        
        if(genre.equals("")){
            JOptionPane.showMessageDialog(this, "please enter gender");
            return false;
        }
        
        if(situation.equals("")){
            JOptionPane.showMessageDialog(this, "please enter situation");
            return false;
        }
         
        if(email.equals("")){
            JOptionPane.showMessageDialog(this, "please enter a email");
            return false;
        }
        if(password.equals("")){
            JOptionPane.showMessageDialog(this, "please enter code postal");
            return false;
        }
         if(num.equals("")){
            JOptionPane.showMessageDialog(this, "please enter your number");
            return false;
        }
        
        return true;
    }
    
 /*   
    public String randomPassword(int length){
        
        String passwordSet = "ABCDEFGHIJKLMNOPQRSTUVWYZ0123456789";
        char [] password = new char[length];
        
        for(int i=0; i< length ; i++){
            int rand = (int)(Math.random() * passwordSet.length());
            password[i] = passwordSet.charAt(rand);
        }
        
        return new String (password);
    }
 */   
    
    public void addAccountToDatabase(){
        
             try {
                Connection connection = null;
                Statement statement = null;
                ResultSet resultSet;
                ResultSetMetaData metaData;
                String resultat = "";

                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, "root", "");
                statement = connection.createStatement();

                String sql = "insert into agent(Nom, Prenom, CIN, Date_Naissance, Lieu_Naissance, Genre, Situation_sociale, Email, Password, Numero) VALUES (?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, txt_nom.getText());
                pst.setString(2, txt_prenom.getText());
                pst.setString(3, txt_cin.getText());
                
                java.sql.Date date_sql = new java.sql.Date(txt_dateNaiss.getDate().getTime());
                pst.setDate(4, date_sql);
                
                pst.setString(5, txt_lieuNaiss.getText());
                pst.setString(6, cmb_genre.getSelectedItem().toString());
                pst.setString(7, cmb_situation.getSelectedItem().toString());
                pst.setString(8, txt_email.getText());
                pst.setString(9, txt_password.getText());
                pst.setString(10, txt_numero.getText());

                pst.executeUpdate();
                
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
            
        }
    

   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        myGroup = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        JPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabell8 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        txt_nom = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_prenom = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_password = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_dateNaiss = new com.toedter.calendar.JDateChooser();
        cmb_situation = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cmb_genre = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txt_lieuNaiss = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_numero = new javax.swing.JFormattedTextField();
        txt_cin = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btn_back = new javax.swing.JButton();
        btn_soumettre = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 488, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(52, 73, 94));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(54, 70, 78));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("New registration !");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        JPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("Num de Tel");
        JPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 320, 130, 29));

        jLabell8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabell8.setText("Lieu de Naissance ");
        JPanel.add(jLabell8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 140, 29));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        JPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 10, 340));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("Nom      ");
        JPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 29));

        txt_nom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nomActionPerformed(evt);
            }
        });
        JPanel.add(txt_nom, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 170, 28));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Prénom ");
        JPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 70, 29));

        txt_prenom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_prenomActionPerformed(evt);
            }
        });
        JPanel.add(txt_prenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 170, 28));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setText("CIN");
        JPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 70, 29));

        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });
        JPanel.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 240, 170, 28));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setText("Date de Naissance ");
        JPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 140, 29));

        txt_dateNaiss.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txt_dateNaissAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        JPanel.add(txt_dateNaiss, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 170, 30));

        cmb_situation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Marié", "Célibataire  " }));
        cmb_situation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_situationActionPerformed(evt);
            }
        });
        JPanel.add(cmb_situation, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 170, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setText("Genre");
        JPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 90, 29));

        cmb_genre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Homme", "Femme" }));
        cmb_genre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_genreActionPerformed(evt);
            }
        });
        JPanel.add(cmb_genre, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, 170, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setText("Situation sociale");
        JPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, 130, 29));

        txt_lieuNaiss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_lieuNaissActionPerformed(evt);
            }
        });
        JPanel.add(txt_lieuNaiss, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 170, 28));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel11.setText("Email");
        JPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 170, 130, 29));

        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });
        JPanel.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, 170, 28));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setText("Mot de passe");
        JPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, 130, 29));

        txt_numero.setColumns(10);
        try {
            txt_numero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-##-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numeroActionPerformed(evt);
            }
        });
        JPanel.add(txt_numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 320, 170, 30));

        txt_cin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cinActionPerformed(evt);
            }
        });
        JPanel.add(txt_cin, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 170, 28));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_back.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_back.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\backto.png")); // NOI18N
        btn_back.setText("Back");
        btn_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_backMouseClicked(evt);
            }
        });
        jPanel3.add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 180, 50));

        btn_soumettre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_soumettre.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\email_send_50px.png")); // NOI18N
        btn_soumettre.setText("Add");
        btn_soumettre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_soumettreMouseClicked(evt);
            }
        });
        btn_soumettre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_soumettreActionPerformed(evt);
            }
        });
        jPanel3.add(btn_soumettre, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 180, 50));

        btn_cancel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_cancel.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\cancel_50px.png")); // NOI18N
        btn_cancel.setText("Cancel");
        jPanel3.add(btn_cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, 180, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(JPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(JPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_soumettreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_soumettreActionPerformed

    }//GEN-LAST:event_btn_soumettreActionPerformed

    private void btn_soumettreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_soumettreMouseClicked
        if(validation()){
            addAccountToDatabase();
        }
    }//GEN-LAST:event_btn_soumettreMouseClicked

    private void txt_nomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomActionPerformed
        txt_prenom.requestFocusInWindow();
    }//GEN-LAST:event_txt_nomActionPerformed

    private void txt_prenomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_prenomActionPerformed
        txt_cin.requestFocusInWindow();
    }//GEN-LAST:event_txt_prenomActionPerformed

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        txt_numero.requestFocusInWindow();
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void txt_lieuNaissActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_lieuNaissActionPerformed
        cmb_genre.requestFocusInWindow();
    }//GEN-LAST:event_txt_lieuNaissActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        txt_password.requestFocusInWindow();
    }//GEN-LAST:event_txt_emailActionPerformed

    private void txt_numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_numeroActionPerformed

    private void txt_cinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cinActionPerformed
        txt_dateNaiss.requestFocusInWindow();
    }//GEN-LAST:event_txt_cinActionPerformed

    private void btn_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseClicked
       MyAccount acc = new MyAccount(name);
       acc.show();
       acc.setAgentVisible();
       this.dispose();
    }//GEN-LAST:event_btn_backMouseClicked

    private void cmb_genreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_genreActionPerformed
        cmb_situation.requestFocusInWindow();
    }//GEN-LAST:event_cmb_genreActionPerformed

    private void cmb_situationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_situationActionPerformed
        txt_email.requestFocusInWindow();
    }//GEN-LAST:event_cmb_situationActionPerformed

    private void txt_dateNaissAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txt_dateNaissAncestorAdded
       
    }//GEN-LAST:event_txt_dateNaissAncestorAdded


    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateAccount().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanel;
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_soumettre;
    private javax.swing.JComboBox<String> cmb_genre;
    private javax.swing.JComboBox<String> cmb_situation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabell8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.ButtonGroup myGroup;
    private javax.swing.JTextField txt_cin;
    private com.toedter.calendar.JDateChooser txt_dateNaiss;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_lieuNaiss;
    private javax.swing.JTextField txt_nom;
    private javax.swing.JFormattedTextField txt_numero;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_prenom;
    // End of variables declaration//GEN-END:variables
}
