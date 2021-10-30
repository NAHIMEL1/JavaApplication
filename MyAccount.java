/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import static Home.Login_Form.DATABASE_URL;
import static Home.Login_Form.JDBC_DRIVER;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ADS
 */
public class MyAccount extends javax.swing.JFrame {

    /**
     * Creates new form MyAccount
     *
     */
    String luser;

    public MyAccount() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        
        getCurrentDay();

        getInfoFromDatabase();
        setRecordsToNoteTable();
        lbl_username.setText("");
        lbl_username.setText(setUser("Admin : Nahi"));

        tbl_cmdAgent.getColumnModel().getColumn(4).setCellRenderer(new MyAccount.ButtonRenderer());;
        tbl_cmdAgent.getColumnModel().getColumn(4).setCellEditor(new MyAccount.ButtonEditor(new JTextField()));
        

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(0, 0, 128));
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setFont(new Font("Tahoma", Font.BOLD, 36));

        for (int i = 0; i < tbl_cmdAgent.getModel().getColumnCount(); i++) {
            tbl_cmdAgent.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        for (int i = 0; i < tbl_Agent.getModel().getColumnCount(); i++) {
            tbl_Agent.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
         for (int i = 0; i < tbl_note.getModel().getColumnCount(); i++) {
            tbl_note.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
         
         for(int i =0; i< tbl_cmdResp.getModel().getColumnCount(); i++){
             tbl_cmdResp.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
         }
         
         for(int i = 0; i< tbl_cmd.getModel().getColumnCount(); i++){
            tbl_cmd.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        tbl_cmdAgent.setRowHeight(25);
        tbl_Agent.setRowHeight(25);
        tbl_stock.setRowHeight(25);
        tbl_note.setRowHeight(25);
        tbl_cmdResp.setRowHeight(25);
        tbl_Agent.setRowHeight(25);
        tbl_cmd.setRowHeight(25);
         
        addAgentToJComboBox();
        addToJComboBox();
        clearArticleTable();
        setRecordArticleTable();
        
    }
    

    public MyAccount(String loginUser) {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        
        getInfoFromDatabase();
        setRecordsToNoteTable();
        
        lbl_username.setText("");
        lbl_username.setText(setUser(loginUser));
        
        tbl_cmdAgent.getColumnModel().getColumn(4).setCellRenderer(new MyAccount.ButtonRenderer());;
        tbl_cmdAgent.getColumnModel().getColumn(4).setCellEditor(new MyAccount.ButtonEditor(new JTextField()));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(0, 0, 128));
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setFont(new Font("Tahoma", Font.BOLD, 36));

        for (int i = 0; i < tbl_cmdAgent.getModel().getColumnCount(); i++) {
            tbl_cmdAgent.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        for (int i = 0; i < tbl_Agent.getModel().getColumnCount(); i++) {
            tbl_Agent.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        for (int i = 0; i < tbl_note.getModel().getColumnCount(); i++) {
            tbl_note.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        for(int i = 0; i< tbl_cmd.getModel().getColumnCount(); i++){
            tbl_cmd.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        tbl_cmdAgent.setRowHeight(25);
        tbl_Agent.setRowHeight(25);
        tbl_stock.setRowHeight(25);
        tbl_note.setRowHeight(25);
        tbl_cmd.setRowHeight(25);
         
        addAgentToJComboBox();
        addToJComboBox();
        clearArticleTable();
        setRecordArticleTable();;
    }
    

    public String setUser(String name) {
        this.luser = name;
        return this.luser;
    }

    /**
     * ******************************************************************************************************
     */
    
/*********************************************************************************************************************/
// MES COMMANDES
    
     public void setRecordsArticleToTable(int id) {
        
        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql = "select * from contientresp where ID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                
                String designation = resultSet.getString("Désignation");
                Integer quantite = Integer.parseInt(resultSet.getString("Quantité"));
                String destination = resultSet.getString("Destination");
                String obs = resultSet.getString("OBS");
                
                Object[] obj = {designation, quantite,destination, obs};

                DefaultTableModel model = (DefaultTableModel)tbl_cmd.getModel();
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
    
     
    public void getCommandRespFromDatabase() {

        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql;
            sql = "select * from commanderesponsable";
            PreparedStatement pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id1 = Integer.parseInt(resultSet.getString("ID"));
                String marche = resultSet.getString("Marché");
                Date date1 = resultSet.getDate("Date");
                String site = resultSet.getString("Site");
                String num = resultSet.getString("Numéro");

                Object[] obj = {id1, marche, num ,site,date1};

                DefaultTableModel model = (DefaultTableModel) tbl_cmdResp.getModel();
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

    public void clearCmdRespTable() {
        DefaultTableModel model1 = (DefaultTableModel) tbl_cmdResp.getModel();
        model1.setRowCount(0);

    }
    
 
    public void getValuesRespFromDatabase(){  
             PrintDoc doc = new PrintDoc();
             doc.printDocument();
    }
    

/************************************************************************************************************************/
    
   public void getCurrentDay(){

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
            Date now = new Date();
            String strDate = sdfDate.format(now);
            lbl_dateOfDay.setText(strDate);

   } 

// Commandes 
   
/***********************************************************************************************************************/
    class ButtonRenderer extends JButton implements TableCellRenderer {

        //CONSTRUCTOR
        public ButtonRenderer() {
            //SET BUTTON PROPERTIES
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object obj,
          
             boolean selected, boolean focused, int row, int col) {
            //SET PASSED OBJECT AS BUTTON TEXT
            setText((obj == null) ? "" : obj.toString());

            return this;
        }

    }
    
    

    //BUTTON EDITOR CLASS
    class ButtonEditor extends DefaultCellEditor {

        protected JButton btn;
        private String lbl;
        private Boolean clicked;

        public ButtonEditor(JTextField txt) {
            super(txt);

            btn = new JButton();
            btn.setOpaque(true);

            //WHEN BUTTON IS CLICKED
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    fireEditingStopped();
                }
            });
        }

        //OVERRIDE A COUPLE OF METHODS
        @Override
        public Component getTableCellEditorComponent(JTable table, Object obj,
                boolean selected, int row, int col) {

            //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
            lbl = (obj == null) ? "" : obj.toString();
            btn.setText(lbl);
            clicked = true;
            return btn;
        }

        //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
        @Override
        public Object getCellEditorValue() {

            if (clicked) {
                //SHOW US SOME MESSAGE
                getValuesFromDatabase();   
            }
            //SET IT TO FALSE NOW THAT ITS CLICKED
            clicked = false;
            return new String(lbl);
        }

        @Override
        public boolean stopCellEditing() {

            //SET CLICKED TO FALSE FIRST
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            // TODO Auto-generated method stub
            super.fireEditingStopped();
        }

    }
    
  /******************************************************************************************************************/
    
     class ButtonRenderer1 extends JButton implements TableCellRenderer {

        //CONSTRUCTOR
        public ButtonRenderer1() {
            //SET BUTTON PROPERTIES
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object obj,
          
            boolean selected, boolean focused, int row, int col) {
            //SET PASSED OBJECT AS BUTTON TEXT
            setText((obj == null) ? "" : obj.toString());

            return this;
        }

    }

    //BUTTON EDITOR CLASS
    class ButtonEditor1 extends DefaultCellEditor {

        protected JButton btn;
        private String lbl;
        private Boolean clicked;

        public ButtonEditor1(JTextField txt) {
            super(txt);

            btn = new JButton();
            btn.setOpaque(true);

            //WHEN BUTTON IS CLICKED
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    fireEditingStopped();
                }
            });
        }

        //OVERRIDE A COUPLE OF METHODS
        @Override
        public Component getTableCellEditorComponent(JTable table, Object obj,
                boolean selected, int row, int col) {

            //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
            lbl = (obj == null) ? "" : obj.toString();
            btn.setText(lbl);
            clicked = true;
            return btn;
        }

        //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
        @Override
        public Object getCellEditorValue() {

            if (clicked) {
                //SHOW US SOME MESSAGE
                getValuesRespFromDatabase();
            }
            //SET IT TO FALSE NOW THAT ITS CLICKED
            clicked = false;
            return new String(lbl);
        }

        @Override
        public boolean stopCellEditing() {

            //SET CLICKED TO FALSE FIRST
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            // TODO Auto-generated method stub
            super.fireEditingStopped();
        }

    }
    
 /*******************************************************************************************************************/   

    public void getCommandFromDatabase() {

        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql;
            sql = "select * from commandeagent";
            PreparedStatement pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int id1 = Integer.parseInt(resultSet.getString("ID"));
                String agent = resultSet.getString("Agent");
                Date date1 = resultSet.getDate("Date");
                String etat = resultSet.getString("Etat");

                Object[] obj = {id1, agent, date1 ,etat, "visualiser"};

                DefaultTableModel model = (DefaultTableModel) tbl_cmdAgent.getModel();
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

    public void clearCmdTable() {
        DefaultTableModel model1 = (DefaultTableModel) tbl_cmdAgent.getModel();
        model1.setRowCount(0);

    }
    
    
     
    public void getValuesFromDatabase(){
        
         try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            
            DefaultTableModel model1 = (DefaultTableModel)tbl_cmdAgent.getModel();
            int selectedRowIndex = tbl_cmdAgent.getSelectedRow();
            int id = (int) model1.getValueAt(selectedRowIndex, 0);
            
            String sql = "select * from contient Where ID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            resultSet = pst.executeQuery();
            
            String column[]={"ID","DESIGNATION","QUANTITE"};  
            String data[][] = null;

            DefaultTableModel tableModel=new DefaultTableModel(data, column);
            JTable table = new JTable(tableModel);
  
            while (resultSet.next()) {
                
                int id1 = Integer.parseInt(resultSet.getString("ID"));
                String designation1 = resultSet.getString("Désignation");
                int quantite1 = Integer.parseInt(resultSet.getString("Quantité"));;
        
                Object[] obj = {id1, designation1 , quantite1};
                tableModel.addRow(obj);
   
            }
                        
            JScrollPane sp = new JScrollPane(table);    
            sp.setBounds(10, 38, 414, 212);
            JOptionPane.showMessageDialog(this, sp,"Les produits de la commande selectionnée",JOptionPane.INFORMATION_MESSAGE);


        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    
    public void getCommandWantedFromDatabase(){
        
         try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql;
            int choix = cmb_choix.getSelectedIndex();
            String agent = cmd_agent.getSelectedItem().toString().trim();
            
            switch (choix) {
                 case 0:
                     {
                         if(cmd_agent.getSelectedItem().equals("All")){
                            sql = "select * from commandeagent";
                            PreparedStatement pst = connection.prepareStatement(sql);
                            resultSet = pst.executeQuery();
                            break;
                         }else{
                            sql = "select * from commandeagent where Agent = ?";
                            PreparedStatement pst = connection.prepareStatement(sql);
                            pst.setString(1,agent);
                            resultSet = pst.executeQuery();
                            break;
                         }
                     }
                 case 1:
                     {
                         if(cmd_agent.getSelectedItem().equals("All")){
                             
                            sql = "select * from commandeagent where Etat = ?";
                            PreparedStatement pst = connection.prepareStatement(sql);
                            pst.setString(1, "En cours");
                            resultSet = pst.executeQuery();
                            break;
                         }else{
                             
                            sql = "select * from commandeagent where Etat = ? and Agent = ?";
                            PreparedStatement pst = connection.prepareStatement(sql);
                            pst.setString(1,"En cours");
                            pst.setString(2,agent);
                            resultSet = pst.executeQuery();
                            break;
                         }
                     }
                 case 2:
                     {
                         if(cmd_agent.getSelectedItem().equals("All")){
                            sql = "select * from commandeagent where Etat = ?";
                            PreparedStatement pst = connection.prepareStatement(sql);
                            pst.setString(1,"Confirmée");
                            resultSet = pst.executeQuery();
                            break;
                         }else{
                         
                            sql = "select * from commandeagent where Etat = ? and Agent = ?";
                            PreparedStatement pst = connection.prepareStatement(sql);
                            pst.setString(1,"Confirmée");
                            pst.setString(2,agent);
                            resultSet = pst.executeQuery();
                            break;
                         }
                     }
                 case 3:
                     {
                        if(cmd_agent.getSelectedItem().equals("All")){
                            sql = "select * from commandeagent where Etat = ?";
                            PreparedStatement pst = connection.prepareStatement(sql);
                            pst.setString(1,"Annulée");
                            resultSet = pst.executeQuery();
                            break;
                         }else{
                            sql = "select * from commandeagent where Etat = ? and Agent = ?";
                            PreparedStatement pst = connection.prepareStatement(sql);
                            pst.setString(1,"Annulée");
                            pst.setString(2,agent);
                            resultSet = pst.executeQuery();
                            break;
                        }
                     }
                 default:
                     break;
             }

            while (resultSet.next()) {
                int id1 = Integer.parseInt(resultSet.getString("ID"));
                Date date1 = resultSet.getDate("Date");
                String ag = resultSet.getString("Agent");
                String etat = resultSet.getString("Etat");

                Object[] obj = {id1, ag, date1, etat, "visualiser"};

                DefaultTableModel model = (DefaultTableModel) tbl_cmdAgent.getModel();
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

    
    public void addAgentToJComboBox(){
        
        ResultSet rs = null;
        Connection connection = null;
        Statement st = null;
        
        cmd_agent.addItem("All");
        try{
            
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            st = connection.createStatement();
            String sql = "select Nom from agent";
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                 String agent = rs.getString(1);
                 cmd_agent.addItem(agent);
            }
            
            }catch(Exception e ){

            e.printStackTrace();
         } 
    }
    
    
    public void confirmerCommmande(){
        
      DefaultTableModel model1 = (DefaultTableModel)tbl_cmdAgent.getModel();
      int selectedRowIndex = tbl_cmdAgent.getSelectedRow();
      int id1 = (int) model1.getValueAt(selectedRowIndex, 0);
      String etat1 = model1.getValueAt(selectedRowIndex, 3).toString().trim();
       
      
      if(etat1.equals("En cours")){

         try {
                Connection connection = null;
                Statement statement = null;

                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, "root", "");
                statement = connection.createStatement();



                String sql = "update commandeagent set Etat = ? Where ID = ?";
                PreparedStatement pst = connection.prepareStatement(sql);
                
                pst.setInt(2, id1);
                pst.setString(1, "Confirmée");
               

               int rowCount = pst.executeUpdate();
               if (rowCount == 1) {
                JOptionPane.showMessageDialog(this, "Commande a été confirmée");
                clearCmdTable();
                getCommandFromDatabase();
                } else {
                    JOptionPane.showMessageDialog(this, "Error while loading");
                }

        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        
        } 
      
        else if(etat1.equals("Annulée")){
             JOptionPane.showMessageDialog(this, "Cette commande à été dèja annulée");
       }
      
        else{
             JOptionPane.showMessageDialog(this, "Cette commande à été dèja confirmée");
        }
    }
    
    
     public void annulerCommmande(){
        
      DefaultTableModel model1 = (DefaultTableModel)tbl_cmdAgent.getModel();
      int selectedRowIndex = tbl_cmdAgent.getSelectedRow();
      int id1 = (int) model1.getValueAt(selectedRowIndex, 0);
      String etat1 = model1.getValueAt(selectedRowIndex, 3).toString().trim();
       
      
      if(etat1.equals("En cours")){

         try {
                Connection connection = null;
                Statement statement = null;

                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, "root", "");
                statement = connection.createStatement();



                String sql = "update commandeagent set Etat = ? Where ID = ?";
                PreparedStatement pst = connection.prepareStatement(sql);
                
                pst.setInt(2, id1);
                pst.setString(1, "Annulée");
               

               int rowCount = pst.executeUpdate();
               if (rowCount == 1) {
                JOptionPane.showMessageDialog(this, "Commande a été annulée");
                clearCmdTable();
                getCommandFromDatabase();
                } else {
                    JOptionPane.showMessageDialog(this, "Error while loading");
                }

        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        
        } 
      
        else if(etat1.equals("Annulée")){
             JOptionPane.showMessageDialog(this, "Cette commande à été dèja annulée");
       }
      
        else{
             JOptionPane.showMessageDialog(this, "Cette commande à été dèja confirmée");
        }
    }

     
     public void searchCommande(String str){
         
            DefaultTableModel model1 = (DefaultTableModel)tbl_cmdAgent.getModel();
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model1);

            tbl_cmdAgent.setRowSorter(trs);
            trs.setRowFilter(RowFilter.regexFilter(str));  
    }
     
     
     

    /**
     * *********************************************************************************************************
     */
    // Traitement du compte
    
    
    public int getAdminId(){
        
        Integer id1 = 0;
        try {
            ResultSet rs = null;
            Connection connection = null;
            Statement st = null;
                           
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            st = connection.createStatement();
            String sql = "SELECT * FROM `responsable` WHERE Nom = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, txt_nom.getText());
            rs = st.executeQuery(sql);
            
            JOptionPane.showMessageDialog(this, id1);
            while(rs.next()){
                id1 = (int)rs.getInt("ID");
            }
            
            
             return id1;
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MyAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id1;
    }
  
    
    
    public void getInfoFromDatabase() {

        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql = "select * from responsable";
            PreparedStatement pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                String nom = resultSet.getString("Nom");
                String prenom = resultSet.getString("Prenom");
                String cin = resultSet.getString("CIN");
                Date datNaiss = resultSet.getDate("DateNaiss");
                String lieuNaiss = resultSet.getString("LieuNaiss");
                String genre = resultSet.getString("Genre");
                String email = resultSet.getString("Email");
                String situation = resultSet.getString("Situation_sociale");
                String password = resultSet.getString("Password");
                String tel = resultSet.getString("Numero");
                

                txt_nom.setText(nom);
                txt_prenom.setText(prenom);
                txt_cin.setText(cin);
                txt_dateNaiss.setDate(datNaiss);
                txt_lieuNaiss.setText(lieuNaiss);
                
                cmb_genre.addItem("Homme");
                cmb_genre.addItem("Femme");
                
                cmb_genre.setSelectedItem(genre);
                
                txt_email.setText(email);
                
                cmb_situation.addItem("Célibataire");
                cmb_situation.addItem("Marié");
                cmb_situation.setSelectedItem(situation);
                
                txt_mdp.setText(password);
                txt_tel.setText(tel);
               
            }

        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    
    public void mettreAJourInfo(){
    
        try {
            Connection connection = null;
            Statement statement = null;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();

            String sql = "update responsable set Nom = ?, Prenom = ? , CIN = ?, DateNaiss = ?, LieuNaiss = ?, Genre = ?, Situation_sociale = ?, Email = ?, Password = ?, Numero = ? where ID = ?";

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
            pst.setString(9, txt_mdp.getText());  
            pst.setString(10, txt_tel.getText());
            pst.setInt(11, getAdminId());
 
            int rowCount = pst.executeUpdate();
            
            
            if (rowCount == 1) {
                JOptionPane.showMessageDialog(this, "Updated succesfully");
            } else {
                JOptionPane.showMessageDialog(this, "Updation failed");
            }
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }

    
    }

    /**
     * ****************************************************************************************************
     */
// Traitement des notes 
    Date date;
    String titre, note;
    
    
     public int getNoteId(){
            ResultSet rs = null;
            Connection connection = null;
            Statement st = null;
           try{
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, "root", "");
                st = connection.createStatement();
                String sql = "select max(ID) from note";
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

    public boolean noteValidation() {

        titre = txt_titre.getText();
        note = txt_note.getText();

        if (titre.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter a title");
            return false;
        }

        if (note.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter a note");
            return false;
        }
        return true;
    }

    public void getSelectedNote() {
        int rowNo = tbl_note.getSelectedRow();
        TableModel model = tbl_note.getModel();

        try {
            
            String dateValue = model.getValueAt(rowNo, 1).toString();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
            txt_date.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(MyAccount.class.getName()).log(Level.SEVERE, null, ex);
        }

        txt_titre.setText((String) model.getValueAt(rowNo, 2));
        txt_note.setText((String) model.getValueAt(rowNo, 3));

    }

    public void clearNoteTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_note.getModel();
        model.setRowCount(0);
    }

    public void deleteNote() {

        try {
            Connection connection = null;
            Statement statement = null;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();

            String sql = "delete from note where ID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            
            int rowNo = tbl_note.getSelectedRow();
            TableModel model1 = tbl_note.getModel();

            Integer id =  Integer.parseInt(model1.getValueAt(rowNo, 0).toString());
            pst.setInt(1,id);
            
            
            int rowCount = pst.executeUpdate();

            if (rowCount == 1) {
                JOptionPane.showMessageDialog(this, "Note deleted succesfully");
                clearNoteTable();
                setRecordsToNoteTable();
            } else {
                JOptionPane.showMessageDialog(this, "Note deletion failed");
            }
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }

    }

    public void setRecordsToNoteTable() {

        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql = "select * from note";
            PreparedStatement pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("ID");
                Date date = resultSet.getDate("Date");
                String titre = resultSet.getString("Titre");
                String note = resultSet.getString("Note");

                Object[] obj = {id, date, titre, note};

                DefaultTableModel model = (DefaultTableModel) tbl_note.getModel();
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

    public void addToNoteTables() {

        java.sql.Date date = new java.sql.Date(txt_date.getDate().getTime());
        String titre = txt_titre.getText();
        String note = txt_note.getText();
        int id = getNoteId();
        
        Object[] obj = {id, date, titre, note};

        DefaultTableModel model = (DefaultTableModel) tbl_note.getModel();
        model.addRow(obj);
    }

    public void addNoteToDatabase() {
        try {
            Connection connection = null;
            Statement statement = null;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();

            String sql = "insert into note(ID, Date, Titre,Note) VALUES (?,?,?,?)";

            PreparedStatement pst = connection.prepareStatement(sql);
            
            pst.setInt(1, getNoteId());
            // java.util.Date date_util = new java.util.Date();
            java.sql.Date date_sql = new java.sql.Date(txt_date.getDate().getTime());
            pst.setDate(2, date_sql);
            pst.setString(3, txt_titre.getText());
            pst.setString(4, txt_note.getText());

            int rowCount = pst.executeUpdate();
            if (rowCount == 1) {
                JOptionPane.showMessageDialog(this, "Note added succesfully");
            } else {
                JOptionPane.showMessageDialog(this, "Note insertion failed");
            }
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public void actualiserNote() {
        txt_date.setCalendar(null);
        txt_titre.setText("");
        txt_note.setText("");
    }

    /**
     * *************************************************************************************************************
     */
// Traitement de stock
    Integer id, quantite;
    String designation, frequence, type;

    public void addToJComboBox() {

        cmb_typeArticle.addItem("All");
        
        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql = "select * from categorie";
            PreparedStatement pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                cmb_typeArticle.addItem(resultSet.getString("Nom"));
                cmb_type.addItem(resultSet.getString("Nom"));
            }

        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public void viderJComboBox() {
        cmb_typeArticle.removeAllItems();
        cmb_type.removeAllItems();
    }

    public void getSelectedArticle() {
        int rowNo = tbl_stock.getSelectedRow();
        TableModel model = tbl_stock.getModel();

        txt_id.setText(model.getValueAt(rowNo, 0).toString());
        txt_designation.setText((String) model.getValueAt(rowNo, 1));
        txt_quantite.setText(model.getValueAt(rowNo, 2).toString());
        txt_frequence.setText(model.getValueAt(rowNo, 3).toString());
        txt_type.setText(model.getValueAt(rowNo, 4).toString());

    }

    public boolean validationArticle() {

        if (txt_id.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "please enter an id");
            return false;
        }

        if (txt_designation.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "please enter desig");
            return false;
        }

        if (!txt_quantite.getText().isEmpty() && txt_quantite.getText().matches("\\d+")) {
            id = Integer.parseInt(txt_quantite.getText());
        } else {
            JOptionPane.showMessageDialog(this, "please enter an id");
            return false;
        }

        if (txt_frequence.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter designation");
            return false;
        }

        if (txt_type.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter designation");
            return false;
        }

        return true;
    }

    public void setRecordArticleTable() {
        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql = "select * from article";
            PreparedStatement pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {

                id = Integer.parseInt(resultSet.getString("ID"));
                designation = resultSet.getString("Désignation");
                quantite = Integer.parseInt(resultSet.getString("Quantité"));
                frequence = resultSet.getString("Fréquence");
                type = resultSet.getString("Type");

                Object[] obj = {id, designation, quantite, frequence, type};

                DefaultTableModel model = (DefaultTableModel) tbl_stock.getModel();
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

    public void addToArticleToTable() {

        id = Integer.parseInt(txt_id.getText());
        designation = txt_designation.getText();
        quantite = Integer.parseInt(txt_quantite.getText());
        frequence = txt_frequence.getText();
        type = txt_type.getText();

        Object[] obj = {id, designation, quantite, frequence, type};

        DefaultTableModel model = (DefaultTableModel) tbl_stock.getModel();
        model.addRow(obj);
    }

    public void actualiserStock() {
        txt_id.setText("");
        txt_designation.setText("");
        txt_quantite.setText("");
        txt_frequence.setText("");
        txt_type.setText("");
    }

    public void addArticleToDatabase() {
        try {
            Connection connection = null;
            Statement statement = null;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();

            String sql = "insert into article(ID, Désignation, Quantité, Fréquence, Type) VALUES (?,?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txt_id.getText()));
            pst.setString(2, txt_designation.getText());
            pst.setInt(3, Integer.parseInt(txt_quantite.getText()));

            if (txt_frequence.getText().equals("")) {
                pst.setString(4, "");
            } else {
                pst.setString(4, txt_frequence.getText());
            }

            pst.setString(5, txt_type.getText());
            int rowCount = pst.executeUpdate();

            if (rowCount == 1) {
                JOptionPane.showMessageDialog(this, "Product added succesfully");
            } else {
                JOptionPane.showMessageDialog(this, "Product insertion failed");
            }
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public void clearArticleTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_stock.getModel();
        model.setRowCount(0);
    }
    

    public void deleteArticle() {
                
        DefaultTableModel model = (DefaultTableModel) tbl_stock.getModel();
        int selectedRow = tbl_stock.getSelectedRow();
        
        if(selectedRow == -1){
             JOptionPane.showMessageDialog(this, "Please select product");
        }else{

            try {
                Connection connection = null;
                Statement statement = null;

                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, "root", "");
                statement = connection.createStatement();

                String sql = "delete from article where ID = ?";

                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(txt_id.getText()));

                int rowCount = pst.executeUpdate();
                if (rowCount == 1) {
                    JOptionPane.showMessageDialog(this, "Product deleted succesfully");
                    clearArticleTable();
                    setRecordArticleTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Product deletion failed");
                }
            } catch (ClassNotFoundException classNotFound) {
                classNotFound.printStackTrace();
                System.exit(1);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                System.exit(1);
            }
        }
    }
 
    public void updateArticle() {

        id = Integer.parseInt(txt_id.getText());
        designation = txt_designation.getText();
        quantite = Integer.parseInt(txt_quantite.getText());
        frequence = txt_frequence.getText();
        type = txt_type.getText();

        try {
            Connection connection = null;
            Statement statement = null;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();

            String sql = "update article set Désignation = ?, Quantité = ?, Fréquence = ?,  Type = ? where ID = ?";

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, designation);
            pst.setInt(2, quantite);
            pst.setString(3, frequence);
            pst.setString(4, type);
            pst.setInt(5,id);

            int rowCount = pst.executeUpdate();
            if (rowCount == 1) {
                JOptionPane.showMessageDialog(this, "Product Updated succesfully");
                clearArticleTable();
                setRecordArticleTable();
            } else {
                JOptionPane.showMessageDialog(this, "Product updation failed");
            }
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }

    }
    

    public void fromJComBoxToTextField() {

        txt_type.setText(cmb_type.getSelectedItem().toString());
    }

    public boolean getInfo() {

        ResultSet rs = null;
        Connection connection = null;
        Statement st = null;

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            st = connection.createStatement();
            String sql = "select * from article";
            rs = st.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt(1);
                designation = rs.getString(2);

                if (id == Integer.parseInt(txt_id.getText())) {
                    return true;
                } else if (designation.equals(txt_designation.getText())) {
                    return true;
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;

    }

    public void getArticleFromDatabase() {

        if (!cmb_typeArticle.getSelectedItem().equals("All")) {

            try {
                Connection connection = null;
                Statement statement = null;
                ResultSet rs = null;

                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, "root", "");
                statement = connection.createStatement();
                String sql;

                sql = "select * from article Where Type = ?";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, cmb_typeArticle.getSelectedItem().toString().trim());
                rs = pst.executeQuery();

                while (rs.next()) {
                    id = Integer.parseInt(rs.getString("ID"));
                    designation = rs.getString("Désignation");
                    quantite = Integer.parseInt(rs.getString("Quantité"));
                    frequence = rs.getString("Fréquence");
                    type = rs.getString("Type");
                    Object[] obj = {id, designation, quantite, frequence, type};

                    DefaultTableModel model = (DefaultTableModel) tbl_stock.getModel();
                    model.addRow(obj);
                }

            } catch (ClassNotFoundException classNotFound) {
                classNotFound.printStackTrace();
                System.exit(1);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                System.exit(1);
            }

        } else {
            clearArticleTable();
            setRecordArticleTable();
        }
    }

    public void searchArticle(String str) {

        DefaultTableModel model1 = (DefaultTableModel) tbl_stock.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model1);
        tbl_stock.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));

    }
    
    
    
  /**************************************************************************************************************/
    
    // Gestion des agents

      
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
                
                
                Object[] obj = {nom, prenom, cin, date_Naiss, lieu_Naiss, genre, situation, email, password,numero};
                
                DefaultTableModel model = (DefaultTableModel)tbl_Agent.getModel();
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
        
         
            DefaultTableModel model1 = (DefaultTableModel)tbl_Agent.getModel();
            int selectedRowIndex = tbl_Agent.getSelectedRow(); 
            
            if(selectedRowIndex == -1){
                JOptionPane.showMessageDialog(this, "Please select agent");
            }else{
        
              try {
                    Connection connection = null;
                    Statement statement = null;
                    ResultSet rs = null;
                    ResultSet rs1 = null;

                    Class.forName(JDBC_DRIVER);
                    connection = DriverManager.getConnection(DATABASE_URL, "root", "");
                    statement = connection.createStatement();

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
    }
   
    
    public void searchAgent(String str){
        
        DefaultTableModel model = (DefaultTableModel)tbl_Agent.getModel();
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
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        pnl_titre = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        lbl_dateOfDay = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnl_welcome = new javax.swing.JPanel();
        pnl_info = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_cin = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_lieuNaiss = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_prenom = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_mdp = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txt_dateNaiss = new com.toedter.calendar.JDateChooser();
        txt_nom = new javax.swing.JTextField();
        cmb_genre = new javax.swing.JComboBox<>();
        txt_tel = new javax.swing.JFormattedTextField();
        cmb_situation = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_date = new com.toedter.calendar.JDateChooser();
        txt_titre = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_note = new javax.swing.JTextArea();
        btn_ajouter = new javax.swing.JButton();
        btn_supprimer = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_note = new javax.swing.JTable();
        btn_actualiser1 = new javax.swing.JButton();
        pnl_stock = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_stock = new javax.swing.JTable();
        cmb_typeArticle = new javax.swing.JComboBox<>();
        txt_search = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_designation = new javax.swing.JTextField();
        txt_type = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        lbl_rechercher = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmb_type = new javax.swing.JComboBox<>();
        txt_frequence = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txt_quantite = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        Chercher = new javax.swing.JButton();
        pnl_cmmande = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        pnl_cmmande1 = new javax.swing.JPanel();
        cmd_agent = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cmb_choix = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_cmdAgent = new javax.swing.JTable();
        txt_searchArticle = new javax.swing.JTextField();
        lbl_rechercher1 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        pnl_cmmande2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_cmdResp = new javax.swing.JTable();
        txt_searchResp = new javax.swing.JTextField();
        lbl_rechercher3 = new javax.swing.JLabel();
        btn_searchResp = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_cmd = new javax.swing.JTable();
        jSeparator9 = new javax.swing.JSeparator();
        jButton8 = new javax.swing.JButton();
        pnl_agent = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lbl_rechercher2 = new javax.swing.JLabel();
        txt_rechercher = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_Agent = new javax.swing.JTable();
        btn_supp = new javax.swing.JButton();
        btn_email = new javax.swing.JButton();
        btn_ajt = new javax.swing.JButton();
        pnl_bar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();

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
        panelSidebar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\admin.png")); // NOI18N
        jLabel9.setText(" Compte");
        jLabel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
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
        panelSidebar.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 40, 210, 60));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\list_1.png")); // NOI18N
        jLabel10.setText("Commande");
        jLabel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
        });
        panelSidebar.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 140, 210, 60));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\stocks_64px.png")); // NOI18N
        jLabel11.setText("Stocks");
        jLabel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });
        panelSidebar.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 240, 210, 60));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\client_management_64px.png")); // NOI18N
        jLabel25.setText("Agents");
        jLabel25.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel25MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel25MouseExited(evt);
            }
        });
        panelSidebar.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 340, 210, 60));

        getContentPane().add(panelSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 220, 650));

        pnl_titre.setBackground(new java.awt.Color(0, 153, 153));
        pnl_titre.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\Capture.PNG")); // NOI18N
        pnl_titre.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 0, 190, 140));

        lbl_username.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        lbl_username.setForeground(new java.awt.Color(255, 255, 255));
        pnl_titre.add(lbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 200, 40));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Vectra Star (Sous-Traitant OCP)");
        pnl_titre.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 600, 40));

        jLabel18.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\microsoft_admin_80px.png")); // NOI18N
        pnl_titre.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 80, 80));

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnl_titre.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 10, 140));

        lbl_dateOfDay.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbl_dateOfDay.setForeground(new java.awt.Color(255, 255, 255));
        pnl_titre.add(lbl_dateOfDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 170, 30));

        jLabel35.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\less_than_30px.png")); // NOI18N
        pnl_titre.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 12, 30, 30));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Application HSE");
        pnl_titre.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 290, 50));
        pnl_titre.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 310, 10));
        pnl_titre.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 110, 560, 10));

        getContentPane().add(pnl_titre, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1620, 140));

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));

        pnl_welcome.setBackground(new java.awt.Color(204, 204, 204));
        pnl_welcome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("tab2", pnl_welcome);

        pnl_info.setBackground(new java.awt.Color(204, 204, 204));
        pnl_info.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\edit_property_40px.png")); // NOI18N
        jLabel3.setText("  Mes informations personnelles");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
        });
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 350, 60));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\note_40px.png")); // NOI18N
        jLabel4.setText("  Mes notes professionnelles");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 350, 60));
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 1390, 20));

        pnl_info.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1390, 200));

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane2.addTab("tab2", jPanel6);

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Prénom");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 140, 30));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("CIN");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 140, 30));

        txt_cin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cinActionPerformed(evt);
            }
        });
        jPanel5.add(txt_cin, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 220, 30));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Date de naissance");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 160, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Lieu de naissance");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 160, 30));

        txt_lieuNaiss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_lieuNaissActionPerformed(evt);
            }
        });
        jPanel5.add(txt_lieuNaiss, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, 220, 30));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("Nom");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 140, 30));

        txt_prenom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_prenomActionPerformed(evt);
            }
        });
        jPanel5.add(txt_prenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 220, 30));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setText("Genre");
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 50, 140, 30));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("Mot de passe");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 250, 140, 30));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Situation sociale");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, 140, 30));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Téléphone");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 310, 140, 30));

        txt_mdp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_mdpActionPerformed(evt);
            }
        });
        jPanel5.add(txt_mdp, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 250, 220, 30));

        jButton1.setBackground(new java.awt.Color(0, 51, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Mettre à jour");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 390, 170, 50));
        jPanel5.add(txt_dateNaiss, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 220, 30));

        txt_nom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nomActionPerformed(evt);
            }
        });
        jPanel5.add(txt_nom, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 220, 30));

        jPanel5.add(cmb_genre, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 50, 220, 30));

        txt_tel.setColumns(10);
        try {
            txt_tel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-##-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_tel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telActionPerformed(evt);
            }
        });
        jPanel5.add(txt_tel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 310, 220, 30));

        jPanel5.add(cmb_situation, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 110, 220, 30));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("E-mail");
        jPanel5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 180, 140, 30));

        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });
        jPanel5.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 180, 220, 30));

        jTabbedPane2.addTab("tab1", jPanel5);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel28.setText("Note : ");
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 80, 30));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel29.setText("Date :");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 70, 30));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel30.setText("Titre :");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 70, 30));
        jPanel7.add(txt_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 200, 30));

        txt_titre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_titreActionPerformed(evt);
            }
        });
        jPanel7.add(txt_titre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 200, 30));

        txt_note.setColumns(20);
        txt_note.setRows(5);
        jScrollPane1.setViewportView(txt_note);

        jPanel7.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 200, 110));

        btn_ajouter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ajouter.setText("Ajouter");
        btn_ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ajouterActionPerformed(evt);
            }
        });
        jPanel7.add(btn_ajouter, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 100, 30));

        btn_supprimer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_supprimer.setText("Supprimer");
        btn_supprimer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_supprimerMouseClicked(evt);
            }
        });
        btn_supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_supprimerActionPerformed(evt);
            }
        });
        jPanel7.add(btn_supprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 380, 100, 30));

        tbl_note.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date", "Titre", "Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_note.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_noteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_note);
        if (tbl_note.getColumnModel().getColumnCount() > 0) {
            tbl_note.getColumnModel().getColumn(0).setMinWidth(100);
            tbl_note.getColumnModel().getColumn(0).setMaxWidth(200);
            tbl_note.getColumnModel().getColumn(1).setMinWidth(150);
            tbl_note.getColumnModel().getColumn(1).setMaxWidth(300);
            tbl_note.getColumnModel().getColumn(2).setMinWidth(150);
            tbl_note.getColumnModel().getColumn(2).setMaxWidth(300);
        }

        jPanel7.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 770, 340));

        btn_actualiser1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_actualiser1.setText("Actualiser");
        btn_actualiser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_actualiser1MouseClicked(evt);
            }
        });
        btn_actualiser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualiser1ActionPerformed(evt);
            }
        });
        jPanel7.add(btn_actualiser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 100, 30));

        jTabbedPane2.addTab("tab3", jPanel7);

        pnl_info.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 1390, 640));

        jTabbedPane1.addTab("tab1", pnl_info);

        pnl_stock.setBackground(new java.awt.Color(204, 204, 204));
        pnl_stock.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnl_stock.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1390, 10));

        tbl_stock.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_stock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Désignation", "Quantité", "Fréquence de dotation", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_stock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_stockMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_stock);
        if (tbl_stock.getColumnModel().getColumnCount() > 0) {
            tbl_stock.getColumnModel().getColumn(0).setMinWidth(50);
            tbl_stock.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbl_stock.getColumnModel().getColumn(0).setMaxWidth(100);
            tbl_stock.getColumnModel().getColumn(1).setMinWidth(100);
            tbl_stock.getColumnModel().getColumn(1).setMaxWidth(200);
            tbl_stock.getColumnModel().getColumn(2).setMinWidth(60);
            tbl_stock.getColumnModel().getColumn(2).setMaxWidth(150);
            tbl_stock.getColumnModel().getColumn(3).setMinWidth(100);
            tbl_stock.getColumnModel().getColumn(3).setMaxWidth(200);
            tbl_stock.getColumnModel().getColumn(4).setMinWidth(200);
            tbl_stock.getColumnModel().getColumn(4).setMaxWidth(400);
        }

        pnl_stock.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 820, 410));

        cmb_typeArticle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_typeArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_typeArticleActionPerformed(evt);
            }
        });
        pnl_stock.add(cmb_typeArticle, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 560, 40));

        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });
        pnl_stock.add(txt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 130, 250, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Type");
        pnl_stock.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 410, 150, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("ID");
        pnl_stock.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 210, 80, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Désignation");
        pnl_stock.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 260, 130, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Quantité");
        pnl_stock.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 310, 80, 30));

        txt_id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnl_stock.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 210, 300, 30));

        txt_designation.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnl_stock.add(txt_designation, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 260, 300, 30));

        txt_type.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnl_stock.add(txt_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 460, 300, 30));

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton2.setText("Vider");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pnl_stock.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 510, 100, 30));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton3.setText("Ajouter");
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
        pnl_stock.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 510, 100, 30));

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton4.setText("Modifier");
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
        pnl_stock.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 510, 100, 30));

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton5.setText("Supprimer");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        pnl_stock.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 510, 110, 30));

        lbl_rechercher.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\search_45px.png")); // NOI18N
        pnl_stock.add(lbl_rechercher, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 120, 60, 50));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\scan_stock_50px.png")); // NOI18N
        jLabel13.setText("Visualiser le stock");
        pnl_stock.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 290, 40));

        cmb_type.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_typeActionPerformed(evt);
            }
        });
        pnl_stock.add(cmb_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 410, 300, 30));

        txt_frequence.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnl_stock.add(txt_frequence, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 360, 300, 30));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setText("Fréq de dotation");
        pnl_stock.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 360, 170, 30));

        txt_quantite.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnl_stock.add(txt_quantite, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 310, 300, 30));

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton6.setText("Faire une demande de dotation");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        pnl_stock.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 580, 560, 50));

        Chercher.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Chercher.setText("Search");
        Chercher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChercherMouseClicked(evt);
            }
        });
        pnl_stock.add(Chercher, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 130, 110, 30));

        jTabbedPane1.addTab("tab3", pnl_stock);

        pnl_cmmande.setBackground(new java.awt.Color(204, 204, 204));
        pnl_cmmande.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane3.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_cmmande1.setBackground(new java.awt.Color(204, 204, 204));
        pnl_cmmande1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmd_agent.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmd_agent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_agentActionPerformed(evt);
            }
        });
        pnl_cmmande1.add(cmd_agent, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 240, 270, 40));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setText("Agents");
        pnl_cmmande1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 240, 110, 40));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setText("Etats");
        pnl_cmmande1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 170, 160, 40));

        cmb_choix.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_choix.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Toutes les commandes", "Les commandes en cours", "Les commandes confirmée", "Les commandes annulée" }));
        cmb_choix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_choixActionPerformed(evt);
            }
        });
        pnl_cmmande1.add(cmb_choix, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 170, 270, 40));

        tbl_cmdAgent.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_cmdAgent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Agent", "Date", "Type", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_cmdAgent.setRowHeight(20);
        jScrollPane4.setViewportView(tbl_cmdAgent);

        pnl_cmmande1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 140, 750, 370));
        pnl_cmmande1.add(txt_searchArticle, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 610, 40));

        lbl_rechercher1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newpackage/my icons/search2.png"))); // NOI18N
        pnl_cmmande1.add(lbl_rechercher1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 110, 80));

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton7.setText("Search");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        pnl_cmmande1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 320, 200, 50));

        jButton10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jButton10.setText("Search");
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton10MouseClicked(evt);
            }
        });
        pnl_cmmande1.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 50, 120, 40));

        jButton11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton11.setText("Annuler");
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton11MouseClicked(evt);
            }
        });
        pnl_cmmande1.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 540, 200, 50));

        jButton12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton12.setText("Confirmer");
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton12MouseClicked(evt);
            }
        });
        pnl_cmmande1.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 540, 200, 50));
        pnl_cmmande1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 140, 550, 20));

        jPanel2.add(pnl_cmmande1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane3.addTab("Les commandes des agents", jPanel2);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_cmmande2.setBackground(new java.awt.Color(204, 204, 204));
        pnl_cmmande2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_cmdResp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_cmdResp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Marché", "N°", "Site", "Date", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_cmdResp.setRowHeight(20);
        tbl_cmdResp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_cmdRespMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_cmdResp);
        if (tbl_cmdResp.getColumnModel().getColumnCount() > 0) {
            tbl_cmdResp.getColumnModel().getColumn(0).setMinWidth(100);
            tbl_cmdResp.getColumnModel().getColumn(0).setMaxWidth(300);
            tbl_cmdResp.getColumnModel().getColumn(2).setMinWidth(100);
            tbl_cmdResp.getColumnModel().getColumn(2).setMaxWidth(200);
            tbl_cmdResp.getColumnModel().getColumn(4).setMinWidth(100);
            tbl_cmdResp.getColumnModel().getColumn(4).setMaxWidth(300);
            tbl_cmdResp.getColumnModel().getColumn(5).setMinWidth(100);
            tbl_cmdResp.getColumnModel().getColumn(5).setMaxWidth(200);
        }

        pnl_cmmande2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 660, 390));
        pnl_cmmande2.add(txt_searchResp, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, 610, 40));

        lbl_rechercher3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newpackage/my icons/search2.png"))); // NOI18N
        pnl_cmmande2.add(lbl_rechercher3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 110, 80));

        btn_searchResp.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btn_searchResp.setText("Search");
        btn_searchResp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_searchRespMouseClicked(evt);
            }
        });
        pnl_cmmande2.add(btn_searchResp, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 50, 120, 40));
        pnl_cmmande2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 1320, 20));

        jScrollPane7.setBackground(new java.awt.Color(255, 255, 255));

        tbl_cmd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_cmd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Désignation", "Quantité", "Destination", "OBS"
            }
        ));
        tbl_cmd.setRowHeight(30);
        jScrollPane7.setViewportView(tbl_cmd);

        pnl_cmmande2.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 150, 580, 390));

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnl_cmmande2.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, 40, 420));

        jPanel1.add(pnl_cmmande2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton8.setText("Imprimer");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 560, 170, 40));

        jTabbedPane3.addTab("Mes commandes de dotation", jPanel1);

        pnl_cmmande.add(jTabbedPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 3, 1320, 650));

        jTabbedPane1.addTab("tab4", pnl_cmmande);

        pnl_agent.setBackground(new java.awt.Color(204, 204, 204));
        pnl_agent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(204, 204, 204));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_rechercher2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newpackage/my icons/search2.png"))); // NOI18N
        jPanel11.add(lbl_rechercher2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 110, 70));

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
        jPanel11.add(txt_rechercher, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 390, 40));

        jButton9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton9.setText("Search");
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton9MouseClicked(evt);
            }
        });
        jPanel11.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 120, 40));

        jPanel14.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 980, 120));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel14.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-150, 122, 1220, 10));

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_Agent.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_Agent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom", "Prénom", "CIN", "Date de naissance ", "Lieu de naissance", "Genre", "Situation sociale", "Email", "Mot de passe ", "Numéro de téléphone"
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
        jScrollPane5.setViewportView(tbl_Agent);
        if (tbl_Agent.getColumnModel().getColumnCount() > 0) {
            tbl_Agent.getColumnModel().getColumn(0).setMinWidth(100);
            tbl_Agent.getColumnModel().getColumn(0).setMaxWidth(200);
            tbl_Agent.getColumnModel().getColumn(1).setMinWidth(100);
            tbl_Agent.getColumnModel().getColumn(1).setMaxWidth(200);
            tbl_Agent.getColumnModel().getColumn(2).setMinWidth(100);
            tbl_Agent.getColumnModel().getColumn(2).setMaxWidth(200);
            tbl_Agent.getColumnModel().getColumn(7).setMinWidth(200);
            tbl_Agent.getColumnModel().getColumn(7).setMaxWidth(400);
        }

        jPanel12.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1240, 320));

        jPanel14.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 1260, 370));

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
        jPanel14.add(btn_supp, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 580, 220, 50));

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
        jPanel14.add(btn_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 510, 610, 50));

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
        jPanel14.add(btn_ajt, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 580, 220, 50));

        pnl_agent.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 1300, 650));

        jTabbedPane1.addTab("tab5", pnl_agent);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 1400, 680));

        pnl_bar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\about_50pxAA.png")); // NOI18N
        jLabel8.setText(" About");
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
        pnl_bar.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1370, 0, 170, 50));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 102));
        jLabel33.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\logout_rounded_up_50px.png")); // NOI18N
        jLabel33.setText("Déconnexion");
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel33MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel33MouseExited(evt);
            }
        });
        pnl_bar.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 50));

        getContentPane().add(pnl_bar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 790, 1540, 60));

        setSize(new java.awt.Dimension(1550, 885));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        copyRight copy = new copyRight();
        copy.show();
        this.dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseEntered

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseExited

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        jTabbedPane1.setSelectedIndex(3);
        clearCmdTable();
        getCommandFromDatabase();
        
        clearCmdRespTable();
        getCommandRespFromDatabase();
        
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseEntered

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseExited

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked

        jTabbedPane1.setSelectedIndex(2);
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(0, 0, 128));
        headerRenderer.setForeground(Color.WHITE);

        for (int i = 0; i < tbl_stock.getModel().getColumnCount(); i++) {
            tbl_stock.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        actualiserStock();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseExited

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        jTabbedPane2.setSelectedIndex(1);

    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        jTabbedPane2.setSelectedIndex(2);

    }//GEN-LAST:event_jLabel4MouseClicked

    private void txt_cinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cinActionPerformed

    private void txt_lieuNaissActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_lieuNaissActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_lieuNaissActionPerformed

    private void txt_prenomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_prenomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_prenomActionPerformed

    private void txt_mdpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_mdpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_mdpActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
            jTabbedPane1.setSelectedIndex(4);
            clearAgentTable();
            setAgentRecordsToTable();
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jLabel25MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel25MouseEntered

    private void jLabel25MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel25MouseExited

    private void btn_ajouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ajouterActionPerformed
        if (noteValidation()) {
            addToNoteTables();
            addNoteToDatabase();
        }
    }//GEN-LAST:event_btn_ajouterActionPerformed

    private void btn_supprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supprimerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_supprimerActionPerformed

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered

    }//GEN-LAST:event_jLabel3MouseEntered

    private void txt_nomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nomActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

       mettreAJourInfo();
    }//GEN-LAST:event_jButton1MouseClicked

    private void btn_supprimerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_supprimerMouseClicked
        deleteNote();
    }//GEN-LAST:event_btn_supprimerMouseClicked

    private void txt_titreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_titreActionPerformed
        txt_note.requestFocusInWindow();
    }//GEN-LAST:event_txt_titreActionPerformed

    private void tbl_stockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_stockMouseClicked
        getSelectedArticle();
    }//GEN-LAST:event_tbl_stockMouseClicked

    private void btn_actualiser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_actualiser1MouseClicked
        actualiserNote();
    }//GEN-LAST:event_btn_actualiser1MouseClicked

    private void btn_actualiser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualiser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_actualiser1ActionPerformed

    private void tbl_noteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_noteMouseClicked
        getSelectedNote();
    }//GEN-LAST:event_tbl_noteMouseClicked

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        if (validationArticle()) {

            if (getInfo()) {
                JOptionPane.showMessageDialog(this, "This product already exists. \nYou can edit it", "Alert", JOptionPane.WARNING_MESSAGE);
            } else {
                addToArticleToTable();
                addArticleToDatabase();
            }
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        actualiserStock();
    }//GEN-LAST:event_jButton2MouseClicked

    private void cmb_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_typeActionPerformed
        fromJComBoxToTextField();
    }//GEN-LAST:event_cmb_typeActionPerformed

    private void cmb_typeArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_typeArticleActionPerformed
        clearArticleTable();
        getArticleFromDatabase();
    }//GEN-LAST:event_cmb_typeArticleActionPerformed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased

    }//GEN-LAST:event_txt_searchKeyReleased

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        Commande cmd = new Commande(lbl_username.getText());
        cmd.show();
        this.dispose();
    }//GEN-LAST:event_jButton6MouseClicked

    private void ChercherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChercherMouseClicked
        String searchString = txt_search.getText();
        searchArticle(searchString);
    }//GEN-LAST:event_ChercherMouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        Login_Form f = new Login_Form();
        f.show();
        this.dispose();
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel33MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel33MouseEntered

    private void jLabel33MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel33MouseExited

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
       if(validationArticle())
         updateArticle();
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        deleteArticle();
    }//GEN-LAST:event_jButton5MouseClicked

    private void txt_telActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void btn_ajtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ajtActionPerformed

    }//GEN-LAST:event_btn_ajtActionPerformed

    private void btn_ajtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ajtMouseClicked

        CreateAccount acc = new CreateAccount(lbl_username.getText());
        acc.show();
        this.dispose();
    }//GEN-LAST:event_btn_ajtMouseClicked

    private void btn_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_emailActionPerformed

    private void btn_emailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_emailMouseClicked

        DefaultTableModel model1 = (DefaultTableModel)tbl_Agent.getModel();
        int selectedRowIndex = tbl_Agent.getSelectedRow();
        if( selectedRowIndex == -1 ){
            JOptionPane.showMessageDialog(this, "Please select an agent");

        }else{

            String email;
            email = model1.getValueAt(selectedRowIndex, 7).toString().trim();

            VerificationEmail aa = new VerificationEmail(lbl_username.getText(), email);
            aa.show();
            this.dispose();
        }
    }//GEN-LAST:event_btn_emailMouseClicked

    private void btn_suppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suppActionPerformed

    }//GEN-LAST:event_btn_suppActionPerformed

    private void btn_suppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_suppMouseClicked
        deleteAgent();
    }//GEN-LAST:event_btn_suppMouseClicked

    private void tbl_AgentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_AgentMouseClicked

    }//GEN-LAST:event_tbl_AgentMouseClicked

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
        String searchString = txt_rechercher.getText();
        searchAgent(searchString);
    }//GEN-LAST:event_jButton9MouseClicked

    private void txt_rechercherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rechercherKeyReleased

    }//GEN-LAST:event_txt_rechercherKeyReleased

    private void txt_rechercherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rechercherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rechercherActionPerformed

    private void jButton10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseClicked
        String searchString = txt_searchArticle.getText();
        searchCommande(searchString);
    }//GEN-LAST:event_jButton10MouseClicked

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseClicked
        annulerCommmande();
    }//GEN-LAST:event_jButton11MouseClicked

    private void jButton12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseClicked
        confirmerCommmande();
    }//GEN-LAST:event_jButton12MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        clearCmdTable();
        getCommandWantedFromDatabase();
    }//GEN-LAST:event_jButton7MouseClicked

    private void cmd_agentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_agentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmd_agentActionPerformed

    private void cmb_choixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_choixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_choixActionPerformed

    private void btn_searchRespMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_searchRespMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_searchRespMouseClicked

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        
         DefaultTableModel model1 = (DefaultTableModel)tbl_cmdResp.getModel();
         int selectedRowIndex = tbl_cmdResp.getSelectedRow();
         
         int id1 = (int) model1.getValueAt(selectedRowIndex, 0);
         
         PrintDoc doc = new PrintDoc(id1);
         doc.printDocument();
         
    }//GEN-LAST:event_jButton8MouseClicked

    private void tbl_cmdRespMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_cmdRespMouseClicked
          DefaultTableModel model1 = (DefaultTableModel)tbl_cmdResp.getModel();
          int selectedRowIndex = tbl_cmdResp.getSelectedRow();
          int id1 = (int) model1.getValueAt(selectedRowIndex, 0);
          
          DefaultTableModel model2 = (DefaultTableModel)tbl_cmd.getModel();
          model2.setRowCount(0);
          setRecordsArticleToTable(id1);
    }//GEN-LAST:event_tbl_cmdRespMouseClicked


    
