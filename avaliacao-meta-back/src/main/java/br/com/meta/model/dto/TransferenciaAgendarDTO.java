package br.com.meta.model.dto;

import br.com.meta.controller.validation.TransferenciaAgendar;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TransferenciaAgendar
public class TransferenciaAgendarDTO {

    @NotNull(message = "A conta de origem é obrigatória")
    @Pattern(regexp = "^\\d{10}$", message = "A conta de origem deve conter 10 números")
    private String contaOrigem;

    @NotNull(message = "A conta de destino é obrigatória")
    @Pattern(regexp = "^\\d{10}$", message = "A conta de origem deve conter 10 números")
    private String contaDestino;

    @NotNull(message = "O valor da transferência é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor da transferência deve ser maior que zero")
    @Schema(example = "354.22")
    private BigDecimal valorTransferencia;

    @NotNull(message = "A data da transferência é obrigatória")
    @Schema(type = "string", pattern = "dd/MM/yyyy", example = "22/06/2025")
    private String dataTransferencia;

}
