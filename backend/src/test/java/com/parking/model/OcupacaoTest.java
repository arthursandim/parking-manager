package com.parking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a entidade Ocupacao
 */
@DisplayName("Testes da Entidade Ocupacao")
public class OcupacaoTest {

    private Ocupacao ocupacao;
    private Vaga vaga;

    @BeforeEach
    void setUp() {
        vaga = new Vaga();
        vaga.setId(1);
        vaga.setNumero(1);
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);

        ocupacao = new Ocupacao();
        ocupacao.setVaga(vaga);
        ocupacao.setPlacaCarro("ABC-1234");
        ocupacao.setHoraEntrada(LocalDateTime.now().minusHours(1)); // 1 hora atrás
    }

    @Test
    @DisplayName("Deve verificar se ocupação está ativa (sem hora de saída)")
    void testOcupacaoEstaAtiva() {
        ocupacao.setHoraSaida(null);

        assertTrue(ocupacao.estaAtiva());
    }

    @Test
    @DisplayName("Deve verificar se ocupação não está ativa (com hora de saída)")
    void testOcupacaoNaoEstaAtiva() {
        ocupacao.setHoraSaida(LocalDateTime.now());

        assertFalse(ocupacao.estaAtiva());
    }

    @Test
    @DisplayName("Deve calcular tempo estacionado em minutos")
    void testCalcularTempoEstacionadoMinutos() {
        ocupacao.setHoraEntrada(LocalDateTime.now().minusMinutes(45));
        ocupacao.setHoraSaida(LocalDateTime.now());

        Long tempoMinutos = ocupacao.getTempoEstacionadoMinutos();

        assertNotNull(tempoMinutos);
        assertTrue(tempoMinutos >= 44 && tempoMinutos <= 46); // Entre 44 e 46 minutos
    }

    @Test
    @DisplayName("Deve calcular tempo estacionado em horas (arredondado para cima)")
    void testCalcularTempoEstacionadoHoras() {
        // Estacionado por 1 hora e 30 minutos
        ocupacao.setHoraEntrada(LocalDateTime.now().minusMinutes(90));
        ocupacao.setHoraSaida(LocalDateTime.now());

        Long tempoHoras = ocupacao.getTempoEstacionadoHoras();

        assertNotNull(tempoHoras);
        assertEquals(2, tempoHoras); // Deve arredondar para 2 horas
    }

    @Test
    @DisplayName("Deve arredondar para cima tempo menor que 1 hora")
    void testArredondarTempoMenor1Hora() {
        // Estacionado por 15 minutos
        ocupacao.setHoraEntrada(LocalDateTime.now().minusMinutes(15));
        ocupacao.setHoraSaida(LocalDateTime.now());

        Long tempoHoras = ocupacao.getTempoEstacionadoHoras();

        assertNotNull(tempoHoras);
        assertEquals(1, tempoHoras); // Deve arredondar para 1 hora
    }

    @Test
    @DisplayName("Deve calcular valor a pagar corretamente (R$ 5,00 por hora)")
    void testCalcularValorPagar() {
        // Estacionado por 2 horas
        ocupacao.setHoraEntrada(LocalDateTime.now().minusHours(2));
        ocupacao.setHoraSaida(LocalDateTime.now());

        BigDecimal valor = ocupacao.calcularValorPago();

        assertNotNull(valor);
        assertEquals(BigDecimal.valueOf(10.0), valor); // 2 horas * R$ 5,00
    }

    @Test
    @DisplayName("Deve calcular valor de 1 hora para tempo menor que 1 hora")
    void testCalcularValorMenor1Hora() {
        // Estacionado por 30 minutos
        ocupacao.setHoraEntrada(LocalDateTime.now().minusMinutes(30));
        ocupacao.setHoraSaida(LocalDateTime.now());

        BigDecimal valor = ocupacao.calcularValorPago();

        assertNotNull(valor);
        assertEquals(BigDecimal.valueOf(5.0), valor); // 1 hora (arredondado) * R$ 5,00
    }

    @Test
    @DisplayName("Deve retornar null para tempo em minutos quando não há saída")
    void testTempoEmMinutosNullQuandoSemSaida() {
        ocupacao.setHoraSaida(null);

        Long tempoMinutos = ocupacao.getTempoEstacionadoMinutos();

        assertNull(tempoMinutos);
    }

    @Test
    @DisplayName("Deve retornar null para valor a pagar quando não há saída")
    void testValorPagarNullQuandoSemSaida() {
        ocupacao.setHoraSaida(null);

        BigDecimal valor = ocupacao.calcularValorPago();

        assertNull(valor);
    }

    @Test
    @DisplayName("Deve validar placa do carro")
    void testValidarPlacaCarro() {
        assertEquals("ABC-1234", ocupacao.getPlacaCarro());
    }
}
