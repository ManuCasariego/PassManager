package pruebas.manuel.passmanager.util;

/**
 * Created by Manuel on 14/02/2015.
 */
public class Usuario {
    private String id;
    private String userName, password, service, URL;

    public Usuario() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getURL() {
        return URL;
    }


    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
