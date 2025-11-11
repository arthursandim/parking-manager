package com.parking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma vaga de estacionamento.
 * Cada vaga tem um número único e um status (LIVRE ou OCUPADA).
 */
@Entity
@Table(name = "vagas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Número da vaga não pode ser nulo")
    @Min(value = 1, message = "Número da vaga deve ser maior que 0")
    @Column(unique = true, nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVaga status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Define a data/hora de criação automaticamente ao persistir a entidade
     */
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = StatusVaga.LIVRE;
        }
    }

    /**
     * Enum que representa os possíveis estados de uma vaga
     */
    public enum StatusVaga {
        LIVRE("Vaga disponível"),
        OCUPADA("Vaga ocupada");

        private final String descricao;

        StatusVaga(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}
