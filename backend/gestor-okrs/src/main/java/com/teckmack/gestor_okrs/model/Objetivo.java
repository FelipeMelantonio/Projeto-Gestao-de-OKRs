package com.teckmack.gestor_okrs.model;

import jakarta.persistence.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Objetivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private double porcentagemConclusao;

    public Objetivo() {}

    @OneToMany(mappedBy = "objetivo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnoreProperties("objetivo")  // Ignora a propriedade objetivo na serialização
    private List<ResultadoChave> resultadosChave;


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

    public List<ResultadoChave> getResultadosChave() {
        return resultadosChave;
    }

    public void setResultadosChave(List<ResultadoChave> resultadosChave) {
        this.resultadosChave = resultadosChave;
    }
}
