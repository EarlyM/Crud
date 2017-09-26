package ua.crud.model;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String login;
    private String password;
    private String fio;

    public User() {
    }

    public User(String login, String password, String fio) {
        this.login = login;
        this.password = password;
        this.fio = fio;
    }

    public User(Long id, String login, String password, String fio) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fio = fio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }
}
