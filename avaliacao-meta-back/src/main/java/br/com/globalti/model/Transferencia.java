package br.com.meta.model;

import br.com.meta.model.enums.StatusTransferencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Table(name = "TRANSFERENCIAS")
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTA_ORIGEM")
    private String contaOrigem;

    @Column(name = "CONTA_DESTINO")
    private String contaDestino;

    @Column(name = "VALOR_TRANSFERENCIA", precision = 38, scale = 2)
    private BigDecimal valorTransferencia;

    @Column(name = "VALOR_TAXA", precision = 38, scale = 2)
    private BigDecimal valorTaxa;

    @Column(name = "DATA_TRANSFERENCIA")
    private LocalDate dataTransferencia;

    @Column(name = "DATA_AGENDAMENTO")
    private LocalDate dataAgendamento;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusTransferencia status;

    public Long getDiasAteTransferencia() {
        return ChronoUnit.DAYS.between(dataAgendamento, dataTransferencia);
    }

}
