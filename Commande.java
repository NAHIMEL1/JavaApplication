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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author ADS
 */
public class Commande extends javax.swing.JFrame {

    /**
     * Creates new form MyAccount
     *
     */
    DefaultTableModel model;

    public Commande() {
        initComponents();
        // setExtendedState(MAXIMIZED_BOTH);
        txt_id.setText(Integer.toString(getId()));
    }
    
    
    public Commande(String User){
        initComponents();   
        lbl_user.setText(User);
        txt_id.setText(Integer.toString(getId()));
    }
    
/*****************************************************************************************************************/
    
    int id, quantite;  
    String designation, destination, obs, marche, num, site;
    Date date;
    
    public int getId(){
        ResultSet rs = null;
        Connection connection = null;
        Statement st = null;
       try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            st = connection.createStatement();
            String sql = "select max(ID) from commanderesponsable";
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
    
    
      public void addToTables() {
          
        destination = txt_destination.getText();
        designation = txt_designation.getText();
        obs = txt_obs.getText();
        quantite = Integer.parseInt(txt_quantite.getText());

        Object[] obj = {designation, quantite, destination, obs};

        model = (DefaultTableModel)tbl_cmdResp.getModel();
        model.addRow(obj);
        
      }
    
    
        public void clearTable() {
         DefaultTableModel model = (DefaultTableModel) tbl_cmdResp.getModel();
         model.setRowCount(0);
        }
    
    
        public void actualiser() {
        txt_designation.setText("");
        txt_quantite.setText("");
        txt_destination.setText("");
        txt_obs.setText("");
     }

        
     public void addArticleRespoDatabase() {
         
        try {
            Connection connection = null;
            Statement statement = null;
            
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            
            DefaultTableModel model1 = (DefaultTableModel)tbl_cmdResp.getModel();
            
            for(int i = 0  ; i< model1.getRowCount() ; i++ ){
                
                Integer id1 = Integer.valueOf(txt_id.getText());
                String designation1 = model1.getValueAt(i, 0).toString();
                Integer quantite1 = Integer.valueOf(model1.getValueAt(i, 1).toString());
                String destination1 = model1.getValueAt(i, 2).toString();
                String obs1 = model1.getValueAt(i, 3).toString();
                
                
                String sql = "insert into contientresp(ID, Désignation, Quantité,Destination, OBS) values (?,?,?,?,?)";
                PreparedStatement pst = connection.prepareStatement(sql);
               
                pst.setInt(1,id1);
                pst.setString(2, designation1);
                pst.setInt(3,quantite1);
                pst.setString(4,destination1);
                pst.setString(5,obs1);
                
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
     
      public void addCommandeRespToDatabase() {
          
        try {
            Connection connection = null;
            Statement statement = null;
            
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
    
            String sql = "insert into commanderesponsable(ID, Marché, Numéro, Site, Date) values (?,?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sql);
               
            pst.setInt(1,Integer.parseInt(txt_id.getText()));
            pst.setString(2, txt_marche.getText());
            pst.setString(3,txt_numero.getText());
            pst.setString(4,txt_site.getText());
            
            java.sql.Date date_sql = new java.sql.Date(txt_date.getDate().getTime());
            pst.setDate(5, date_sql);
           
            int rw = pst.executeUpdate();
            
            if(rw == 1){
                 JOptionPane.showMessageDialog(this, "Added successfully");
            }else{
                JOptionPane.showMessageDialog(this, "Failed");
            }
            
            
 
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
     
      public void fromTableToTxtField(){
        
        int rowNo = tbl_cmdResp.getSelectedRow();
        TableModel model1 = tbl_cmdResp.getModel();

        txt_designation.setText((String) model1.getValueAt(rowNo, 0));
        txt_quantite.setText(model1.getValueAt(rowNo, 1).toString());
        txt_destination.setText((String) model1.getValueAt(rowNo, 2).toString());
        txt_obs.setText((String) model1.getValueAt(rowNo, 3).toString());
        
    }
    
       public void updateRowSelected(){
        
         DefaultTableModel model1 = (DefaultTableModel)tbl_cmdResp.getModel();
         int selectedRowIndex = tbl_cmdResp.getSelectedRow();
         
         model1.setValueAt(txt_designation.getText(), selectedRowIndex, 0);
         model1.setValueAt(txt_quantite.getText(), selectedRowIndex, 1);
         model1.setValueAt(txt_destination.getText(), selectedRowIndex, 2);
         if(txt_obs.equals("")){
                 model1.setValueAt("", selectedRowIndex, 3);
         }else{
                 model1.setValueAt(txt_obs.getText(), selectedRowIndex, 3);
         }
    
        }
       
       
        
       public void deleteSelectedRow(){
        
        int numRows = tbl_cmdResp.getSelectedRows().length;
         if(numRows == 0){
           JOptionPane.showMessageDialog(this, "Table is empty", "Alert", JOptionPane.WARNING_MESSAGE);
         } else{
            
            for(int i=0; i<numRows ; i++ ) {
                     model.removeRow(tbl_cmdResp.getSelectedRow());
            }
         }
        }
   


    boolean validationArticle() {

        designation = txt_designation.getText();
        destination = txt_destination.getText();

        if (designation.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter designation");
            return false;
        }

       if (!txt_quantite.getText().isEmpty() && txt_quantite.getText().matches("\\d+")) {
            quantite = Integer.parseInt(txt_quantite.getText());
        } else {
            JOptionPane.showMessageDialog(this, "please enter an quantity");
            return false;
        }

        if (destination.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter destination");
            return false;
        }

        return true;
    }
    
    
    boolean validationCommande() {

        marche = txt_marche.getText();
        num = txt_numero.getText();
        site = txt_site.getText();
                
               
        if (marche.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter marchee");
            return false;
        }

       if (!txt_id.getText().isEmpty() && txt_id.getText().matches("\\d+")) {
            id = Integer.parseInt(txt_id.getText());
        } else {
            JOptionPane.showMessageDialog(this, "please enter id");
            return false;
        }

        if (num.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter numeroo");
            return false;
        }
        
         if (site.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter numeroo");
            return false;
        }


        return true;
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
        pnl = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbl_user = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_obs = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_Ajouter = new javax.swing.JButton();
        btn_Actualiser = new javax.swing.JButton();
        btn_Modifier = new javax.swing.JButton();
        btn_Supprimer = new javax.swing.JButton();
        btn_print = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txt_designation = new javax.swing.JTextField();
        txt_id = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_marche = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_numero = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_site = new javax.swing.JTextField();
        txt_quantite = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_destination = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txt_date = new com.toedter.calendar.JDateChooser();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_cmdResp = new javax.swing.JTable();
        btn_back = new javax.swing.JButton();
        btn_annuler = new javax.swing.JButton();
        btn_valider = new javax.swing.JButton();
        btn_annuler1 = new javax.swing.JButton();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("jMenu5");

        jMenu6.setText("jMenu6");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl.setBackground(new java.awt.Color(204, 204, 204));
        pnl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        pnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(54, 70, 78));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\list.png")); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 60));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Demande de dotation");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 360, 60));

        lbl_user.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_user.setForeground(new java.awt.Color(255, 255, 255));
        lbl_user.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\admin.png")); // NOI18N
        jPanel1.add(lbl_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 80, 70));

        pnl.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 70));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("ID");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Quantité");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 100, 30));

