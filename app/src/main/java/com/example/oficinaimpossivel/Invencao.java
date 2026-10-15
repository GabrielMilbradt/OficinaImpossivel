package com.example.oficinaimpossivel;

public class Invencao {

    private final String nome;
    private final String inventor;
    private final String categoria;
    private final String descricao;
    private final int imagemRes;

    public Invencao(String nome, String inventor, String categoria, String descricao, int imagemRes) {
        this.nome = nome;
        this.inventor = inventor;
        this.categoria = categoria;
        this.descricao = descricao;
        this.imagemRes = imagemRes;
    }

    public String getNome() { return nome; }
    public String getInventor() { return inventor; }
    public String getCategoria() { return categoria; }
    public String getDescricao() { return descricao; }
    public int getImagemRes() { return imagemRes; }
}
