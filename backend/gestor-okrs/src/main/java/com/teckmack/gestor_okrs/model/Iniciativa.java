package com.teckmack.gestor_okrs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Iniciativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private double porcentagemConclusao;

    public Iniciativa() {}

    @ManyToOne
    @JoinColumn(name = "resultado_chave_id")
    @JsonBackReference
    private ResultadoChave resultadoChave;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPorcentagemConclusao() {
        return porcentagemConclusao;
    }

    public void setPorcentagemConclusao(double porcentagemConclusao) {
        this.porcentagemConclusao = porcentagemConclusao;
    }

    public ResultadoChave getResultadoChave() {
        return resultadoChave;
    }

    public void setResultadoChave(ResultadoChave resultadoChave) {
        this.resultadoChave = resultadoChave;
    }
}