package darwincenter;

/**
 *
 * @author jahir
 */
public class Usuario {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIntMultiples() {
        return intMultiples;
    }

    public void setIntMultiples(String intMultiples) {
        this.intMultiples = intMultiples;
    }

    public String getEstiloAprendizaje() {
        return estiloAprendizaje;
    }

    public void setEstiloAprendizaje(String estiloAprendizaje) {
        this.estiloAprendizaje = estiloAprendizaje;
    }

    public int getCocienteIntelectual() {
        return cocienteIntelectual;
    }

    public void setCocienteIntelectual(int cocienteIntelectual) {
        this.cocienteIntelectual = cocienteIntelectual;
    }

    private int id;
    private String username;
    private String password;
    private String intMultiples;
    private String estiloAprendizaje;
    private int cocienteIntelectual;
}
