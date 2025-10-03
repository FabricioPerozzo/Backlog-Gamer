package com.stackmobile.backloggamer.model;

public class Game {
    private String nome;
    private String plataforma;
    private String status;
    private String capaUrl;
    private String userId;

    public Game() {}

    public Game(String nome, String plataforma, String status, String capaUrl, String userId) {
        this.nome = nome;
        this.plataforma = plataforma;
        this.status = status;
        this.capaUrl = capaUrl;
        this.userId = userId;
    }

    public String getNome() { return nome; }
    public String getPlataforma() { return plataforma; }
    public String getStatus() { return status; }
    public String getCapaUrl() { return capaUrl; }
    public String getUserId() { return userId; }


    public void setNome(String nome) { this.nome = nome; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public void setStatus(String status) { this.status = status; }
    public void setCapaUrl(String capaUrl) { this.capaUrl = capaUrl; }
    public void setUserId(String userId) { this.userId = userId; }
}
