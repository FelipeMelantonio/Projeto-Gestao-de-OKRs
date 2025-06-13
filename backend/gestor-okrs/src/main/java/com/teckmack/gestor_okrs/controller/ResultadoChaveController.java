package com.teckmack.gestor_okrs.controller;

import com.teckmack.gestor_okrs.model.ResultadoChave;
import com.teckmack.gestor_okrs.service.ResultadoChaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/resultados-chaves")  // Endereço base para as requisições de ResultadoChave
public class ResultadoChaveController {

    @Autowired
    private ResultadoChaveService resultadoChaveService;  // Injetando o serviço para tratar os Resultados Chave

    // Método para listar todos os Resultados Chave
    @GetMapping
    public ResponseEntity<List<ResultadoChave>> listarTodos() {
        // Chama o serviço para pegar todos os resultados chave
        List<ResultadoChave> resultadosChave = resultadoChaveService.listarTodos();
        
        // Retorna os resultados com status 200 OK
        return new ResponseEntity<>(resultadosChave, HttpStatus.OK);
    }

    // Método para buscar um Resultado Chave por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResultadoChave> buscarPorId(@PathVariable Long id) {
        // Chama o serviço para buscar um resultado chave específico
        Optional<ResultadoChave> resultadoChave = resultadoChaveService.buscarPorId(id);
        
        // Se encontrado, retorna com status 200 OK
        if (resultadoChave.isPresent()) {
            return new ResponseEntity<>(resultadoChave.get(), HttpStatus.OK);
        }
        
        // Se não encontrado, retorna com status 404 NOT FOUND
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Método para salvar ou criar um novo Resultado Chave
    @PostMapping
    public ResponseEntity<ResultadoChave> salvar(@RequestBody ResultadoChave resultadoChave) {
        // Chama o serviço para salvar o novo resultado chave
        ResultadoChave savedResultadoChave = resultadoChaveService.salvar(resultadoChave);
        
        // Retorna o resultado salvo com status 201 CREATED
        return new ResponseEntity<>(savedResultadoChave, HttpStatus.CREATED);
    }

    // Método para deletar um Resultado Chave
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // Verifica se o resultado chave existe
        Optional<ResultadoChave> resultadoChave = resultadoChaveService.buscarPorId(id);
        
        // Se encontrado, deleta e retorna com status 204 NO CONTENT
        if (resultadoChave.isPresent()) {
            resultadoChaveService.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        // Se não encontrado, retorna com status 404 NOT FOUND
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}/porcentagem")
public ResponseEntity<Double> calcularPorcentagemConclusaoResultadoChave(@PathVariable Long id) {
    double porcentagem = resultadoChaveService.calcularPorcentagemConclusao(id);
    return new ResponseEntity<>(porcentagem, HttpStatus.OK);
}
@PutMapping("/{id}")
public ResponseEntity<ResultadoChave> atualizarOuCriar(@PathVariable Long id, @RequestBody ResultadoChave resultadoChave) {
    ResultadoChave atualizado = resultadoChaveService.put(id, resultadoChave);
    return ResponseEntity.ok(atualizado);
}

}