/******************************************************************************************************/
    
public void setAgentVisible(){
      jTabbedPane1.setSelectedIndex(4);
      clearAgentTable();
      setAgentRecordsToTable();
} 
    

public void setStockVisible(){ 
    jTabbedPane1.setSelectedIndex(2);
    clearArticleTable();
    setRecordArticleTable(); 
    
}
    


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
            java.util.logging.Logger.getLogger(MyAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyAccount().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Chercher;
    private javax.swing.JButton btn_actualiser1;
    private javax.swing.JButton btn_ajouter;
    private javax.swing.JButton btn_ajt;
    private javax.swing.JButton btn_email;
    private javax.swing.JButton btn_searchResp;
    private javax.swing.JButton btn_supp;
    private javax.swing.JButton btn_supprimer;
    private javax.swing.JComboBox<String> cmb_choix;
    private javax.swing.JComboBox<String> cmb_genre;
    private javax.swing.JComboBox<String> cmb_situation;
    private javax.swing.JComboBox<String> cmb_type;
    private javax.swing.JComboBox<String> cmb_typeArticle;
    private javax.swing.JComboBox<String> cmd_agent;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lbl_dateOfDay;
    private javax.swing.JLabel lbl_rechercher;
    private javax.swing.JLabel lbl_rechercher1;
    private javax.swing.JLabel lbl_rechercher2;
    private javax.swing.JLabel lbl_rechercher3;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel pnl_agent;
    private javax.swing.JPanel pnl_bar;
    private javax.swing.JPanel pnl_cmmande;
    private javax.swing.JPanel pnl_cmmande1;
    private javax.swing.JPanel pnl_cmmande2;
    private javax.swing.JPanel pnl_info;
    private javax.swing.JPanel pnl_stock;
    private javax.swing.JPanel pnl_titre;
    private javax.swing.JPanel pnl_welcome;
    private javax.swing.JTable tbl_Agent;
    private javax.swing.JTable tbl_cmd;
    private javax.swing.JTable tbl_cmdAgent;
    private javax.swing.JTable tbl_cmdResp;
    private javax.swing.JTable tbl_note;
    private javax.swing.JTable tbl_stock;
    private javax.swing.JTextField txt_cin;
    private com.toedter.calendar.JDateChooser txt_date;
    private com.toedter.calendar.JDateChooser txt_dateNaiss;
    private javax.swing.JTextField txt_designation;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_frequence;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_lieuNaiss;
    private javax.swing.JTextField txt_mdp;
    private javax.swing.JTextField txt_nom;
    private javax.swing.JTextArea txt_note;
    private javax.swing.JTextField txt_prenom;
    private javax.swing.JTextField txt_quantite;
    private javax.swing.JTextField txt_rechercher;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_searchArticle;
    private javax.swing.JTextField txt_searchResp;
    private javax.swing.JFormattedTextField txt_tel;
    private javax.swing.JTextField txt_titre;
    private javax.swing.JTextField txt_type;
    // End of variables declaration//GEN-END:variables
}
