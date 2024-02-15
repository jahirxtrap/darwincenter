package darwincenter;

import modelo.Usuario;
import static modelo.Database.crearDatabase;
import jade.core.Profile;
import jade.core.ProfileImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author jahir
 */
public class LoginFrame extends javax.swing.JFrame {

    static Usuario usr;

    public LoginFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/icon.png").getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        loginButton = new javax.swing.JButton();
        userField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Darwin Center");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        loginButton.setBackground(new java.awt.Color(0, 0, 0));
        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Iniciar sesión");
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        loginButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                loginButtonKeyPressed(evt);
            }
        });

        userField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        userField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userFieldKeyPressed(evt);
            }
        });

        passwordField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordFieldKeyPressed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Usuario:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Constraseña:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(passwordField)
                    .addComponent(userField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(loginButton)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        login();
    }//GEN-LAST:event_loginButtonActionPerformed

    private void userFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { // Enter
            loginButton.doClick();
        }
    }//GEN-LAST:event_userFieldKeyPressed

    private void passwordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { // Enter
            loginButton.doClick();
        }
    }//GEN-LAST:event_passwordFieldKeyPressed

    private void loginButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginButtonKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { // Enter
            loginButton.doClick();
        }
    }//GEN-LAST:event_loginButtonKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });

        // Crear Base de datos
        crearDatabase();
    }

    private void login() {
        String user = userField.getText();
        String password = passwordField.getText();

        obtenerUsuario(user, password);

        if (user.equals(usr.getUsername()) && password.equals(usr.getPassword())) {
            // Iniciar el contenedor principal
            jade.core.Runtime rt = jade.core.Runtime.instance();
            Profile p = new ProfileImpl();
            // Configuración para mostrar la interfaz de JADE
            // p.setParameter(Profile.GUI, "true");
            ContainerController cc = rt.createMainContainer(p);

            // Crear el agente principal y asignarlo al contenedor
            try {
                AgentController ac = cc.createNewAgent(
                        "MainAgent",
                        "darwincenter.Main",
                        new Object[]{});
                ac.start();
            } catch (StaleProxyException e) {
            }

            this.setVisible(false);
            java.awt.EventQueue.invokeLater(() -> {
                new MainFrame().setVisible(true);
            });
        } else if ("".equals(userField.getText()) || "".equals(passwordField.getText())) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void obtenerUsuario(String user, String password) {
        Connection connection = null;

        try {
            // Establecer la conexión con la base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            // Preparar la consulta SQL para obtener el usuario con el username y password proporcionados
            String consulta = "SELECT * FROM Usuario WHERE Username = ? AND Password = ?";
            try ( var pstmt = connection.prepareStatement(consulta)) {
                pstmt.setString(1, user);
                pstmt.setString(2, password);

                // Verificar si se encontró un usuario con el nombre de usuario y contraseña proporcionados
                try ( var resultSet = pstmt.executeQuery()) {
                    // Verificar si se obtuvo alguna fila
                    if (resultSet.next()) {
                        usr = new Usuario();

                        // Obtener los valores de EstiloAprendizaje e IntMultiples mediante sus IDs
                        int estiloAprendizajeId = resultSet.getInt("EstiloAprendizajeId");
                        int intMultiplesId = resultSet.getInt("IntMultiplesId");

                        // Asignar los valores básicos al objeto usr
                        usr.setId(resultSet.getInt("id"));
                        usr.setUsername(resultSet.getString("Username"));
                        usr.setPassword(resultSet.getString("Password"));
                        usr.setEstiloAprendizajeId(estiloAprendizajeId);
                        usr.setIntMultiplesId(intMultiplesId);
                        usr.setCocienteIntelectual(resultSet.getInt("CocienteIntelectual"));

                        // Consultar las tablas EstiloAprendizaje e IntMultiples para obtener los valores reales
                        String consultaEstilo = "SELECT Valor FROM EstiloAprendizaje WHERE id = ?";
                        String consultaIntMultiples = "SELECT Valor FROM IntMultiples WHERE id = ?";

                        try ( var pstmtEstilo = connection.prepareStatement(consultaEstilo);  var pstmtIntMultiples = connection.prepareStatement(consultaIntMultiples)) {

                            // Obtener el valor de EstiloAprendizaje
                            pstmtEstilo.setInt(1, estiloAprendizajeId);
                            try ( var resultSetEstilo = pstmtEstilo.executeQuery()) {
                                if (resultSetEstilo.next()) {
                                    usr.setEstiloAprendizaje(resultSetEstilo.getString("Valor"));
                                }
                            }

                            // Obtener el valor de IntMultiples
                            pstmtIntMultiples.setInt(1, intMultiplesId);
                            try ( var resultSetIntMultiples = pstmtIntMultiples.executeQuery()) {
                                if (resultSetIntMultiples.next()) {
                                    usr.setIntMultiples(resultSetIntMultiples.getString("Valor"));
                                }
                            }
                        }
                    }
                }
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField userField;
    // End of variables declaration//GEN-END:variables
}
