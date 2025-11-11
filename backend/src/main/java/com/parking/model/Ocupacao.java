package com.parking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa a ocupação de uma vaga de estacionamento.
 * Registra entrada e saída de um veículo, além do valor a pagar.
 */
@Entity
@Table(name = "ocupacoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ocupacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Vaga não pode ser nula")
    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;

    @NotBlank(message = "Placa do carro não pode estar vazia")
    @Column(name = "placa_carro", nullable = false, length = 20)
    private String placaCarro;

    @NotNull(message = "Hora de entrada não pode ser nula")
    @Column(name = "hora_entrada", nullable = false)
    private LocalDateTime horaEntrada;

    @Column(name = "hora_saida")
    private LocalDateTime horaSaida;

    @Column(name = "valor_pago", precision = 10, scale = 2)
    private BigDecimal valorPago;

    /**
     * Define a data/hora de entrada automaticamente ao persistir a entidade
     */
    @PrePersist
    protected void onCreate() {
        if (this.horaEntrada == null) {
            this.horaEntrada = LocalDateTime.now();
        }
    }

    /**
     * Verifica se a ocupação está ativa (sem hora de saída registrada)
     */
    public boolean estaAtiva() {
        return this.horaSaida == null;
    }

    /**
     * Calcula o tempo estacionado em minutos
     */
    public Long getTempoEstacionadoMinutos() {
        if (horaSaida == null) {
            return null;
        }
        return java.time.temporal.ChronoUnit.MINUTES.between(horaEntrada, horaSaida);
    }

    /**
     * Calcula o tempo estacionado em horas (arredondado para cima)
     */
    public Long getTempoEstacionadoHoras() {
        Long minutos = getTempoEstacionadoMinutos();
        if (minutos == null) {
            return null;
        }
        return (minutos + 59) / 60; // Arredonda para cima
    }

    /**
     * Calcula o valor a pagar com base na tarifa de R$ 5,00 por hora
     */
    public BigDecimal calcularValorPago() {
        Long horas = getTempoEstacionadoHoras();
        if (horas == null) {
            return null;
        }
        return BigDecimal.valueOf(horas * 5.0);
    }
}
