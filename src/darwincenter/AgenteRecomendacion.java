package darwincenter;

import static darwincenter.LoginFrame.usr;
import modelo.Doc;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jahir
 */
public class AgenteRecomendacion extends Agent {

    static ArrayList<Doc> listaDocs;

    @Override
    protected void setup() {
        addBehaviour(new AgenteBehaviour());
    }

    private class AgenteBehaviour extends OneShotBehaviour {

        @Override
        public void action() {
            // 1. Solicitar datos del perfil del usuario al agente de usuario
            AID Agente = new AID("AgenteUsuario", AID.ISLOCALNAME);
            ACLMessage solicitudPerfil = new ACLMessage(ACLMessage.REQUEST);
            solicitudPerfil.addReceiver(Agente);
            solicitudPerfil.setContent("SolicitarPerfil");
            send(solicitudPerfil);

            // 2. Recibir datos del perfil del usuario
            ACLMessage respuestaPerfil = blockingReceive();
            if (respuestaPerfil != null && respuestaPerfil.getPerformative() == ACLMessage.INFORM) {
                String perfilUsuario = respuestaPerfil.getContent();

                // 4. Generar recomendaciones usando los datos del perfil del usuario
                List<String> datosUsuario = Arrays.asList(perfilUsuario.split(","));
                System.out.println("Perfil de " + usr.getUsername() + "[" + usr.getId() + "]: " + datosUsuario);
                generarRecomendaciones(datosUsuario);

                // Crear un ArrayList para almacenar los títulos
                ArrayList<String> titulos = new ArrayList<>();
                for (Doc doc : listaDocs) {
                    titulos.add(doc.getTitulo());
                }

                // 5. Enviar recomendaciones al usuario
                ACLMessage mensajeRecomendaciones = new ACLMessage(ACLMessage.INFORM);
                mensajeRecomendaciones.addReceiver(Agente);
                mensajeRecomendaciones.setContent(String.join(",", titulos));
                send(mensajeRecomendaciones);
            }
        }

        private void generarRecomendaciones(List<String> datosUsuario) {
            // Lógica de generación de recomendaciones
            Connection connection = null;

            try {
                // Establecer la conexión con la base de datos
                connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

                // Preparar la consulta SQL para obtener el usuario con el username y password proporcionados
                String consulta = "SELECT * FROM Docs WHERE CocienteIntelectual >= ? AND (EstiloAprendizajeId = ? OR IntMultiplesId = ?)";
                try ( var pstmt = connection.prepareStatement(consulta)) {
                    pstmt.setString(2, datosUsuario.get(0));
                    pstmt.setString(3, datosUsuario.get(1));
                    pstmt.setInt(1, Integer.parseInt(datosUsuario.get(2)));

                    // Verificar si se encontró docs con los datos proporcionados
                    try ( var resultSet = pstmt.executeQuery()) {
                        listaDocs = new ArrayList<>();

                        // Recorrer todos los docs y agregarlos a la lista
                        while (resultSet.next()) {
                            // Asignar los valores a la lista de docs
                            Doc doc = new Doc();
                            doc.setId(resultSet.getInt("id"));
                            doc.setTitulo(resultSet.getString("Titulo"));
                            doc.setArchivo(resultSet.getString("Archivo"));
                            listaDocs.add(doc);
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
