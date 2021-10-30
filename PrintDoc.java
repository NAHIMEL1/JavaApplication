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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADS
 */
public class PrintDoc extends javax.swing.JFrame {

    /**
     * Creates new form PrintDoc
     */
    public PrintDoc() {
        initComponents();    
        
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(153,153,153));
        headerRenderer.setForeground(Color.black);
        headerRenderer.setFont( new Font("Tahoma", Font.BOLD , 36));

         for (int i = 0; i < tbl_cmd.getModel().getColumnCount(); i++) {
             tbl_cmd.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
         }
         
         tbl_cmd.setRowHeight(40);
         
    }
    
    public PrintDoc(int id){
        initComponents();
        setRecordsArticle(id);  
        setRecordsArticleToTable(id);
        tbl_cmd.setRowHeight(40);
        
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(153,153,153));
        headerRenderer.setForeground(Color.black);
        headerRenderer.setFont( new Font("Tahoma", Font.BOLD , 36));

         for (int i = 0; i < tbl_cmd.getModel().getColumnCount(); i++) {
             tbl_cmd.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
         }
    }

    
    
    public void setRecordsArticle(int id) {
        
        try {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet;

            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = connection.createStatement();
            String sql = "select * from commanderesponsable where ID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                lbl_marche.setText(resultSet.getString("Marché"));
                lbl_numero.setText(resultSet.getString("Numéro"));
                lbl_site.setText(resultSet.getString("Site"));
                lbl_date.setText(resultSet.getString("Date"));
             }
            
        } catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    
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
    

    
    public void printDocument(){
         
        PrinterJob doc = PrinterJob.getPrinterJob();
        doc.setJobName("Print Data");
        
        doc.setPrintable(new Printable(){
            
            public int print(Graphics pg, PageFormat pf, int pageNum){
                
                pf.setOrientation(PageFormat.LANDSCAPE);
             if(pageNum > 0){
                 return Printable.NO_SUCH_PAGE;
             }
             
             Graphics2D g2 = (Graphics2D)pg;
             g2.translate(pf.getImageableX(), pf.getImageableY());
             g2.scale(0.47,0.47);
             
             pnl_print.print(g2);
             
             return Printable.PAGE_EXISTS;
            }
        
          });
        
        boolean ok = doc.printDialog();
        if(ok){
            try{
                doc.print();
            }
            catch(PrinterException ex){
                ex.printStackTrace();
            }
        }
    }
    
    
                  
      /* This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_print = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_cmd = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lbl_numero = new javax.swing.JLabel();
        lbl_marche = new javax.swing.JLabel();
        lbl_site = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_print.setBackground(new java.awt.Color(255, 255, 255));
        pnl_print.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        pnl_print.setForeground(new java.awt.Color(255, 255, 255));
        pnl_print.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnl_print.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnl_print.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 1130, 10));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnl_print.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 60, 10, 160));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnl_print.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 20, 160));
        pnl_print.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 1130, 10));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("PRESTATION DES TRAVAUX DE PROCEDES DES ATELIERS SULFURIQUES, CENTRALE ET UTILITES");
        pnl_print.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 1100, 30));

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnl_print.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 90, -1, 130));

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnl_print.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 10, 130));
        pnl_print.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 220, 170, 10));
        pnl_print.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 170, 10));
        pnl_print.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 790, 10));

        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnl_print.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, 10, 80));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("N° : ");
        pnl_print.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 60, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Site : ");
        pnl_print.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 100, 100, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Marché :");
        pnl_print.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 100, 30));
        pnl_print.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 790, 10));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel5.setText("FICHE DE BESOIN");
        pnl_print.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 340, 50));

        lbl_date.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        pnl_print.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 260, 200, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Fait à Jorf Lasfar le : ");
        pnl_print.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 260, 200, 30));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tbl_cmd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_cmd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Désignation", "Quantité", "Destination", "OBS"
            }
        ));
        tbl_cmd.setRowHeight(30);
        jScrollPane1.setViewportView(tbl_cmd);

        pnl_print.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, 1040, 580));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel9.setText("d'affaires : ");
        pnl_print.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 1380, 130, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setText("Visa du demandeur : ");
        pnl_print.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 1300, 220, 30));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel13.setText("Accord");
        pnl_print.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 1300, 80, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel14.setText("du chargé");
        pnl_print.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 1340, 130, 30));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel15.setText("d'affaires : ");
        pnl_print.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 1380, 130, 30));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel16.setText("Visa");
        pnl_print.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 1300, 80, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel17.setText("du chargé");
        pnl_print.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 1340, 130, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel18.setText("Accord");
        pnl_print.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 1300, 80, 30));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel19.setText("de la direction :");
        pnl_print.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 1340, 170, 30));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 255), 3, true));
        jPanel5.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 40));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 255), 3, true));
        jPanel5.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 40, 40));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 255), 3, true));
        jPanel5.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 40, 40));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 255), 3, true));
        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 0, 40, 40));

        pnl_print.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 1460, 750, 40));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setText("Non");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 50, 40));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel20.setText("Oui");
        jPanel7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 50, 40));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel21.setText("Non");
        jPanel7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 50, 40));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel22.setText("Oui");
        jPanel7.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 50, 40));

        pnl_print.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 1500, 740, 40));

        lbl_numero.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        pnl_print.add(lbl_numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 270, 30));

        lbl_marche.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        pnl_print.add(lbl_marche, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 260, 30));

        lbl_site.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        pnl_print.add(lbl_site, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 100, 300, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\Capture.PNG")); // NOI18N
        pnl_print.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 170, 130));

        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADS\\Desktop\\Stage\\my icons\\ocp1.PNG")); // NOI18N
        pnl_print.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 90, 160, 130));

        getContentPane().add(pnl_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 1240, 1550));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(PrintDoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrintDoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrintDoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrintDoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrintDoc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_marche;
    private javax.swing.JLabel lbl_numero;
    private javax.swing.JLabel lbl_site;
    private javax.swing.JPanel pnl_print;
    private javax.swing.JTable tbl_cmd;
    // End of variables declaration//GEN-END:variables
}
