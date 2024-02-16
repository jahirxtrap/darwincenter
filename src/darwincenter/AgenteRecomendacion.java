package darwincenter;

import static darwincenter.AgenteUsuario.listaUsuarios;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import modelo.Interaccion;
import modelo.Usuario;

/**
 *
 * @author jahir
 */
public class AgenteRecomendacion extends Agent {

    static ArrayList<Doc> listaDocs;
    static ArrayList<Doc> listaAllDocs;
    static ArrayList<Interaccion> listaInter;

    @Override
    protected void setup() {
        addBehaviour(new AgenteBehaviour());
    }

    private class AgenteBehaviour extends OneShotBehaviour {

        @Override
        public void action() {
            // Solicitar datos del perfil del usuario al agente de usuario
            AID Agente = new AID("AgenteUsuario", AID.ISLOCALNAME);
            ACLMessage solicitudPerfil = new ACLMessage(ACLMessage.REQUEST);
            solicitudPerfil.addReceiver(Agente);
            solicitudPerfil.setContent("SolicitarPerfil");
            send(solicitudPerfil);

            // Recibir datos del perfil del usuario
            ACLMessage respuestaPerfil = blockingReceive();
            if (respuestaPerfil != null && respuestaPerfil.getPerformative() == ACLMessage.INFORM) {
                String perfilUsuario = respuestaPerfil.getContent();

                // Generar recomendaciones usando los datos del perfil del usuario
                List<String> datosUsuario = Arrays.asList(perfilUsuario.split(","));
                System.out.println("Perfil de " + usr.getUsername() + "[" + usr.getId() + "]: " + datosUsuario);
                generarRecomendaciones(datosUsuario);

                // Crear un ArrayList para almacenar los títulos
                ArrayList<String> titulos = new ArrayList<>();
                for (Doc doc : listaDocs) {
                    titulos.add(doc.getTitulo());
                }

                // Enviar recomendaciones al usuario
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

                // Preparar la consulta SQL para obtener todos los Docs
                String consultaAll = "SELECT * FROM Docs";
                try ( var pstmt = connection.prepareStatement(consultaAll)) {

                    // Verificar si se encontró docs con los datos proporcionados
                    try ( var resultSet = pstmt.executeQuery()) {
                        listaAllDocs = new ArrayList<>();

                        // Recorrer todos los docs y agregarlos a la lista
                        while (resultSet.next()) {
                            // Asignar los valores a la lista de docs
                            Doc doc = new Doc();
                            doc.setId(resultSet.getInt("id"));
                            doc.setTitulo(resultSet.getString("Titulo"));
                            doc.setArchivo(resultSet.getString("Archivo"));
                            listaAllDocs.add(doc);
                        }
                    }
                }

                // Preparar la consulta SQL para obtener todas las Interacciones
                String consultaInter = "SELECT * FROM Interaccion";
                try ( var pstmt = connection.prepareStatement(consultaInter)) {

                    // Verificar si se encontró docs con los datos proporcionados
                    try ( var resultSet = pstmt.executeQuery()) {
                        listaInter = new ArrayList<>();

                        // Recorrer todos los docs y agregarlos a la lista
                        while (resultSet.next()) {
                            // Asignar los valores a la lista de interacciones
                            Interaccion inter = new Interaccion();
                            inter.setId(resultSet.getInt("id"));
                            inter.setUsuarioId(resultSet.getInt("UsuarioId"));
                            inter.setDocId(resultSet.getInt("DocId"));
                            listaInter.add(inter);
                        }
                    }
                }

                // Preparar la consulta SQL para obtener los Docs con los datos proporcionados
                String consulta = "SELECT * FROM Docs WHERE CocienteIntelectual <= ? AND (EstiloAprendizajeId = ? OR IntMultiplesId = ?)";
                try ( var pstmt = connection.prepareStatement(consulta)) {
                    pstmt.setString(2, datosUsuario.get(0));
                    pstmt.setString(3, datosUsuario.get(1));
                    pstmt.setInt(1, Integer.parseInt(datosUsuario.get(2)) + 10);

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

                        ordenarLista(listaDocs);
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

        private void ordenarLista(ArrayList<Doc> listaDocs) {
            // Algoritmo Filtrado colaborativo
            // Crear un mapa para almacenar la puntuación de cada documento
            Map<Doc, Integer> scores = new HashMap<>();

            // Recorrer la lista de documentos
            for (Doc doc : listaDocs) {
                int score = 0;

                // Filtrar las interacciones de otros usuarios con características similares al usuario actual
                for (Interaccion interaccion : listaInter) {
                    Usuario usuario = obtenerUsuarioPorId(interaccion.getUsuarioId(), listaUsuarios);

                    // Verificar si el usuario tiene características similares al usuario actual
                    if (usuario != null
                            && usuario.getEstiloAprendizajeId() == usr.getEstiloAprendizajeId()
                            && usuario.getIntMultiplesId() == usr.getIntMultiplesId()
                            && Math.abs(usuario.getCocienteIntelectual() - usr.getCocienteIntelectual()) <= 10) {

                        if (interaccion.getDocId() == doc.getId()) {
                            score++;
                        }
                    }
                }

                // Almacenar el puntaje total para el documento
                scores.put(doc, score);
            }

            // Ordenar la lista de documentos en función de sus puntajes
            Collections.sort(listaDocs, (doc1, doc2) -> scores.get(doc2).compareTo(scores.get(doc1)));
        }
        
        private Usuario obtenerUsuarioPorId(int usuarioId, List<Usuario> listaUsuarios) {
            for (Usuario usuario : listaUsuarios) {
                if (usuario.getId() == usuarioId) {
                    return usuario;
                }
            }
            return null;
        }
    }
}
