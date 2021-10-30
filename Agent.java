/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import static Home.Login_Form.DATABASE_URL;
import static Home.Login_Form.JDBC_DRIVER;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ADS
 */

public class Agent extends javax.swing.JFrame {

    /**
     * Creates new form Agent
     */
    
    DefaultTableModel model;
    public Agent() { 
        
        initComponents();
    }
    
    
    String lname;
    int numero;
    public Agent(String name){
        
         initComponents();
         this.lname = name;
         lbl_username.setText(name);
         lbl_agent.setText(name);
         this.numero = getId();
         lbl_numero.setText(Integer.toString(this.numero));  
         
         Calendar calendar = Calendar.getInstance(); 
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         lbl_date.setText( formatter.format(calendar.getTime()));
         
         pnl_details.setVisible(false);
         
         DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
         headerRenderer.setBackground(new Color(0,0,128));
         headerRenderer.setForeground(Color.WHITE);
         headerRenderer.setFont( new Font("Tahoma", Font.BOLD , 36));

         for (int i = 0; i < tbl_cmd.getModel().getColumnCount(); i++) {
             tbl_cmd.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
         }
         
         for (int i = 0; i < tbl_cmdAgent.getModel().getColumnCount(); i++) {
             tbl_cmdAgent.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
         }
         
         for (int i = 0; i < tbl_element.getModel().getColumnCount(); i++) {
             tbl_element.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
         }
         
         tbl_cmd.setRowHeight(20);
         tbl_cmdAgent.setRowHeight(20);
         tbl_element.setRowHeight(20);

         
    }
    
