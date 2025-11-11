package com.parking.repository;

import com.parking.model.Ocupacao;
import com.parking.model.Vaga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de integração para OcupacaoRepository
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes do OcupacaoRepository")
public class OcupacaoRepositoryTest {
    // Nota: @DataJpaTest cria automaticamente as tabelas via Hibernate (ddl-auto=create-drop)

    @Autowired
    private OcupacaoRepository ocupacaoRepository;

    @Autowired
    private VagaRepository vagaRepository;

    private Vaga vaga;
    private Ocupacao ocupacao1;
    private Ocupacao ocupacao2;

    @BeforeEach
    void setUp() {
        // Limpar dados
        ocupacaoRepository.deleteAll();
        vagaRepository.deleteAll();

        // Criar vaga
        vaga = new Vaga();
        vaga.setNumero(1);
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);
        vagaRepository.save(vaga);

        // Criar ocupações
        ocupacao1 = new Ocupacao();
        ocupacao1.setVaga(vaga);
        ocupacao1.setPlacaCarro("ABC-1234");
        ocupacao1.setHoraEntrada(LocalDateTime.now().minusHours(2));
        ocupacao1.setHoraSaida(null); // Ainda ativa

        ocupacao2 = new Ocupacao();
        ocupacao2.setVaga(vaga);
        ocupacao2.setPlacaCarro("XYZ-5678");
        ocupacao2.setHoraEntrada(LocalDateTime.now().minusHours(5));
        ocupacao2.setHoraSaida(LocalDateTime.now().minusHours(1)); // Já finalizada

        ocupacaoRepository.save(ocupacao1);
        ocupacaoRepository.save(ocupacao2);
    }

    @Test
    @DisplayName("Deve listar todas as ocupações")
    void testListarTodasOcupacoes() {
        List<Ocupacao> ocupacoes = ocupacaoRepository.findAll();

        assertEquals(2, ocupacoes.size());
    }

    @Test
    @DisplayName("Deve buscar ocupações por vaga")
    void testBuscarOcupacoesPorVaga() {
        List<Ocupacao> ocupacoes = ocupacaoRepository.findByVaga(vaga);

        assertEquals(2, ocupacoes.size());
    }

    @Test
    @DisplayName("Deve encontrar ocupação ativa de uma vaga")
    void testEncontrarOcupacaoAtivaByVaga() {
        Optional<Ocupacao> ocupacao = ocupacaoRepository.findOcupacaoAtivaByVaga(vaga);

        assertTrue(ocupacao.isPresent());
        assertNull(ocupacao.get().getHoraSaida());
        assertEquals("ABC-1234", ocupacao.get().getPlacaCarro());
    }

    @Test
    @DisplayName("Deve retornar vazio se não houver ocupação ativa")
    void testNaoEncontrarOcupacaoAtiva() {
        // Criar vaga sem ocupação ativa
        Vaga vagaSemOcupacao = new Vaga();
        vagaSemOcupacao.setNumero(2);
        vagaSemOcupacao.setStatus(Vaga.StatusVaga.LIVRE);
        vagaRepository.save(vagaSemOcupacao);

        Optional<Ocupacao> ocupacao = ocupacaoRepository.findOcupacaoAtivaByVaga(vagaSemOcupacao);

        assertTrue(ocupacao.isEmpty());
    }

    @Test
    @DisplayName("Deve listar ocupações pendentes (sem saída)")
    void testListarOcupacoesPendentes() {
        List<Ocupacao> pendentes = ocupacaoRepository.findOcupacoesPendentes();

        assertEquals(1, pendentes.size());
        assertNull(pendentes.get(0).getHoraSaida());
    }

    @Test
    @DisplayName("Deve listar ocupações concluídas (com saída)")
    void testListarOcupacoesConcluidas() {
        List<Ocupacao> concluidas = ocupacaoRepository.findOcupacoesConcluidas();

        assertEquals(1, concluidas.size());
        assertNotNull(concluidas.get(0).getHoraSaida());
    }

    @Test
    @DisplayName("Deve contar ocupações pendentes")
    void testContarOcupacoesPendentes() {
        long pendentes = ocupacaoRepository.countOcupacoesPendentes();

        assertEquals(1, pendentes);
    }

    @Test
    @DisplayName("Deve buscar ocupações por período")
    void testBuscarOcupacoesPorPeriodo() {
        LocalDateTime inicio = LocalDateTime.now().minusHours(6);
        LocalDateTime fim = LocalDateTime.now();

        List<Ocupacao> ocupacoesPeriodo = ocupacaoRepository.findOcupacoesPorPeriodo(inicio, fim);

        assertEquals(2, ocupacoesPeriodo.size());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar período sem ocupações")
    void testBuscarPeriodoSemOcupacoes() {
        LocalDateTime inicio = LocalDateTime.now().plusHours(1);
        LocalDateTime fim = LocalDateTime.now().plusHours(2);

        List<Ocupacao> ocupacoesPeriodo = ocupacaoRepository.findOcupacoesPorPeriodo(inicio, fim);

        assertEquals(0, ocupacoesPeriodo.size());
    }

    @Test
    @DisplayName("Deve encontrar ocupação mais recente de uma vaga")
    void testEncontrarMaisRecenteByVaga() {
        Optional<Ocupacao> maisRecente = ocupacaoRepository.findMaisRecenteByVaga(vaga);

        assertTrue(maisRecente.isPresent());
        // A mais recente é a ocupacao1 (entra mais recentemente)
        assertEquals("ABC-1234", maisRecente.get().getPlacaCarro());
    }

    @Test
    @DisplayName("Deve salvar nova ocupação")
    void testSalvarNovaOcupacao() {
        Ocupacao novaOcupacao = new Ocupacao();
        novaOcupacao.setVaga(vaga);
        novaOcupacao.setPlacaCarro("DEF-9999");
        novaOcupacao.setHoraEntrada(LocalDateTime.now());

        ocupacaoRepository.save(novaOcupacao);
        List<Ocupacao> ocupacoes = ocupacaoRepository.findAll();

        assertEquals(3, ocupacoes.size());
    }

    @Test
    @DisplayName("Deve atualizar hora de saída de uma ocupação")
    void testAtualizarHoraSaida() {
        ocupacao1.setHoraSaida(LocalDateTime.now());
        ocupacaoRepository.save(ocupacao1);

        Optional<Ocupacao> atualizada = ocupacaoRepository.findById(ocupacao1.getId());

        assertTrue(atualizada.isPresent());
        assertNotNull(atualizada.get().getHoraSaida());
    }

    @Test
    @DisplayName("Deve deletar uma ocupação")
    void testDeletarOcupacao() {
        ocupacaoRepository.delete(ocupacao1);

        List<Ocupacao> ocupacoes = ocupacaoRepository.findAll();

        assertEquals(1, ocupacoes.size());
    }
}
