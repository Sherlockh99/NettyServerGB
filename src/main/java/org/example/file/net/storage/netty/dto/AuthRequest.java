package org.example.file.net.storage.netty.dto;


public class AuthRequest implements BasicRequest{

    private String login;
    private String password;
    @Override
    public String getType() {
        return "auth";
    }

    public AuthRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
