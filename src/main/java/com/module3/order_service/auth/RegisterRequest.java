package com.module3.order_service.auth;

public class RegisterRequest {

    private String fio;
    private String username;
    private String password;
    private String phone;

    public RegisterRequest() {
    }

    public RegisterRequest(String fio, String username, String password, String phone) {
        this.fio = fio;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
