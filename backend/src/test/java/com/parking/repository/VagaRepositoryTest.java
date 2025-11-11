package com.parking.repository;

import com.parking.model.Vaga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de integração para VagaRepository
 * Usa banco de dados em memória (H2) para testes
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes do VagaRepository")
public class VagaRepositoryTest {

    @Autowired
    private VagaRepository vagaRepository;

    private Vaga vaga1;
    private Vaga vaga2;

    @BeforeEach
    void setUp() {
        // Limpar dados anteriores
        vagaRepository.deleteAll();

        // Criar vagas de teste
        vaga1 = new Vaga();
        vaga1.setNumero(1);
        vaga1.setStatus(Vaga.StatusVaga.LIVRE);

        vaga2 = new Vaga();
        vaga2.setNumero(2);
        vaga2.setStatus(Vaga.StatusVaga.OCUPADA);

        vagaRepository.save(vaga1);
        vagaRepository.save(vaga2);
    }

    @Test
    @DisplayName("Deve listar todas as vagas")
    void testListarTodasVagas() {
        List<Vaga> vagas = vagaRepository.findAll();

        assertEquals(2, vagas.size());
    }

    @Test
    @DisplayName("Deve buscar vaga pelo número")
    void testBuscarVagaPorNumero() {
        Optional<Vaga> vaga = vagaRepository.findByNumero(1);

        assertTrue(vaga.isPresent());
        assertEquals(1, vaga.get().getNumero());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar vaga inexistente")
    void testBuscarVagaInexistente() {
        Optional<Vaga> vaga = vagaRepository.findByNumero(999);

        assertTrue(vaga.isEmpty());
    }

    @Test
    @DisplayName("Deve buscar todas as vagas livres")
    void testBuscarVagasLivres() {
        List<Vaga> vagasLivres = vagaRepository.findByStatus(Vaga.StatusVaga.LIVRE);

        assertEquals(1, vagasLivres.size());
        assertEquals(Vaga.StatusVaga.LIVRE, vagasLivres.get(0).getStatus());
    }

    @Test
    @DisplayName("Deve buscar todas as vagas ocupadas")
    void testBuscarVagasOcupadas() {
        List<Vaga> vagasOcupadas = vagaRepository.findByStatus(Vaga.StatusVaga.OCUPADA);

        assertEquals(1, vagasOcupadas.size());
        assertEquals(Vaga.StatusVaga.OCUPADA, vagasOcupadas.get(0).getStatus());
    }

    @Test
    @DisplayName("Deve contar vagas livres")
    void testContarVagasLivres() {
        long vagasLivres = vagaRepository.countVagasLivres();

        assertEquals(1, vagasLivres);
    }

    @Test
    @DisplayName("Deve contar vagas ocupadas")
    void testContarVagasOcupadas() {
        long vagasOcupadas = vagaRepository.countVagasOcupadas();

        assertEquals(1, vagasOcupadas);
    }

    @Test
    @DisplayName("Deve salvar uma nova vaga")
    void testSalvarNovaVaga() {
        Vaga novaVaga = new Vaga();
        novaVaga.setNumero(3);
        novaVaga.setStatus(Vaga.StatusVaga.LIVRE);

        vagaRepository.save(novaVaga);
        List<Vaga> vagas = vagaRepository.findAll();

        assertEquals(3, vagas.size());
    }

    @Test
    @DisplayName("Deve atualizar status de uma vaga")
    void testAtualizarStatusVaga() {
        vaga1.setStatus(Vaga.StatusVaga.OCUPADA);
        vagaRepository.save(vaga1);

        Optional<Vaga> vagaAtualizada = vagaRepository.findByNumero(1);

        assertTrue(vagaAtualizada.isPresent());
        assertEquals(Vaga.StatusVaga.OCUPADA, vagaAtualizada.get().getStatus());
    }

    @Test
    @DisplayName("Deve deletar uma vaga")
    void testDeletarVaga() {
        vagaRepository.delete(vaga1);

        List<Vaga> vagas = vagaRepository.findAll();

        assertEquals(1, vagas.size());
    }
}
