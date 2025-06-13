package com.teckmack.gestor_okrs.controller;

import com.teckmack.gestor_okrs.model.Iniciativa;
import com.teckmack.gestor_okrs.service.IniciativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/iniciativas")  // Endereço base para as requisições de Iniciativa
public class IniciativaController {

    @Autowired
    private IniciativaService iniciativaService;  // Injetando o serviço para tratar as Iniciativas

    // Método para listar todas as Iniciativas
    @GetMapping
    public ResponseEntity<List<Iniciativa>> listarTodos() {
        // Chama o serviço para pegar todas as iniciativas
        List<Iniciativa> iniciativas = iniciativaService.listarTodos();
        
        // Retorna as iniciativas com status 200 OK
        return new ResponseEntity<>(iniciativas, HttpStatus.OK);
    }

    // Método para buscar uma Iniciativa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Iniciativa> buscarPorId(@PathVariable Long id) {
        // Chama o serviço para buscar uma iniciativa específica
        Optional<Iniciativa> iniciativa = iniciativaService.buscarPorId(id);
        
        // Se encontrada, retorna com status 200 OK
        if (iniciativa.isPresent()) {
            return new ResponseEntity<>(iniciativa.get(), HttpStatus.OK);
        }
        
        // Se não encontrada, retorna com status 404 NOT FOUND
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Método para salvar ou criar uma nova Iniciativa
    @PostMapping
    public ResponseEntity<Iniciativa> salvar(@RequestBody Iniciativa iniciativa) {
        // Chama o serviço para salvar a nova iniciativa
        Iniciativa savedIniciativa = iniciativaService.salvar(iniciativa);
        
        // Retorna a iniciativa salva com status 201 CREATED
        return new ResponseEntity<>(savedIniciativa, HttpStatus.CREATED);
    }

    // Método para deletar uma Iniciativa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // Verifica se a iniciativa existe
        Optional<Iniciativa> iniciativa = iniciativaService.buscarPorId(id);
        
        // Se encontrada, deleta e retorna com status 204 NO CONTENT
        if (iniciativa.isPresent()) {
            iniciativaService.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        // Se não encontrada, retorna com status 404 NOT FOUND
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Iniciativa> atualizarOuCriar(@PathVariable Long id, @RequestBody Iniciativa iniciativa) {
        Iniciativa atualizada = iniciativaService.put(id, iniciativa);
        return ResponseEntity.ok(atualizada);
    }
}
