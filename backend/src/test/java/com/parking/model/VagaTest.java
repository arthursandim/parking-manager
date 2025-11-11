package com.parking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a entidade Vaga
 */
@DisplayName("Testes da Entidade Vaga")
public class VagaTest {

    private Vaga vaga;

    @BeforeEach
    void setUp() {
        vaga = new Vaga();
        vaga.setNumero(1);
    }

    @Test
    @DisplayName("Deve criar uma vaga com status LIVRE por padrão")
    void testCriarVagaComStatusLivrePadrao() {
        vaga.onCreate();

        assertEquals(Vaga.StatusVaga.LIVRE, vaga.getStatus());
        assertNotNull(vaga.getCreatedAt());
    }

    @Test
    @DisplayName("Deve permitir mudar status da vaga para OCUPADA")
    void testMudarStatusParaOcupada() {
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);

        assertEquals(Vaga.StatusVaga.OCUPADA, vaga.getStatus());
    }

    @Test
    @DisplayName("Deve permitir mudar status da vaga de volta para LIVRE")
    void testMudarStatusParaLivre() {
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);
        vaga.setStatus(Vaga.StatusVaga.LIVRE);

        assertEquals(Vaga.StatusVaga.LIVRE, vaga.getStatus());
    }

    @Test
    @DisplayName("Deve definir createdAt automaticamente ao criar")
    void testCreatedAtDefinidoAutomaticamente() {
        vaga.onCreate();

        assertNotNull(vaga.getCreatedAt());
        assertTrue(vaga.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    @DisplayName("Deve ter número da vaga")
    void testVagaTemNumero() {
        assertEquals(1, vaga.getNumero());
    }

    @Test
    @DisplayName("StatusVaga enum deve ter descrições")
    void testStatusVagaTemDescricoes() {
        assertEquals("Vaga disponível", Vaga.StatusVaga.LIVRE.getDescricao());
        assertEquals("Vaga ocupada", Vaga.StatusVaga.OCUPADA.getDescricao());
    }
}
