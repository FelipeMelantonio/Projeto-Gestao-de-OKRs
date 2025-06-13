package com.teckmack.gestor_okrs.controller;

import com.teckmack.gestor_okrs.model.Objetivo;
import com.teckmack.gestor_okrs.service.ObjetivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/objetivos")  // Endereço base para as requisições de Objetivo
public class ObjetivoController {

    @Autowired
    private ObjetivoService objetivoService;  // Injetando o serviço que vai tratar da lógica de Objetivos

    // Método para listar todos os Objetivos
    @GetMapping
    public ResponseEntity<List<Objetivo>> listarTodos() {
        // Chama o serviço para pegar todos os objetivos do banco
        List<Objetivo> objetivos = objetivoService.listarTodos();
        
        // Retorna os objetivos com o status HTTP 200 OK
        return new ResponseEntity<>(objetivos, HttpStatus.OK);
    }

    // Método para buscar um Objetivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Objetivo> buscarPorId(@PathVariable Long id) {
        // Chama o serviço para buscar um objetivo específico
        Optional<Objetivo> objetivo = objetivoService.buscarPorId(id);
        
        // Se o objetivo for encontrado, retorna ele com status 200 OK
        if (objetivo.isPresent()) {
            return new ResponseEntity<>(objetivo.get(), HttpStatus.OK);
        }
        
        // Se não encontrar, retorna um status 404 NOT FOUND
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Método para criar um novo Objetivo
    @PostMapping
    public ResponseEntity<Objetivo> salvar(@RequestBody Objetivo objetivo) {
        // Chama o serviço para salvar o novo objetivo
        Objetivo savedObjetivo = objetivoService.salvar(objetivo);
        
        // Retorna o objetivo salvo com status 201 CREATED
        return new ResponseEntity<>(savedObjetivo, HttpStatus.CREATED);
    }

    // Método para deletar um Objetivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // Verifica se o objetivo existe
        Optional<Objetivo> objetivo = objetivoService.buscarPorId(id);
        
        // Se o objetivo for encontrado, deleta e retorna 204 NO CONTENT
        if (objetivo.isPresent()) {
            objetivoService.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        // Se não encontrar, retorna 404 NOT FOUND
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}/porcentagem")
    public ResponseEntity<Double> calcularPorcentagemConclusaoObjetivo(@PathVariable Long id) {
    double porcentagem = objetivoService.calcularPorcentagemConclusao(id);
    return new ResponseEntity<>(porcentagem, HttpStatus.OK);
}

@PutMapping("/{id}")
public ResponseEntity<Objetivo> atualizarOuCriar(@PathVariable Long id, @RequestBody Objetivo objetivo) {
    Objetivo atualizado = objetivoService.put(id, objetivo);
    return ResponseEntity.ok(atualizado);
}


} 