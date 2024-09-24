package br.com.meta.model.dto;

import br.com.meta.model.enums.StatusTransferencia;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaDTO {

    private Long id;

    private String contaOrigem;

    private String contaDestino;

    private BigDecimal valorTransferencia;

    private BigDecimal valorTaxa;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Schema(type = "string", pattern = "dd/MM/yyyy")
    private LocalDate dataTransferencia;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Schema(type = "string", pattern = "dd/MM/yyyy")
    private LocalDate dataAgendamento;

    private StatusTransferencia status;

}
