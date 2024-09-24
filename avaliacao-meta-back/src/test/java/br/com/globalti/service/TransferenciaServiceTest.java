package br.com.meta.service;

import br.com.meta.model.Transferencia;
import br.com.meta.model.enums.StatusTransferencia;
import br.com.meta.repository.TransferenciaRepository;
import br.com.meta.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaServiceTest {

    @Mock
    private TransferenciaRepository repository;

    @InjectMocks
    private TransferenciaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testBuscarPeloId_Encontrado() {
        Transferencia transferencia = new Transferencia();
        transferencia.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(transferencia));

        Transferencia result = service.buscarPeloId(1L);
        assertEquals(transferencia, result);
    }

    @Test
    void testBuscarPeloId_NaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> service.buscarPeloId(1L));
    }


    @Test
    void testAgendar_TAXA_DE_0_ATE_0() {
        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDate.now());
        transferencia.setDataAgendamento(LocalDate.now());
        transferencia.setValorTransferencia(BigDecimal.valueOf(100));

        when(repository.save(transferencia)).thenReturn(transferencia);

        Transferencia result = service.agendar(transferencia);

        // R$ 3,00 + (2,5% de R% 100,00) = R$ 5,50
        assertEquals(5.5, result.getValorTaxa().doubleValue());
    }

    @Test
    void testAgendar_TAXA_DE_1_ATE_10() {
        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDate.now().plusDays(5));
        transferencia.setDataAgendamento(LocalDate.now());
        transferencia.setValorTransferencia(BigDecimal.valueOf(100));

        when(repository.save(transferencia)).thenReturn(transferencia);

        Transferencia result = service.agendar(transferencia);

        // R$ 12,00 + (0% de R% 100,00) = R$ 12,00
        assertEquals(12.0, result.getValorTaxa().doubleValue());
    }

    @Test
    void testAgendar_TAXA_DE_11_ATE_20() {
        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDate.now().plusDays(15));
        transferencia.setDataAgendamento(LocalDate.now());
        transferencia.setValorTransferencia(BigDecimal.valueOf(100));

        when(repository.save(transferencia)).thenReturn(transferencia);

        Transferencia result = service.agendar(transferencia);

        // R$ 0,00 + (8,2% de R% 100,00) = R$ 8,20
        assertEquals(8.2, result.getValorTaxa().doubleValue());
    }

    @Test
    void testAgendar_TAXA_DE_21_ATE_30() {
        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDate.now().plusDays(25));
        transferencia.setDataAgendamento(LocalDate.now());
        transferencia.setValorTransferencia(BigDecimal.valueOf(100));

        when(repository.save(transferencia)).thenReturn(transferencia);

        Transferencia result = service.agendar(transferencia);

        // R$ 0,00 + (6,9% de R% 100,00) = R$ 6,90
        assertEquals(6.9, result.getValorTaxa().doubleValue());
    }

    @Test
    void testAgendar_TAXA_DE_31_ATE_40() {
        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDate.now().plusDays(35));
        transferencia.setDataAgendamento(LocalDate.now());
        transferencia.setValorTransferencia(BigDecimal.valueOf(100));

        when(repository.save(transferencia)).thenReturn(transferencia);

        Transferencia result = service.agendar(transferencia);

        // R$ 0,00 + (4,7% de R% 100,00) = R$ 4,70
        assertEquals(4.7, result.getValorTaxa().doubleValue());
    }

    @Test
    void testAgendar_TAXA_DE_41_ATE_50() {
        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDate.now().plusDays(45));
        transferencia.setDataAgendamento(LocalDate.now());
        transferencia.setValorTransferencia(BigDecimal.valueOf(100));

        when(repository.save(transferencia)).thenReturn(transferencia);

        Transferencia result = service.agendar(transferencia);

        // R$ 0,00 + (1,7% de R% 100,00) = R$ 1,70
        assertEquals(1.7, result.getValorTaxa().doubleValue());
    }

    @Test
    void testAgendar_NaoHaTaxaAplicavel() {
        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDate.now().plusDays(55));
        transferencia.setDataAgendamento(LocalDate.now());
        transferencia.setValorTransferencia(BigDecimal.valueOf(100));

        when(repository.save(transferencia)).thenReturn(transferencia);

        assertThrows(ObjectNotFoundException.class, () -> service.agendar(transferencia));
    }

    @Test
    void testEfetivarTransferencia() {
        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDate.now());
        transferencia.setStatus(StatusTransferencia.AGENDADA);

        when(repository.findByDataTransferenciaAndStatus(LocalDate.now(), StatusTransferencia.AGENDADA))
                .thenReturn(Collections.singletonList(transferencia));

        service.efetivarTransferencia();

        assertEquals(StatusTransferencia.CONFIRMADA, transferencia.getStatus());
        verify(repository, times(1)).save(transferencia);
    }
}