package darwincenter;

import static darwincenter.LoginFrame.usr;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.Usuario;

/**
 *
 * @author jahir
 */
public class AgenteUsuario extends Agent {

    private String perfilUsuario;
    static ArrayList<Usuario> listaUsuarios;

    @Override
    protected void setup() {
        addBehaviour(new AgenteBehaviour());
    }

    private class AgenteBehaviour extends Behaviour {

        @Override
        public void action() {
            // Esperar solicitud de perfil del agente de recomendación
            ACLMessage solicitudPerfil = blockingReceive();
            if (solicitudPerfil != null && solicitudPerfil.getContent().equals("SolicitarPerfil")) {
                // Proporcionar datos de perfil al agente de recomendación
                obtenerUsuarios();
                enviarPerfil();
            }

            // Recibir recomendaciones del agente de recomendación
            ACLMessage mensajeRecomendaciones = blockingReceive();
            if (mensajeRecomendaciones != null && mensajeRecomendaciones.getPerformative() == ACLMessage.INFORM) {
                procesarRecomendaciones(mensajeRecomendaciones.getContent());
            }
        }

        @Override
        public boolean done() {
            // Condición de finalización del comportamiento
            return false;
        }

        private void enviarPerfil() {
            // Enviar datos de perfil al agente de recomendación
            perfilUsuario = usr.getEstiloAprendizajeId() + "," + usr.getIntMultiplesId() + "," + usr.getCocienteIntelectual();
            ACLMessage respuestaPerfil = new ACLMessage(ACLMessage.INFORM);
            respuestaPerfil.addReceiver(new AID("AgenteRecomendacion", AID.ISLOCALNAME));
            respuestaPerfil.setContent(perfilUsuario);
            send(respuestaPerfil);
        }

        private void procesarRecomendaciones(String recomendaciones) {
            // 4. Procesar y mostrar recomendaciones al usuario
            List<String> listaRecomendaciones = Arrays.asList(recomendaciones.split(","));
            System.out.println("Recomendaciones: " + listaRecomendaciones + "\n------------------------------------");
        }

        private void obtenerUsuarios() {
            // Obtener todos los usuarios
            Connection connection = null;

            try {
                // Establecer la conexión con la base de datos
                connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

                // Preparar la consulta SQL para obtener todas las Interacciones
                String consulta = "SELECT * FROM Usuario";
                try ( var pstmt = connection.prepareStatement(consulta)) {

                    // Verificar si se encontró docs con los datos proporcionados
                    try ( var resultSet = pstmt.executeQuery()) {
                        listaUsuarios = new ArrayList<>();

                        // Recorrer todos los docs y agregarlos a la lista
                        while (resultSet.next()) {
                            // Asignar los valores a la lista de usuarios
                            Usuario usuario = new Usuario();

                            // Obtener los valores de EstiloAprendizaje e IntMultiples mediante sus IDs
                            int estiloAprendizajeId = resultSet.getInt("EstiloAprendizajeId");
                            int intMultiplesId = resultSet.getInt("IntMultiplesId");

                            // Asignar los valores básicos al objeto usr
                            usuario.setId(resultSet.getInt("id"));
                            usuario.setUsername(resultSet.getString("Username"));
                            usuario.setPassword(resultSet.getString("Password"));
                            usuario.setEstiloAprendizajeId(estiloAprendizajeId);
                            usuario.setIntMultiplesId(intMultiplesId);
                            usuario.setCocienteIntelectual(resultSet.getInt("CocienteIntelectual"));

                            // Consultar las tablas EstiloAprendizaje e IntMultiples para obtener los valores reales
                            String consultaEstilo = "SELECT Valor FROM EstiloAprendizaje WHERE id = ?";
                            String consultaIntMultiples = "SELECT Valor FROM IntMultiples WHERE id = ?";

                            try ( var pstmtEstilo = connection.prepareStatement(consultaEstilo);  var pstmtIntMultiples = connection.prepareStatement(consultaIntMultiples)) {

                                // Obtener el valor de EstiloAprendizaje
                                pstmtEstilo.setInt(1, estiloAprendizajeId);
                                try ( var resultSetEstilo = pstmtEstilo.executeQuery()) {
                                    if (resultSetEstilo.next()) {
                                        usuario.setEstiloAprendizaje(resultSetEstilo.getString("Valor"));
                                    }
                                }

                                // Obtener el valor de IntMultiples
                                pstmtIntMultiples.setInt(1, intMultiplesId);
                                try ( var resultSetIntMultiples = pstmtIntMultiples.executeQuery()) {
                                    if (resultSetIntMultiples.next()) {
                                        usuario.setIntMultiples(resultSetIntMultiples.getString("Valor"));
                                    }
                                }
                            }
                            listaUsuarios.add(usuario);
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
    }
}
