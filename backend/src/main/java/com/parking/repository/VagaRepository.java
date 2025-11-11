package com.parking.repository;

import com.parking.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository para acesso e manipulação de dados da entidade Vaga.
 * Fornece operações CRUD padrão e queries customizadas.
 */
@Repository
public interface VagaRepository extends JpaRepository<Vaga, Integer> {

    /**
     * Busca uma vaga pelo seu número único
     * @param numero o número da vaga
     * @return Optional contendo a vaga se encontrada
     */
    Optional<Vaga> findByNumero(Integer numero);

    /**
     * Busca todas as vagas com um status específico
     * @param status o status da vaga (LIVRE ou OCUPADA)
     * @return lista de vagas com o status especificado
     */
    List<Vaga> findByStatus(Vaga.StatusVaga status);

    /**
     * Conta quantas vagas estão livres
     * @return número de vagas livres
     */
    @Query("SELECT COUNT(v) FROM Vaga v WHERE v.status = 'LIVRE'")
    long countVagasLivres();

    /**
     * Conta quantas vagas estão ocupadas
     * @return número de vagas ocupadas
     */
    @Query("SELECT COUNT(v) FROM Vaga v WHERE v.status = 'OCUPADA'")
    long countVagasOcupadas();
}