        txt_obs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_obsActionPerformed(evt);
            }
        });
        jPanel3.add(txt_obs, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 130, 210, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Désignation");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 120, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("OBS");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, 110, 30));

        btn_Ajouter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Ajouter.setText("Ajouter");
        btn_Ajouter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_AjouterMouseClicked(evt);
            }
        });
        btn_Ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AjouterActionPerformed(evt);
            }
        });
        jPanel3.add(btn_Ajouter, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, 120, 40));

        btn_Actualiser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Actualiser.setText("Actualiser");
        btn_Actualiser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ActualiserMouseClicked(evt);
            }
        });
        btn_Actualiser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ActualiserActionPerformed(evt);
            }
        });
        jPanel3.add(btn_Actualiser, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 190, 120, 40));

        btn_Modifier.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Modifier.setText("Modifier");
        btn_Modifier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ModifierMouseClicked(evt);
            }
        });
        btn_Modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ModifierActionPerformed(evt);
            }
        });
        jPanel3.add(btn_Modifier, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 190, 120, 40));

        btn_Supprimer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Supprimer.setText("Supprimer");
        btn_Supprimer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_SupprimerMouseClicked(evt);
            }
        });
        btn_Supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SupprimerActionPerformed(evt);
            }
        });
        jPanel3.add(btn_Supprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 190, 120, 40));

        btn_print.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btn_print.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\print_50px.png")); // NOI18N
        btn_print.setText("Imprimer la fiche de besoin");
        btn_print.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_printMouseClicked(evt);
            }
        });
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });
        jPanel3.add(btn_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 610, 390, 60));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Date");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 100, 30));

        txt_designation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_designationActionPerformed(evt);
            }
        });
        jPanel3.add(txt_designation, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 210, 30));

        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });
        jPanel3.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 190, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Marché");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 100, 30));

        txt_marche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_marcheActionPerformed(evt);
            }
        });
        jPanel3.add(txt_marche, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 190, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Numéro");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 100, 30));

        txt_numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numeroActionPerformed(evt);
            }
        });
        jPanel3.add(txt_numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 190, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Site");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 100, 30));

        txt_site.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_siteActionPerformed(evt);
            }
        });
        jPanel3.add(txt_site, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 190, 30));

        txt_quantite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_quantiteActionPerformed(evt);
            }
        });
        jPanel3.add(txt_quantite, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 50, 210, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Destinantion");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 90, 110, 30));

        txt_destination.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_destinationActionPerformed(evt);
            }
        });
        jPanel3.add(txt_destination, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, 210, 30));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 10, 250));
        jPanel3.add(txt_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 190, 30));

        pnl.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 980, 250));
        pnl.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 980, 10));

        tbl_cmdResp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Désignation", "Quantité", "Destination", "OBS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_cmdResp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_cmdRespMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_cmdResp);
        if (tbl_cmdResp.getColumnModel().getColumnCount() > 0) {
            tbl_cmdResp.getColumnModel().getColumn(0).setResizable(false);
            tbl_cmdResp.getColumnModel().getColumn(1).setResizable(false);
            tbl_cmdResp.getColumnModel().getColumn(3).setResizable(false);
        }

        pnl.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 940, 240));

        btn_back.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btn_back.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\back_to_50px blue ciel.png")); // NOI18N
        btn_back.setText("Back");
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });
        pnl.add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 610, 160, 60));

        btn_annuler.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btn_annuler.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\shutdown_50px.png")); // NOI18N
        btn_annuler.setText("Annuler");
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
        pnl.add(btn_annuler, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 610, 160, 60));

        btn_valider.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btn_valider.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\add_new_50px.png")); // NOI18N
        btn_valider.setText("Valider");
        btn_valider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_validerMouseClicked(evt);
            }
        });
        btn_valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_validerActionPerformed(evt);
            }
        });
        pnl.add(btn_valider, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 610, 160, 60));

        btn_annuler1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btn_annuler1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\printblueciel.png")); // NOI18N
        btn_annuler1.setText("Print");
        btn_annuler1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_annuler1MouseClicked(evt);
            }
        });
        btn_annuler1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annuler1ActionPerformed(evt);
            }
        });
        pnl.add(btn_annuler1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 610, 160, 60));

        getContentPane().add(pnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 700));

        setSize(new java.awt.Dimension(992, 737));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_obsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_obsActionPerformed
        txt_destination.requestFocusInWindow();
    }//GEN-LAST:event_txt_obsActionPerformed

    private void btn_SupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SupprimerActionPerformed
       
    }//GEN-LAST:event_btn_SupprimerActionPerformed

    private void btn_ModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ModifierActionPerformed

    }//GEN-LAST:event_btn_ModifierActionPerformed

    private void btn_ActualiserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ActualiserActionPerformed
      

    }//GEN-LAST:event_btn_ActualiserActionPerformed

    private void btn_AjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AjouterActionPerformed
        
    }//GEN-LAST:event_btn_AjouterActionPerformed

    private void btn_ActualiserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ActualiserMouseClicked
        actualiser();
    }//GEN-LAST:event_btn_ActualiserMouseClicked

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
      MyAccount acc = new MyAccount(lbl_user.getText());
      acc.show();
      acc.setStockVisible();
      this.dispose();
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annulerActionPerformed
      
    }//GEN-LAST:event_btn_annulerActionPerformed

    private void btn_printMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_printMouseClicked
            PrintDoc doc = new PrintDoc();
            doc.printDocument();
    }//GEN-LAST:event_btn_printMouseClicked

    private void btn_ModifierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ModifierMouseClicked
       if(validationArticle()){
           updateRowSelected();
       }
    }//GEN-LAST:event_btn_ModifierMouseClicked

    private void tbl_cmdRespMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_cmdRespMouseClicked
        fromTableToTxtField();
    }//GEN-LAST:event_tbl_cmdRespMouseClicked

    private void txt_designationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_designationActionPerformed
        txt_quantite.requestFocusInWindow();
    }//GEN-LAST:event_txt_designationActionPerformed

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        txt_marche.requestFocusInWindow();
    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_marcheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_marcheActionPerformed
        txt_numero.requestFocusInWindow();
    }//GEN-LAST:event_txt_marcheActionPerformed

    private void txt_numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numeroActionPerformed
        txt_site.requestFocusInWindow();
    }//GEN-LAST:event_txt_numeroActionPerformed

    private void txt_siteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_siteActionPerformed
        txt_date.requestFocusInWindow();
    }//GEN-LAST:event_txt_siteActionPerformed

    private void txt_quantiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_quantiteActionPerformed
        txt_destination.requestFocusInWindow();
    }//GEN-LAST:event_txt_quantiteActionPerformed

    private void txt_destinationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_destinationActionPerformed
       txt_obs.requestFocusInWindow();
    }//GEN-LAST:event_txt_destinationActionPerformed

    private void btn_validerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_validerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_validerActionPerformed

    private void btn_AjouterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_AjouterMouseClicked
        if (validationArticle()) {
            addToTables();
        }   
    }//GEN-LAST:event_btn_AjouterMouseClicked

    private void btn_SupprimerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SupprimerMouseClicked
        deleteSelectedRow();
    }//GEN-LAST:event_btn_SupprimerMouseClicked

    private void btn_validerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_validerMouseClicked
        if(validationCommande()){
            addCommandeRespToDatabase();
            addArticleRespoDatabase();
            txt_id.setText(Integer.toString(getId()));
            actualiser();
            txt_marche.setText("");
            txt_numero.setText("");
            txt_site.setText("");
            txt_date.setDate(null);
        }
            
    }//GEN-LAST:event_btn_validerMouseClicked

    private void btn_annulerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_annulerMouseClicked
            clearTable();
            actualiser();
            txt_marche.setText("");
            txt_numero.setText("");
            txt_site.setText("");
            txt_date.setDate(null);
    }//GEN-LAST:event_btn_annulerMouseClicked

    private void btn_annuler1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_annuler1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_annuler1MouseClicked

    private void btn_annuler1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annuler1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_annuler1ActionPerformed

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
            java.util.logging.Logger.getLogger(Commande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Commande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Commande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Commande.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Commande().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Actualiser;
    private javax.swing.JButton btn_Ajouter;
    private javax.swing.JButton btn_Modifier;
    private javax.swing.JButton btn_Supprimer;
    private javax.swing.JButton btn_annuler;
    private javax.swing.JButton btn_annuler1;
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_valider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbl_user;
    private javax.swing.JPanel pnl;
    private javax.swing.JTable tbl_cmdResp;
    private com.toedter.calendar.JDateChooser txt_date;
    private javax.swing.JTextField txt_designation;
    private javax.swing.JTextField txt_destination;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_marche;
    private javax.swing.JTextField txt_numero;
    private javax.swing.JTextField txt_obs;
    private javax.swing.JTextField txt_quantite;
    private javax.swing.JTextField txt_site;
    // End of variables declaration//GEN-END:variables
}
