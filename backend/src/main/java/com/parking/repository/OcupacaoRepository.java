package com.parking.repository;

import com.parking.model.Ocupacao;
import com.parking.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository para acesso e manipulação de dados da entidade Ocupacao.
 * Fornece operações CRUD padrão e queries customizadas para ocupações.
 */
@Repository
public interface OcupacaoRepository extends JpaRepository<Ocupacao, Integer> {

    /**
     * Busca todas as ocupações de uma vaga específica
     * @param vaga a vaga
     * @return lista de ocupações da vaga
     */
    List<Ocupacao> findByVaga(Vaga vaga);

    /**
     * Busca a ocupação ativa (sem hora de saída) de uma vaga
     * @param vaga a vaga
     * @return Optional contendo a ocupação ativa, se existir
     */
    @Query("SELECT o FROM Ocupacao o WHERE o.vaga = :vaga AND o.horaSaida IS NULL")
    Optional<Ocupacao> findOcupacaoAtivaByVaga(Vaga vaga);

    /**
     * Busca todas as ocupações que ainda estão ativas (sem hora de saída)
     * @return lista de ocupações ativas
     */
    @Query("SELECT o FROM Ocupacao o WHERE o.horaSaida IS NULL")
    List<Ocupacao> findOcupacoesPendentes();

    /**
     * Busca todas as ocupações finalizadas (com hora de saída)
     * @return lista de ocupações finalizadas
     */
    @Query("SELECT o FROM Ocupacao o WHERE o.horaSaida IS NOT NULL")
    List<Ocupacao> findOcupacoesConcluidas();

    /**
     * Busca ocupações por período (entre duas datas)
     * @param dataInicio a data de início do período
     * @param dataFim a data de fim do período
     * @return lista de ocupações dentro do período
     */
    @Query("SELECT o FROM Ocupacao o WHERE o.horaEntrada >= :dataInicio AND o.horaEntrada <= :dataFim")
    List<Ocupacao> findOcupacoesPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);

    /**
     * Conta quantas ocupações estão ativas no momento
     * @return número de ocupações ativas
     */
    @Query("SELECT COUNT(o) FROM Ocupacao o WHERE o.horaSaida IS NULL")
    long countOcupacoesPendentes();

    /**
     * Busca a ocupação mais recente de uma vaga
     * @param vaga a vaga
     * @return Optional contendo a ocupação mais recente
     */
    @Query(value = "SELECT * FROM ocupacoes WHERE vaga_id = :#{#vaga.id} ORDER BY hora_entrada DESC LIMIT 1", nativeQuery = true)
    Optional<Ocupacao> findMaisRecenteByVaga(Vaga vaga);
}
