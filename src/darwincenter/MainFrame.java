package darwincenter;

import static darwincenter.AgenteRecomendacion.listaAllDocs;
import static darwincenter.AgenteRecomendacion.listaDocs;
import modelo.Doc;
import static darwincenter.LoginFrame.usr;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

/**
 *
 * @author jahir
 */
public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/icon.png").getImage());

        userLabel.setText("Usuario: " + usr.getUsername());
        ciLabel.setText("Cociente Intelectual: " + usr.getCocienteIntelectual());
        estApLabel.setText("Estilo de Aprendizaje: " + usr.getEstiloAprendizaje());
        intMultLabel.setText("Inteligencias Multiples: " + usr.getIntMultiples());

        cargarListas();
    }

    private void cargarListas() {
        DefaultListModel<String> model = new DefaultListModel<>();
        DefaultListModel<String> modelAll = new DefaultListModel<>();

        int num = 1;
        for (Doc doc : listaDocs) {
            model.addElement(" " + num + ". " + doc.getTitulo());
            num++;
        }

        num = 1;
        for (Doc doc : listaAllDocs) {
            modelAll.addElement(" " + num + ". " + doc.getTitulo());
            num++;
        }

        recomendacionesList.setModel(model);
        todoList.setModel(modelAll);
    }

    private void abrirDoc() {
        int index = recomendacionesList.getSelectedIndex();
        int panel = jTabbedPane1.getSelectedIndex();

        if (panel == 1) {
            index = todoList.getSelectedIndex();
        }

        if (index != -1) {
            int id = listaDocs.get(index).getId();
            for (Doc doc : listaDocs) {
                if (doc.getId() == id) {
                    try {
                        // Abrir el archivo con la aplicación predeterminada del sistema
                        Desktop.getDesktop().open(new File(doc.getArchivo()));
                        // Registrar interacción en la base de datos
                        registrarInteraccion(doc.getId());
                    } catch (IOException ex) {
                    }
                    break;
                }
            }
        }
    }

    private void registrarInteraccion(int docId) {
        Connection connection = null;

        try {
            // Establecer la conexión con la base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            // Insertar datos aleatorios en la tabla Interaccion
            String insertarDatosInteraccion = "INSERT INTO Interaccion (UsuarioId, DocId) VALUES (?, ?)";
            try ( var pstmtInter = connection.prepareStatement(insertarDatosInteraccion)) {
                pstmtInter.setInt(1, usr.getId()); // UsuarioId
                pstmtInter.setInt(2, docId); // DocId
                pstmtInter.executeUpdate();
            }

        } catch (SQLException e) {
        } finally {
            try {
                // Cerrar la conexión
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        recomendacionesList = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        todoList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        ciLabel = new javax.swing.JLabel();
        estApLabel = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        intMultLabel = new javax.swing.JLabel();
        openButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Darwin Center");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jTabbedPane1.setBackground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        recomendacionesList.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        recomendacionesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recomendacionesListMouseClicked(evt);
            }
        });
        recomendacionesList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                recomendacionesListKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(recomendacionesList);

        jTabbedPane1.addTab("Recomendaciones", jScrollPane1);

        todoList.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        todoList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                todoListMouseClicked(evt);
            }
        });
        todoList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                todoListKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(todoList);

        jTabbedPane1.addTab("Todos los artículos", jScrollPane2);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo.png"))); // NOI18N

        logoutButton.setBackground(new java.awt.Color(0, 0, 0));
        logoutButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton.setText("Cerrar sesión");
        logoutButton.setToolTipText("");
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(80, 80, 80));

        ciLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ciLabel.setForeground(new java.awt.Color(255, 255, 255));
        ciLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ciLabel.setText("Cociente Intelectual:");

        estApLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        estApLabel.setForeground(new java.awt.Color(255, 255, 255));
        estApLabel.setText("Estilo de Aprendizaje:");

        userLabel.setBackground(new java.awt.Color(102, 102, 102));
        userLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        userLabel.setForeground(new java.awt.Color(255, 255, 255));
        userLabel.setText("Usuario:");

        intMultLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        intMultLabel.setForeground(new java.awt.Color(255, 255, 255));
        intMultLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        intMultLabel.setText("Inteligencias Multiples:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(estApLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(intMultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ciLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ciLabel)
                    .addComponent(userLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(estApLabel)
                    .addComponent(intMultLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        openButton.setBackground(new java.awt.Color(0, 0, 0));
        openButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        openButton.setForeground(new java.awt.Color(255, 255, 255));
        openButton.setText("Abrir");
        openButton.setToolTipText("");
        openButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logoutButton)
                    .addComponent(openButton))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        this.setVisible(false);
        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void recomendacionesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recomendacionesListMouseClicked
        if (evt.getClickCount() == 2) { // Doble clic
            openButton.doClick();
        }
    }//GEN-LAST:event_recomendacionesListMouseClicked

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        abrirDoc();
    }//GEN-LAST:event_openButtonActionPerformed

    private void recomendacionesListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_recomendacionesListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { // Enter
            openButton.doClick();
        }
    }//GEN-LAST:event_recomendacionesListKeyPressed

    private void todoListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_todoListMouseClicked
        if (evt.getClickCount() == 2) { // Doble clic
            openButton.doClick();
        }
    }//GEN-LAST:event_todoListMouseClicked

    private void todoListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_todoListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { // Enter
            openButton.doClick();
        }
    }//GEN-LAST:event_todoListKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ciLabel;
    private javax.swing.JLabel estApLabel;
    private javax.swing.JLabel intMultLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton openButton;
    private javax.swing.JList<String> recomendacionesList;
    private javax.swing.JList<String> todoList;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
