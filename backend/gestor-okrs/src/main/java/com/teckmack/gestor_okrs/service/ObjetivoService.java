package com.teckmack.gestor_okrs.service;

import com.teckmack.gestor_okrs.model.Objetivo;
import com.teckmack.gestor_okrs.model.ResultadoChave;
import com.teckmack.gestor_okrs.repository.ObjetivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas operações relacionadas à entidade Objetivo.
 */
@Service
public class ObjetivoService {

    @Autowired
    private ObjetivoRepository objetivoRepository;

    /**
     * Lista todos os objetivos cadastrados.
     *
     * @return Lista de Objetivos.
     */
    public List<Objetivo> listarTodos() {
        return objetivoRepository.findAll();
    }

    /**
     * Busca um objetivo pelo seu ID.
     *
     * @param id ID do objetivo.
     * @return Optional com o objetivo encontrado, se existir.
     */
    public Optional<Objetivo> buscarPorId(Long id) {
        return objetivoRepository.findById(id);
    }

    /**
     * Salva um novo objetivo no banco de dados.
     *
     * @param objetivo Objetivo a ser salvo.
     * @return Objetivo salvo.
     */
    public Objetivo salvar(Objetivo objetivo) {
        return objetivoRepository.save(objetivo);
    }

    /**
     * Deleta um objetivo a partir de seu ID.
     *
     * @param id ID do objetivo a ser deletado.
     */
    public void deletar(Long id) {
        objetivoRepository.deleteById(id);
    }

    /**
     * Calcula a porcentagem de conclusão de um objetivo com base
     * na média das porcentagens dos Resultados Chave associados.
     *
     * @param objetivo Objetivo que terá a porcentagem calculada.
     * @return Porcentagem média de conclusão.
     */
    public double calcularPorcentagemConclusao(Objetivo objetivo) {
        List<ResultadoChave> resultados = objetivo.getResultadosChave();
        if (resultados == null || resultados.isEmpty()) {
            return 0;
        }

        double somaPorcentagem = 0;
        for (ResultadoChave resultado : resultados) {
            somaPorcentagem += resultado.getPorcentagemConclusao();
        }

        return somaPorcentagem / resultados.size();
    }

    /**
     * Atualiza a porcentagem de conclusão de um objetivo.
     *
     * @param objetivo Objetivo a ser atualizado.
     */
    public void atualizarPorcentagemConclusao(Objetivo objetivo) {
        double novaPorcentagem = calcularPorcentagemConclusao(objetivo);
        objetivo.setPorcentagemConclusao(novaPorcentagem);
        objetivoRepository.save(objetivo);
    }

    /**
     * Calcula a porcentagem de conclusão de um objetivo a partir de seu ID.
     *
     * @param id ID do objetivo.
     * @return Porcentagem de conclusão do objetivo.
     */
    public double calcularPorcentagemConclusao(Long id) {
        Optional<Objetivo> objetivoOpt = buscarPorId(id);
        if (objetivoOpt.isPresent()) {
            return calcularPorcentagemConclusao(objetivoOpt.get());
        }
        return 0;
    }

    /**
     * Atualiza um objetivo existente com novos dados ou cria um novo caso o ID não exista.
     * Também atualiza a porcentagem de conclusão com base nos resultados chave informados.
     *
     * @param id ID do objetivo.
     * @param novoObjetivo Objeto com os novos dados.
     * @return Objetivo atualizado ou criado.
     */
    public Objetivo put(Long id, Objetivo novoObjetivo) {
        return objetivoRepository.findById(id).map(objetivoExistente -> {
            objetivoExistente.setTitulo(novoObjetivo.getTitulo());
            objetivoExistente.setDescricao(novoObjetivo.getDescricao());

            if (novoObjetivo.getResultadosChave() != null) {
                objetivoExistente.setResultadosChave(novoObjetivo.getResultadosChave());
            }

            atualizarPorcentagemConclusao(objetivoExistente);
            return objetivoRepository.save(objetivoExistente);
        }).orElseGet(() -> {
            novoObjetivo.setId(id);
            atualizarPorcentagemConclusao(novoObjetivo);
            return objetivoRepository.save(novoObjetivo);
        });
    }
}
