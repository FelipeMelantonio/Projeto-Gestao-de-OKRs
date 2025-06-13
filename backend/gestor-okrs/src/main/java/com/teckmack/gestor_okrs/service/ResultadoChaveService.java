package com.teckmack.gestor_okrs.service;

import com.teckmack.gestor_okrs.model.Iniciativa;
import com.teckmack.gestor_okrs.model.ResultadoChave;
import com.teckmack.gestor_okrs.repository.ResultadoChaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Serviço responsável pelas operações relacionadas à entidade ResultadoChave.
 */
@Service
public class ResultadoChaveService {

    @Autowired
    private ResultadoChaveRepository resultadoChaveRepository;

    @Autowired
    private ObjetivoService objetivoService;

    /**
     * Lista todos os resultados chave cadastrados.
     *
     * @return Lista de ResultadoChave.
     */
    public List<ResultadoChave> listarTodos() {
        return resultadoChaveRepository.findAll();
    }

    /**
     * Busca um resultado chave pelo seu ID.
     *
     * @param id ID do resultado chave.
     * @return Optional contendo o resultado chave, se encontrado.
     */
    public Optional<ResultadoChave> buscarPorId(Long id) {
        return resultadoChaveRepository.findById(id);
    }

    /**
     * Salva um novo resultado chave no banco de dados.
     *
     * @param resultadoChave Objeto ResultadoChave a ser salvo.
     * @return ResultadoChave salvo.
     */
    public ResultadoChave salvar(ResultadoChave resultadoChave) {
        return resultadoChaveRepository.save(resultadoChave);
    }

    /**
     * Deleta um resultado chave com base no ID.
     *
     * @param id ID do resultado chave a ser deletado.
     */
    public void deletar(Long id) {
        resultadoChaveRepository.deleteById(id);
    }

    /**
     * Calcula a porcentagem de conclusão de um resultado chave com base nas iniciativas associadas.
     *
     * @param resultadoChaveId ID do resultado chave.
     * @return Porcentagem de conclusão calculada.
     */
    public double calcularPorcentagemConclusao(Long resultadoChaveId) {
        Optional<ResultadoChave> resultadoChaveOpt = resultadoChaveRepository.findById(resultadoChaveId);
        return resultadoChaveOpt.map(this::calcularPorcentagemConclusao).orElse(0.0);
    }

    /**
     * Calcula a porcentagem de conclusão com base na média das porcentagens das iniciativas do resultado chave.
     *
     * @param resultadoChave Objeto ResultadoChave a ser calculado.
     * @return Porcentagem de conclusão.
     */
    public double calcularPorcentagemConclusao(ResultadoChave resultadoChave) {
        List<Iniciativa> iniciativas = resultadoChave.getIniciativas();
        if (iniciativas == null || iniciativas.isEmpty()) {
            return 0;
        }

        double somaPorcentagem = 0;
        for (Iniciativa iniciativa : iniciativas) {
            somaPorcentagem += iniciativa.getPorcentagemConclusao();
        }

        return somaPorcentagem / iniciativas.size();
    }

    /**
     * Atualiza a porcentagem de conclusão de um resultado chave com base nas suas iniciativas.
     * Também atualiza a porcentagem de conclusão do objetivo relacionado.
     *
     * @param resultadoChave Objeto ResultadoChave a ser atualizado.
     */
    public void atualizarPorcentagemConclusao(ResultadoChave resultadoChave) {
        double novaPorcentagem = calcularPorcentagemConclusao(resultadoChave);
        resultadoChave.setPorcentagemConclusao(novaPorcentagem);
        resultadoChaveRepository.save(resultadoChave);

        // Atualiza também o objetivo relacionado
        if (resultadoChave.getObjetivo() != null) {
            objetivoService.atualizarPorcentagemConclusao(resultadoChave.getObjetivo());
        }
    }

    /**
     * Atualiza um resultado chave existente ou cria um novo caso o ID não exista.
     *
     * @param id ID do resultado chave.
     * @param novoResultado Dados atualizados ou novos.
     * @return ResultadoChave atualizado ou criado.
     */
    public ResultadoChave put(Long id, ResultadoChave novoResultado) {
        return resultadoChaveRepository.findById(id).map(resultadoExistente -> {
            resultadoExistente.setDescricao(novoResultado.getDescricao());
            resultadoExistente.setIniciativas(novoResultado.getIniciativas());
            return resultadoChaveRepository.save(resultadoExistente);
        }).orElseGet(() -> {
            novoResultado.setId(id);
            return resultadoChaveRepository.save(novoResultado);
        });
    }
}
