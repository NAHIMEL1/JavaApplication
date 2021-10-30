/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ADS
 */
public class Login_Form extends javax.swing.JFrame {

    /**
     * Creates new form Login_Form4
     *
     */
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/login_database";
     
    public Login_Form() {
        initComponents();
    }
     
    public void userverification(String username, String password){


        if(rd_admin.isSelected()){                
          try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;
            ResultSetMetaData metaData;
            String resultat = "";

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();

            String sql = "Select * from responsable where Email = ? and Password = ?";
            
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, username.trim());
            pst.setString(2, password.trim());
            resultSet = pst.executeQuery();

            if (resultSet.next()) {
                
                String name = resultSet.getString("Nom");
                MyAccount homee = new MyAccount(name);
                homee.show();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Admin doesn't exist in the database !");
            }

        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
  
    } else if(rd_agent.isSelected()){
        
        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;
            ResultSetMetaData metaData;
            String resultat = "";

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();

            String sql = "Select * from agent where Email = ? and Password = ?";
            
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, username.trim());
            pst.setString(2, password.trim());
            resultSet = pst.executeQuery();

            if (resultSet.next()) {                
                String name = resultSet.getString("Nom");
                Agent homee = new Agent(name);
                homee.show();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Agent doesn't exist in the database !");
            }

        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }else{

        JOptionPane.showMessageDialog(null, "You have to be a agent or admin !");

    }
      
    }
    
    
    public String getPassword(String email){
        
        String password1 = "";
        
        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;
            ResultSetMetaData metaData;
            String resultat = "";

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();

            String sql = "Select * from responsable where Email = ?";
            
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email.trim());

            resultSet = pst.executeQuery();

            if (resultSet.next()) {
                
                password1 = resultSet.getString("Password");

            }

        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        
        
        return password1;
        
    }  
    

    public void resetPassword(String content, String email){
       
        String toEmail = email.trim();
       
        String fromEmail = "mustaphanahi2017@gmail.com";
        String fromEmailPassword = "mustaphanahi2017";
        
                              
        Properties properties = new Properties();
        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication (fromEmail, fromEmailPassword);
            }
        });
        MimeMessage message = new MimeMessage(session);
        try {

            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Password reset !");
            String htmlCode = "<h2><b>" + content+ "</b></h2>";
            message.setContent(htmlCode, "text/html");

        } catch (Exception ex) {
            System.out.println("Error" + ex);
        }

        try {
            Transport.send(message);
        } catch (MessagingException ex) {
            java.util.logging.Logger.getLogger(VerificationEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Message sent successfully");
    
    
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton3 = new javax.swing.JRadioButton();
        btn_grp = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        rd_admin = new javax.swing.JRadioButton();
        rd_agent = new javax.swing.JRadioButton();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        jRadioButton3.setText("jRadioButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));

        jLabel7.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\Capture.PNG")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(186, 79, 84));
        jLabel8.setText("V E C T R A    S T A R ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel7)))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        username.setBackground(new java.awt.Color(0, 102, 102));
        username.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        username.setForeground(new java.awt.Color(255, 255, 255));
        username.setText("Username");
        username.setBorder(null);
        username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameFocusGained(evt);
            }
        });
        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });
        jPanel3.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 230, 30));

        password.setBackground(new java.awt.Color(0, 102, 102));
        password.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        password.setForeground(new java.awt.Color(255, 255, 255));
        password.setText("PassWord");
        password.setBorder(null);
        password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordFocusGained(evt);
            }
        });
        jPanel3.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 146, 229, 30));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 118, 280, 10));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 280, 10));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\user_50px.png")); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 40, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\database_40px.png")); // NOI18N
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 40, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\lock_50px.png")); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 40, 40));

        btn_grp.add(rd_admin);
        rd_admin.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        rd_admin.setForeground(new java.awt.Color(255, 255, 255));
        rd_admin.setText("Admin");
        jPanel3.add(rd_admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 90, -1));

        btn_grp.add(rd_agent);
        rd_agent.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        rd_agent.setForeground(new java.awt.Color(255, 255, 255));
        rd_agent.setText("Agent");
        jPanel3.add(rd_agent, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 90, -1));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 280, 10));

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\multiply_30px.png")); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(463, 0, 40, 40));

        jButton3.setBackground(new java.awt.Color(0, 51, 51));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\login.png")); // NOI18N
        jButton3.setText("Login");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 110, 40));

        jButton4.setBackground(new java.awt.Color(0, 51, 51));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\exit.png")); // NOI18N
        jButton4.setText("Exit");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 310, 110, 40));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Mot de passe oublié ? ");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 370, -1, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
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

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
       password.requestFocusInWindow();
    }//GEN-LAST:event_usernameActionPerformed

    private void usernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameFocusGained
        username.setText("");
    }//GEN-LAST:event_usernameFocusGained

    private void passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFocusGained
        password.setText("");   
    }//GEN-LAST:event_passwordFocusGained
 
    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
       
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(username.getText().equals("") ||  password.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Please enter username & password");
        }else{
            userverification(username.getText().trim(),password.getText().trim());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked

        String email = JOptionPane.showInputDialog(null,
                                                  "Enter your email",
                                                  "Reset your password",
                                                  JOptionPane.INFORMATION_MESSAGE);
        
       if(email.equals("")){
            JOptionPane.showMessageDialog(null, "Please enter your email!");
       }else{
        
            if(getPassword(email).equals("")){
                 JOptionPane.showMessageDialog(null, "You doesn't exist in the database !");
            }else{

                String content = "Your password is : " + getPassword(email);
                resetPassword(content,email);
            }
           }
    }//GEN-LAST:event_jLabel5MouseClicked

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
            java.util.logging.Logger.getLogger(Login_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login_Form form = new Login_Form();
                form.show();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btn_grp;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JPasswordField password;
    private javax.swing.JRadioButton rd_admin;
    private javax.swing.JRadioButton rd_agent;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
