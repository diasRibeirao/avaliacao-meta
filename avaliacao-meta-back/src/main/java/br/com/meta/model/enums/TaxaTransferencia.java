package br.com.meta.model.enums;

import br.com.meta.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum TaxaTransferencia {
    TAXA_DE_0_ATE_0(0L, 0L, BigDecimal.valueOf(3.0), BigDecimal.valueOf(0.025)),
    TAXA_DE_1_ATE_10(1L, 10L, BigDecimal.valueOf(12.0), BigDecimal.ZERO),
    TAXA_DE_11_ATE_20(11L, 20L, BigDecimal.ZERO, BigDecimal.valueOf(0.082)),
    TAXA_DE_21_ATE_30(21L, 30L, BigDecimal.ZERO, BigDecimal.valueOf(0.069)),
    TAXA_DE_31_ATE_40(31L, 40L, BigDecimal.ZERO, BigDecimal.valueOf(0.047)),
    TAXA_DE_41_ATE_50(41L, 50L, BigDecimal.ZERO, BigDecimal.valueOf(0.017));

    private Long de;
    private Long ate;
    private BigDecimal valor;
    private BigDecimal taxa;

    public static TaxaTransferencia toEnum(Long diasAteTransferencia) {
        for (TaxaTransferencia taxaTransferencia : TaxaTransferencia.values()) {
            if (diasAteTransferencia >= taxaTransferencia.getDe() && diasAteTransferencia <= taxaTransferencia.getAte()) {
                return taxaTransferencia;
            }
        }

        throw new ObjectNotFoundException("Não há taxa aplicável para a quantidade de " + diasAteTransferencia + " dias até a transferência");
    }

}
