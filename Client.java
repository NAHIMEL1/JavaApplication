/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;
import static Home.Login_Form.DATABASE_URL;
import static Home.Login_Form.JDBC_DRIVER;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ADS
 */
public class Client extends javax.swing.JFrame {

    /**
     * Creates new form MyAccount
     * 
     */
    
   DefaultTableModel model;
            
    public Client() {
        initComponents();
        setAgentRecordsToTable();
 
    }
    
 
 

    public Client(String name){
        
        initComponents();
        // setExtendedState(MAXIMIZED_BOTH);
        setAgentRecordsToTable();
        lbl_user.setText(name);
                
    }
  
    
    public void setAgentRecordsToTable(){
        
         try {
            Connection connection = null;
            Statement statement = null;
            ResultSet rs;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql = "select * from agent";
            PreparedStatement pst = connection.prepareStatement(sql);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                String cin = rs.getString("CIN");
                Date date_Naiss = rs.getDate("Date_Naissance");
                String lieu_Naiss = rs.getString("Lieu_Naissance");
                String genre = rs.getString("Genre");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                String situation = rs.getString("Situation_sociale");
                String numero = rs.getString("Numero");
                
                
                Object[] obj = {nom, prenom, cin, date_Naiss, lieu_Naiss, genre, password, email, situation,numero};
                
                model = (DefaultTableModel)tbl_Agent.getModel();
                model.addRow(obj);  
            }
         
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public void clearAgentTable() {
        DefaultTableModel model1 = (DefaultTableModel)tbl_Agent.getModel();
        model1.setRowCount(0);
    }
    
    
    public void deleteAgent(){
        
            try {
            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;
            ResultSet rs1 = null;
            
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();

            DefaultTableModel model1 = (DefaultTableModel)tbl_Agent.getModel();
            int selectedRowIndex = tbl_Agent.getSelectedRow();
            String nom;
            nom = model1.getValueAt(selectedRowIndex, 0).toString().trim();
                   
            String sql = "select * from commandeagent where Agent = ?"; 
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, nom);   
            rs = pst.executeQuery();
            
            while (rs.next()) {
                
                int id  = Integer.parseInt(rs.getString("ID"));
                
                String sql1 = "DELETE FROM contient WHERE ID = ?";
                
                PreparedStatement pst1 = connection.prepareStatement(sql1);
                pst1.setInt(1, id);
                pst1.executeUpdate();
            }  
            
            sql = "delete from commandeagent where Agent = ?"; 
            pst = connection.prepareStatement(sql);
            pst.setString(1, nom);   
            pst.executeUpdate();
            
            
            sql = "delete from agent where Nom = ?"; 
            pst = connection.prepareStatement(sql);
            pst.setString(1, nom);   
          
            int rowCount =  pst.executeUpdate();     
            if(rowCount == 1){
                JOptionPane.showMessageDialog(this, "Agent deleted succesfully");
                clearAgentTable();
                setAgentRecordsToTable();
            }else{
                JOptionPane.showMessageDialog(this, "Agent deletion failed");
            }  

            } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
            } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        
    }
   
    
    public void search(String str){
        
        model = (DefaultTableModel)tbl_Agent.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tbl_Agent.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
        
            }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        panelSidebar = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        lbl_user = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lbl_rechercher = new javax.swing.JLabel();
        txt_rechercher = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Agent = new javax.swing.JTable();
        btn_supp = new javax.swing.JButton();
        btn_email = new javax.swing.JButton();
        btn_ajt = new javax.swing.JButton();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("jMenu5");

        jMenu6.setText("jMenu6");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelSidebar.setBackground(new java.awt.Color(54, 70, 78));
        panelSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelSidebar.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 132, 220, 10));

        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\admin.png")); // NOI18N
        panelSidebar.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 70, 80));

        lbl_user.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lbl_user.setForeground(new java.awt.Color(255, 255, 255));
        panelSidebar.add(lbl_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 93, 180, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\logout.png")); // NOI18N
        jLabel9.setText(" Exit");
        jLabel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });
        panelSidebar.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 180, 60));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\back-button.png")); // NOI18N
        jLabel8.setText(" Back");
        jLabel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
        });
        panelSidebar.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 180, 60));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\home.png")); // NOI18N
        jLabel7.setText(" Home");
        jLabel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });
        panelSidebar.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 180, 60));

        getContentPane().add(panelSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 700));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_rechercher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newpackage/my icons/search2.png"))); // NOI18N
        jPanel1.add(lbl_rechercher, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 110, 70));

        txt_rechercher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rechercherActionPerformed(evt);
            }
        });
        txt_rechercher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_rechercherKeyReleased(evt);
            }
        });
        jPanel1.add(txt_rechercher, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 640, 40));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nom", "Prénom", "Adresse", "Numéro" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 110, 40));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newpackage/my icons/signup.png"))); // NOI18N
        jLabel1.setText("   Agents ");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 240, 40));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 130));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 980, -1));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_Agent.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbl_Agent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom", "Prénom", "CIN", "Date de naissance ", "Lieu de naissance", "Genre", "Situation sociale", "Email", "Mot de passe ", "Numéro de tél"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Agent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbl_Agent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_AgentMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Agent);
        if (tbl_Agent.getColumnModel().getColumnCount() > 0) {
            tbl_Agent.getColumnModel().getColumn(0).setResizable(false);
            tbl_Agent.getColumnModel().getColumn(1).setResizable(false);
            tbl_Agent.getColumnModel().getColumn(2).setResizable(false);
            tbl_Agent.getColumnModel().getColumn(3).setResizable(false);
            tbl_Agent.getColumnModel().getColumn(4).setResizable(false);
            tbl_Agent.getColumnModel().getColumn(5).setResizable(false);
            tbl_Agent.getColumnModel().getColumn(6).setResizable(false);
            tbl_Agent.getColumnModel().getColumn(7).setResizable(false);
            tbl_Agent.getColumnModel().getColumn(8).setResizable(false);
            tbl_Agent.getColumnModel().getColumn(9).setResizable(false);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 960, 320));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 980, 370));

        btn_supp.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btn_supp.setText("Supprimer un agent");
        btn_supp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_suppMouseClicked(evt);
            }
        });
        btn_supp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suppActionPerformed(evt);
            }
        });
        jPanel2.add(btn_supp, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 600, 220, 50));

        btn_email.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btn_email.setText("Envoyer un E-mail à un agent");
        btn_email.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_emailMouseClicked(evt);
            }
        });
        btn_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_emailActionPerformed(evt);
            }
        });
        jPanel2.add(btn_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 510, 610, 50));

        btn_ajt.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btn_ajt.setText("Ajouter un agent");
        btn_ajt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ajtMouseClicked(evt);
            }
        });
        btn_ajt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ajtActionPerformed(evt);
            }
        });
        jPanel2.add(btn_ajt, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 600, 220, 50));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 1000, 700));

        setSize(new java.awt.Dimension(1214, 737));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_rechercherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rechercherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rechercherActionPerformed

    private void tbl_AgentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_AgentMouseClicked
           
    }//GEN-LAST:event_tbl_AgentMouseClicked

    private void txt_rechercherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rechercherKeyReleased
       String searchString = txt_rechercher.getText();
       search(searchString);
    }//GEN-LAST:event_txt_rechercherKeyReleased

    private void btn_suppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suppActionPerformed
       
    }//GEN-LAST:event_btn_suppActionPerformed

    private void btn_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_emailActionPerformed

    private void btn_ajtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ajtActionPerformed
       
    }//GEN-LAST:event_btn_ajtActionPerformed

    private void btn_suppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_suppMouseClicked
         deleteAgent();
    }//GEN-LAST:event_btn_suppMouseClicked

    private void btn_ajtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ajtMouseClicked
         CreateAccount acc = new CreateAccount(lbl_user.getText());
         acc.show();
         this.dispose();
    }//GEN-LAST:event_btn_ajtMouseClicked

    private void btn_emailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_emailMouseClicked
       
        DefaultTableModel model1 = (DefaultTableModel)tbl_Agent.getModel();
        int selectedRowIndex = tbl_Agent.getSelectedRow();
        if( selectedRowIndex == -1 ){
             JOptionPane.showMessageDialog(this, "Please select an agent");
                
        }else{
            
            String email;
            email = model1.getValueAt(selectedRowIndex, 7).toString().trim();

            VerificationEmail aa = new VerificationEmail(lbl_user.getText(), email);
            aa.show();
            this.dispose();
        }
        
            
    }//GEN-LAST:event_btn_emailMouseClicked

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
 
    }//GEN-LAST:event_jLabel7MouseExited

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
      
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        MyAccount h = new MyAccount();
        h.show();
        this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
  
    }//GEN-LAST:event_jLabel8MouseExited

    private void jLabel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseEntered
     
    }//GEN-LAST:event_jLabel8MouseEntered

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked

        /*MyAccount acc = new MyAccount(lbl_user.getText());
        acc.show();
        this.dispose(); */
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
       
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel9MouseClicked

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
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
    
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ajt;
    private javax.swing.JButton btn_email;
    private javax.swing.JButton btn_supp;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbl_rechercher;
    private javax.swing.JLabel lbl_user;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JTable tbl_Agent;
    private javax.swing.JTextField txt_rechercher;
    // End of variables declaration//GEN-END:variables
}
