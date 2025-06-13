package com.teckmack.gestor_okrs.service;

import com.teckmack.gestor_okrs.model.Iniciativa;
import com.teckmack.gestor_okrs.model.ResultadoChave;
import com.teckmack.gestor_okrs.repository.IniciativaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas operações relacionadas à entidade Iniciativa.
 */
@Service
public class IniciativaService {

    @Autowired
    private IniciativaRepository iniciativaRepository;

    @Autowired
    private ResultadoChaveService resultadoChaveService;

    /**
     * Lista todas as iniciativas cadastradas.
     *
     * @return Lista de todas as iniciativas.
     */
    public List<Iniciativa> listarTodos() {
        return iniciativaRepository.findAll();
    }

    /**
     * Busca uma iniciativa pelo seu ID.
     *
     * @param id ID da iniciativa a ser buscada.
     * @return Um Optional contendo a iniciativa, se encontrada.
     */
    public Optional<Iniciativa> buscarPorId(Long id) {
        return iniciativaRepository.findById(id);
    }

    /**
     * Salva uma nova iniciativa no banco de dados.
     * Após a inserção, atualiza a porcentagem de conclusão do ResultadoChave associado.
     *
     * @param iniciativa Objeto Iniciativa a ser salvo.
     * @return A iniciativa salva com ID gerado.
     */
    public Iniciativa salvar(Iniciativa iniciativa) {
        Iniciativa salva = iniciativaRepository.save(iniciativa);

        ResultadoChave resultadoChave = salva.getResultadoChave();
        if (resultadoChave != null) {
            resultadoChaveService.atualizarPorcentagemConclusao(resultadoChave);
        }

        return salva;
    }

    /**
     * Remove uma iniciativa pelo ID.
     * Após a exclusão, atualiza a porcentagem de conclusão do ResultadoChave relacionado.
     *
     * @param id ID da iniciativa a ser deletada.
     */
    public void deletar(Long id) {
        Optional<Iniciativa> iniciativaOpt = iniciativaRepository.findById(id);
        if (iniciativaOpt.isPresent()) {
            Iniciativa iniciativa = iniciativaOpt.get();
            ResultadoChave resultadoChave = iniciativa.getResultadoChave();
            iniciativaRepository.deleteById(id);

            if (resultadoChave != null) {
                resultadoChaveService.atualizarPorcentagemConclusao(resultadoChave);
            }
        }
    }

    /**
     * Atualiza uma iniciativa existente com novos dados ou cria uma nova caso o ID não exista.
     *
     * @param id ID da iniciativa a ser atualizada ou criada.
     * @param novaIniciativa Objeto Iniciativa com os novos dados.
     * @return A iniciativa atualizada ou criada.
     */
    public Iniciativa put(Long id, Iniciativa novaIniciativa) {
        return iniciativaRepository.findById(id).map(iniciativaExistente -> {
            iniciativaExistente.setTitulo(novaIniciativa.getTitulo());
            iniciativaExistente.setDescricao(novaIniciativa.getDescricao());
            iniciativaExistente.setPorcentagemConclusao(novaIniciativa.getPorcentagemConclusao());
            iniciativaExistente.setResultadoChave(novaIniciativa.getResultadoChave());
            return iniciativaRepository.save(iniciativaExistente);
        }).orElseGet(() -> {
            novaIniciativa.setId(id);
            return iniciativaRepository.save(novaIniciativa);
        });
    }
}