    String designation, obs;
    int quantite, id;

    
    private int getNumberOfRows(){
        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql = "SELECT COUNT(*) as numberOfRows FROM commandeagent";
            PreparedStatement pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();
            resultSet.next();
            
            int number = resultSet.getInt("numberOfRows");
            return number;
            
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return 0;
    }
    
    
    public int getId(){
        ResultSet rs = null;
        Connection connection = null;
        Statement st = null;
       try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            st = connection.createStatement();
            String sql = "select max(ID) from commandeagent";
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                 id = rs.getInt(1);
                 ++id;
            }
            
            }catch(Exception e ){

            e.printStackTrace();
         }
      
       return id;
        
    }
    
    
    public void addCommandToDatabase() {
        try {
            Connection connection = null;
            Statement statement = null;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();

            String sql = "insert into commandeagent(ID, Agent, Date, Etat) values (?,?,?,?)";

            PreparedStatement pst = connection.prepareStatement(sql);
            
            pst.setInt(1,Integer.parseInt(lbl_numero.getText()));
            pst.setString(2, lbl_agent.getText());
            
            java.util.Date utilDate = new Date();
            java.sql.Date date = new java.sql.Date(utilDate.getTime());
            pst.setDate(3,date);
            
            pst.setString(4, "En cours");

            int rowCount = pst.executeUpdate();
            if (rowCount == 1) {
                JOptionPane.showMessageDialog(this, "Commmand added succesfully");
            } else {
                    JOptionPane.showMessageDialog(this, "Command insertion failed");
            }
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }


    boolean validation() {
 
        if (txt_designation.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "please enter designation");
            return false;
        }

        if (!txt_quantite.getText().isEmpty() && txt_quantite.getText().matches("\\d+")) {
            Integer quantite = Integer.parseInt(txt_quantite.getText());
        } else {
            JOptionPane.showMessageDialog(this, "Please enter the quantity");
        }
        return true;
    }

    
    public void setRecordsToTable() {
        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql = "select * from contient Where ID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(lbl_numero.getText()));
            
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                designation = resultSet.getString("Désignation");
                quantite = Integer.parseInt(resultSet.getString("Quantité"));
                obs = resultSet.getString("OBS");
                Object[] obj = {designation, quantite, obs};

                model = (DefaultTableModel) tbl_cmdAgent.getModel();
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

    
    public void addToTables() {
        
        designation = txt_designation.getText();
        obs = txt_obs.getText();
        quantite = Integer.parseInt(txt_quantite.getText());

        Object[] obj = {designation, quantite, obs};

        model = (DefaultTableModel)tbl_cmdAgent.getModel();
        model.addRow(obj);
    }

    
    public void vider() {
        txt_quantite.setText("");
        txt_designation.setText("");
        txt_obs.setText("");
    }

    
    public void addCommandeAgentToDatabase() {
        try {
            Connection connection = null;
            Statement statement = null;
            
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            
            DefaultTableModel model1 = (DefaultTableModel)tbl_cmdAgent.getModel();
            
            for(int i = 0  ; i< model1.getRowCount() ; i++ ){
                
                int id1 = Integer.valueOf(lbl_numero.getText());
                String designation1 = model1.getValueAt(i, 0).toString();
                int quantite1 = Integer.valueOf(model1.getValueAt(i, 1).toString());
                String obs1 = model1.getValueAt(i, 2).toString();
                
                String sql = "insert into contient(ID, Désignation, Quantité, OBS) values (?,?,?,?)";
                PreparedStatement pst = connection.prepareStatement(sql);
               
                pst.setInt(1,id1);
                pst.setString(2, designation1);
                pst.setInt(3,quantite1);
                pst.setString(4,obs1);
                
                pst.executeUpdate();
            } 
            
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public void clearTable() {
        DefaultTableModel model1 = (DefaultTableModel)tbl_cmdAgent.getModel();
        model1.setRowCount(0);
    }
    
    public void clearElementTable() {
        DefaultTableModel model1 = (DefaultTableModel)tbl_element.getModel();
        model1.setRowCount(0);
    }
    
    public void clearCmdTable(){
        DefaultTableModel model1 = (DefaultTableModel)tbl_cmd.getModel();
        model1.setRowCount(0);

    }

    
    public void fromTableToTxtField(){
        
        int rowNo = tbl_cmdAgent.getSelectedRow();
        TableModel model1 = tbl_cmdAgent.getModel();

        txt_designation.setText((String) model1.getValueAt(rowNo, 0));
        txt_quantite.setText(model1.getValueAt(rowNo, 1).toString());
        txt_obs.setText((String) model1.getValueAt(rowNo, 2).toString());
        
    }
    
    public void updateRowSelected(){
        
         DefaultTableModel model1 = (DefaultTableModel)tbl_cmdAgent.getModel();
         int selectedRowIndex = tbl_cmdAgent.getSelectedRow();
         
         model1.setValueAt(txt_designation.getText(), selectedRowIndex, 0);
         model1.setValueAt(txt_quantite.getText(), selectedRowIndex, 1);
         if(txt_obs.equals("")){
                 model1.setValueAt("", selectedRowIndex, 2);
         }else{
                 model1.setValueAt(txt_obs.getText(), selectedRowIndex, 2);
         }
    
    }
   
    
    public void deleteSelectedRow(){
        
        int numRows = tbl_cmdAgent.getSelectedRows().length;
         if(numRows == 0){
           JOptionPane.showMessageDialog(this, "Table is empty", "Alert", JOptionPane.WARNING_MESSAGE);
         } else{
            
            for(int i=0; i<numRows ; i++ ) {
                     model.removeRow(tbl_cmdAgent.getSelectedRow());
            }
     }
    }

 /****************************************************************************************************************/
      
    public void getCommandFromDatabase(){
        
         try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql;
            int choix = cmb_choix.getSelectedIndex();
            
             switch (choix) {
                 case 0:
                     {
                         sql = "select * from commandeagent Where Agent = ?";
                         PreparedStatement pst = connection.prepareStatement(sql);
                         pst.setString(1, lbl_agent.getText());
                         resultSet = pst.executeQuery();
                         break;
                     }
                 case 1:
                     {
                         sql = "select * from commandeagent where Agent = ? and Etat = ?";
                         PreparedStatement pst = connection.prepareStatement(sql);
                         pst.setString(1, lbl_agent.getText());
                         pst.setString(2,"En cours");
                         resultSet = pst.executeQuery();
                         break;
                     }
                 case 2:
                     {
                         sql = "select * from commandeagent where Agent = ? and Etat = ?";
                         PreparedStatement pst = connection.prepareStatement(sql);
                         pst.setString(1, lbl_agent.getText());
                         pst.setString(2,"Confirmée");
                         resultSet = pst.executeQuery();
                         break;
                     }
                 case 3:
                     {
                         sql = "select * from commandeagent where Agent = ? and Etat = ?";
                         PreparedStatement pst = connection.prepareStatement(sql);
                         pst.setString(1, lbl_agent.getText());
                         pst.setString(2,"Annulée");
                         resultSet = pst.executeQuery();
                         break;
                     }
                 default:
                     break;
             }

            while (resultSet.next()) {
                int id1 = Integer.parseInt(resultSet.getString("ID"));
                Date date = resultSet.getDate("Date");
                String etat = resultSet.getString("Etat");

                Object[] obj = {id1, date, etat};

                model = (DefaultTableModel) tbl_cmd.getModel();
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
    
    
    public void getValuesFromDatabase(){
        
         try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            
            DefaultTableModel model1 = (DefaultTableModel)tbl_cmd.getModel();
            int selectedRowIndex = tbl_cmd.getSelectedRow();
            
            int id = (int) model1.getValueAt(selectedRowIndex, 0);
            
            
            String sql = "select * from contient Where ID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            
            resultSet = pst.executeQuery();
            
            while (resultSet.next()) {
                
                int id1 = Integer.parseInt(resultSet.getString("ID"));
                String designation1 = resultSet.getString("Désignation");
                int quantite1 = Integer.parseInt(resultSet.getString("Quantité"));
                String obs1 = resultSet.getString("OBS");
                
                Object[] obj = {id1, designation1 , quantite1 , obs1 };

                model1 = (DefaultTableModel) tbl_element.getModel();
                model1.addRow(obj);
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
        model = (DefaultTableModel)tbl_cmd.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        
        tbl_cmd.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));  
    }
    
   boolean validationEmail() {
 
        if (txt_toEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "please enter email adress");
            return false;
        }

        if (txt_subject.getText().equals("")) {
           JOptionPane.showMessageDialog(this, "please enter the subject");
           return false;
        } else {
            JOptionPane.showMessageDialog(this, "Please enter the the content of email");
        }
        return true;
    }
    
    
    public void sendEmail(){
        String toEmail = txt_toEmail.getText();
        String fromEmail = "mustaphanahi2017@gmail.com";
        String fromEmailPassword = "mustaphanahi2017";
        
        String subject = txt_subject.getText();
        String content = txt_content.getText();                
                
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
            message.setSubject(subject);
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
    
      public void annuler(){
        txt_toEmail.setText("");
        txt_subject.setText("");
        txt_content.setText("");
    }
       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lbl_deconnexion = new javax.swing.JLabel();
        lbl_nouvelleCmd = new javax.swing.JLabel();
        lbl_détailsCmd1 = new javax.swing.JLabel();
        lbl_contacterRespo = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_designation = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_obs = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_quantite = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        btn_ajouter = new javax.swing.JButton();
        btn_modifier = new javax.swing.JButton();
        btn_supprimer = new javax.swing.JButton();
        btn_vider = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbl_date = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_numero = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_agent = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_cmdAgent = new javax.swing.JTable();
        btn_annulerCmd = new javax.swing.JButton();
        btn_validerCmd = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        cmb_choix = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_cmd = new javax.swing.JTable();
        jSeparator3 = new javax.swing.JSeparator();
        pnl_details = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_element = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        lbl_rechercher = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txt_subject = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btn_annuler = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_content = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        txt_toEmail = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btn_send = new javax.swing.JButton();

        jButton1.setText("Afficher");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(54, 70, 78));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Documents\\NetBeansProjects\\Login Form\\src\\Home\\icons\\Capture.PNG")); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, -20, 190, 140));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Bjr !");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 60, 35));

        lbl_username.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_username.setForeground(new java.awt.Color(255, 0, 0));
        lbl_username.setText(" ");
        jPanel3.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 110, 35));
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 192, 14));

        lbl_deconnexion.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        lbl_deconnexion.setForeground(new java.awt.Color(255, 255, 255));
        lbl_deconnexion.setText(" Déconnexion");
        lbl_deconnexion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        lbl_deconnexion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_deconnexionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_deconnexionMouseEntered(evt);
            }
        });
        jPanel3.add(lbl_deconnexion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 171, 41));

        lbl_nouvelleCmd.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        lbl_nouvelleCmd.setForeground(new java.awt.Color(255, 255, 255));
        lbl_nouvelleCmd.setText(" Nouvelle Commande ");
        lbl_nouvelleCmd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        lbl_nouvelleCmd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_nouvelleCmdMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_nouvelleCmdMouseEntered(evt);
            }
        });
        jPanel3.add(lbl_nouvelleCmd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 170, 41));

        lbl_détailsCmd1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        lbl_détailsCmd1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_détailsCmd1.setText(" Détails commandes ");
        lbl_détailsCmd1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        lbl_détailsCmd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_détailsCmd1MouseClicked(evt);
            }
        });
        jPanel3.add(lbl_détailsCmd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 171, 41));

        lbl_contacterRespo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        lbl_contacterRespo.setForeground(new java.awt.Color(255, 255, 255));
        lbl_contacterRespo.setText(" Contacter le resp");
        lbl_contacterRespo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        lbl_contacterRespo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_contacterRespoMouseClicked(evt);
            }
        });
        jPanel3.add(lbl_contacterRespo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 171, 41));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 610));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 610));

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 45)); // NOI18N
        jLabel6.setText(" You are connected to the app !");
        jLabel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 4, true));
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 730, 90));

        jTabbedPane1.addTab("tab2", jPanel5);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Désignation");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 100, 30));

        txt_designation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_designationActionPerformed(evt);
            }
        });
        jPanel6.add(txt_designation, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 190, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Remarque ");
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 100, 30));

        txt_obs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_obsActionPerformed(evt);
            }
        });
        jPanel6.add(txt_obs, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 610, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Quantité");
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 110, 30));

        txt_quantite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_quantiteActionPerformed(evt);
            }
        });
        jPanel6.add(txt_quantite, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 190, 30));
        jPanel6.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 810, 10));

        btn_ajouter.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_ajouter.setText("Ajouter");
        btn_ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ajouterActionPerformed(evt);
            }
        });
        jPanel6.add(btn_ajouter, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 120, 40));

        btn_modifier.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_modifier.setText("Modifier");
        btn_modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifierActionPerformed(evt);
            }
        });
        jPanel6.add(btn_modifier, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 120, 40));

        btn_supprimer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_supprimer.setText("Supprimer");
        btn_supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_supprimerActionPerformed(evt);
            }
        });
        jPanel6.add(btn_supprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, 130, 40));

        btn_vider.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_vider.setText("Vider");
        btn_vider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_viderActionPerformed(evt);
            }
        });
        jPanel6.add(btn_vider, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 130, 40));

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 810, 210));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_date.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lbl_date.setForeground(new java.awt.Color(255, 0, 51));
        jPanel2.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 0, 140, 40));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setText("En : ");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 0, 50, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel9.setText("Cette commande N° : ");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 200, 40));

        lbl_numero.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lbl_numero.setForeground(new java.awt.Color(255, 51, 51));
        jPanel2.add(lbl_numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 80, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel10.setText("Se fait par :");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 120, 40));

        lbl_agent.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lbl_agent.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.add(lbl_agent, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 150, 40));

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 810, 40));

        tbl_cmdAgent.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbl_cmdAgent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Désignation", "Quantité", "OBS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_cmdAgent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_cmdAgentMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_cmdAgent);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 780, 230));

        btn_annulerCmd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_annulerCmd.setText("Annuler la commande ");
        btn_annulerCmd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annulerCmdActionPerformed(evt);
            }
        });
        jPanel4.add(btn_annulerCmd, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 540, 220, 40));

        btn_validerCmd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_validerCmd.setText("Valider la commande ");
        btn_validerCmd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_validerCmdActionPerformed(evt);
            }
        });
        jPanel4.add(btn_validerCmd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 540, 220, 40));

        jTabbedPane1.addTab("tab1", jPanel4);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmb_choix.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cmb_choix.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Toutes les commandes", "Les commandes en cours ", "Les commandes confirmées", "Les commandes annulées " }));
        cmb_choix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_choixActionPerformed(evt);
            }
        });
        jPanel7.add(cmb_choix, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 380, 30));

        tbl_cmd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_cmd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date", "Etat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_cmd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_cmdMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_cmd);

        jPanel7.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 780, 220));
        jPanel7.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 810, 10));

        pnl_details.setBackground(new java.awt.Color(204, 204, 204));
        pnl_details.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_element.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_element.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Désignation", "Quantité", "OBS"
            }
        ));
        jScrollPane1.setViewportView(tbl_element);

        pnl_details.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 780, 240));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText(">> Détail de la commande");
        pnl_details.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 3, 250, 30));

        jPanel7.add(pnl_details, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 780, 260));

        lbl_rechercher.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\search_45px.png")); // NOI18N
        jPanel7.add(lbl_rechercher, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 60, 50));

        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });
        jPanel7.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 210, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Date", "Etat" }));
        jPanel7.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 19, 60, 30));

        jTabbedPane1.addTab("tab3", jPanel7);

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 153), 3, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_subject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_subjectActionPerformed(evt);
            }
        });
        jPanel9.add(txt_subject, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 390, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\content_50px.png")); // NOI18N
        jLabel11.setText("Content");
        jPanel9.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 140, 50));

        btn_annuler.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btn_annuler.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\cancel_50px.png")); // NOI18N
        btn_annuler.setText("Cancel");
        btn_annuler.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_annulerMouseClicked(evt);
            }
        });
        btn_annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annulerActionPerformed(evt);
            }
        });
        jPanel9.add(btn_annuler, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 350, 160, 50));

        txt_content.setColumns(20);
        txt_content.setRows(5);
        jScrollPane4.setViewportView(txt_content);

        jPanel9.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 390, 150));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\email_50px.png")); // NOI18N
        jLabel12.setText("Email ");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 140, 40));

        txt_toEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_toEmailActionPerformed(evt);
            }
        });
        jPanel9.add(txt_toEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 390, 40));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\recurring_appointment_exception_50px.png")); // NOI18N
        jLabel13.setText("Subject");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 120, 40));

        btn_send.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btn_send.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\email_send_50px.png")); // NOI18N
        btn_send.setText("Send");
        btn_send.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_sendMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_sendMouseEntered(evt);
            }
        });
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });
        jPanel9.add(btn_send, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 160, 50));

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 640, 420));

        jTabbedPane1.addTab("tab4", jPanel8);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, -30, 810, 640));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_nouvelleCmdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_nouvelleCmdMouseClicked
       jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_lbl_nouvelleCmdMouseClicked

    private void lbl_deconnexionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_deconnexionMouseClicked
        Login_Form form = new Login_Form();
        form.show();
        this.dispose();
    }//GEN-LAST:event_lbl_deconnexionMouseClicked

    private void txt_quantiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_quantiteActionPerformed
        txt_obs.requestFocusInWindow();
    }//GEN-LAST:event_txt_quantiteActionPerformed

    private void txt_obsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_obsActionPerformed
        txt_quantite.requestFocusInWindow();
    }//GEN-LAST:event_txt_obsActionPerformed

    private void btn_annulerCmdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annulerCmdActionPerformed
         clearTable();
         vider();
    }//GEN-LAST:event_btn_annulerCmdActionPerformed

    private void btn_ajouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ajouterActionPerformed
          if (validation()) {
            addToTables();
        }    
    }//GEN-LAST:event_btn_ajouterActionPerformed

    private void btn_modifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifierActionPerformed
        if(validation())
           updateRowSelected();
    
    }//GEN-LAST:event_btn_modifierActionPerformed

    private void btn_supprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supprimerActionPerformed
            deleteSelectedRow();
    }//GEN-LAST:event_btn_supprimerActionPerformed

    private void tbl_cmdAgentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_cmdAgentMouseClicked
        fromTableToTxtField();
    }//GEN-LAST:event_tbl_cmdAgentMouseClicked

    private void btn_viderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viderActionPerformed
            vider();
    }//GEN-LAST:event_btn_viderActionPerformed

    private void btn_validerCmdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_validerCmdActionPerformed
       
       DefaultTableModel model1 = (DefaultTableModel)tbl_cmdAgent.getModel();
       int row = model1.getRowCount();
       if(row == 0){
            JOptionPane.showMessageDialog(this, "Please add Products", "Alert", JOptionPane.WARNING_MESSAGE);
       }else{
       addCommandToDatabase();
       addCommandeAgentToDatabase();
       lbl_numero.setText(Integer.toString(getId()));
       clearTable();
    }//GEN-LAST:event_btn_validerCmdActionPerformed
    }
    private void txt_designationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_designationActionPerformed
        txt_obs.requestFocusInWindow();
        clearTable();
    }//GEN-LAST:event_txt_designationActionPerformed

    private void lbl_détailsCmd1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_détailsCmd1MouseClicked
        pnl_details.setVisible(false);
        jTabbedPane1.setSelectedIndex(2);
        clearCmdTable();
        getCommandFromDatabase();   
    }//GEN-LAST:event_lbl_détailsCmd1MouseClicked

    private void tbl_cmdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_cmdMouseClicked
        pnl_details.setVisible(true);
        clearElementTable();
        getValuesFromDatabase();
    }//GEN-LAST:event_tbl_cmdMouseClicked

    private void cmb_choixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_choixActionPerformed
       clearCmdTable();
       clearElementTable();
       getCommandFromDatabase();
    }//GEN-LAST:event_cmb_choixActionPerformed

    private void lbl_nouvelleCmdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_nouvelleCmdMouseEntered
 
    }//GEN-LAST:event_lbl_nouvelleCmdMouseEntered

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
         String searchString = txt_search.getText();
         search(searchString);
    }//GEN-LAST:event_txt_searchKeyReleased

    private void lbl_contacterRespoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_contacterRespoMouseClicked
            jTabbedPane1.setSelectedIndex(3);
            txt_toEmail.setText("companyname@gmail.com");

    }//GEN-LAST:event_lbl_contacterRespoMouseClicked

    private void lbl_deconnexionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_deconnexionMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_deconnexionMouseEntered

    private void txt_subjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_subjectActionPerformed
        txt_content.requestFocusInWindow();
    }//GEN-LAST:event_txt_subjectActionPerformed

    private void btn_annulerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_annulerMouseClicked
        annuler();
    }//GEN-LAST:event_btn_annulerMouseClicked

    private void btn_annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annulerActionPerformed

    }//GEN-LAST:event_btn_annulerActionPerformed

    private void txt_toEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_toEmailActionPerformed
        txt_subject.requestFocusInWindow();
    }//GEN-LAST:event_txt_toEmailActionPerformed

    private void btn_sendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_sendMouseClicked
        if(validationEmail()){
            sendEmail();
        }
    }//GEN-LAST:event_btn_sendMouseClicked

    private void btn_sendMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_sendMouseEntered

    }//GEN-LAST:event_btn_sendMouseEntered

    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_sendActionPerformed

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
            java.util.logging.Logger.getLogger(Agent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Agent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Agent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Agent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Agent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ajouter;
    private javax.swing.JButton btn_annuler;
    private javax.swing.JButton btn_annulerCmd;
    private javax.swing.JButton btn_modifier;
    private javax.swing.JButton btn_send;
    private javax.swing.JButton btn_supprimer;
    private javax.swing.JButton btn_validerCmd;
    private javax.swing.JButton btn_vider;
    private javax.swing.JComboBox<String> cmb_choix;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_agent;
    private javax.swing.JLabel lbl_contacterRespo;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_deconnexion;
    private javax.swing.JLabel lbl_détailsCmd1;
    private javax.swing.JLabel lbl_nouvelleCmd;
    private javax.swing.JLabel lbl_numero;
    private javax.swing.JLabel lbl_rechercher;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_details;
    private javax.swing.JTable tbl_cmd;
    private javax.swing.JTable tbl_cmdAgent;
    private javax.swing.JTable tbl_element;
    private javax.swing.JTextArea txt_content;
    private javax.swing.JTextField txt_designation;
    private javax.swing.JTextField txt_obs;
    private javax.swing.JTextField txt_quantite;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_subject;
    private javax.swing.JTextField txt_toEmail;
    // End of variables declaration//GEN-END:variables
}
