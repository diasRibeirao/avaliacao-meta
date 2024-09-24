package br.com.meta.controller;

import br.com.meta.model.Transferencia;
import br.com.meta.model.converter.TransferenciaConverter;
import br.com.meta.model.dto.TransferenciaAgendarDTO;
import br.com.meta.model.dto.TransferenciaDTO;
import br.com.meta.model.enums.StatusTransferencia;
import br.com.meta.service.TransferenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransferenciasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransferenciaService transferenciaService;

    @Mock
    private TransferenciaAgendarDTO mockTransferenciaAgendarDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testBuscarPeloId() throws Exception {
        when(transferenciaService.buscarPeloId(1L)).thenReturn(Transferencia.builder()
                .id(1L)
                .contaOrigem("1234567891")
                .contaDestino("1234567892")
                .valorTransferencia(BigDecimal.valueOf(100.00))
                .valorTaxa(BigDecimal.valueOf(1.00))
                .dataTransferencia(LocalDate.now())
                .dataAgendamento(LocalDate.now())
                .status(StatusTransferencia.AGENDADA)
                .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/transferencias/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contaOrigem").value("1234567891"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contaDestino").value("1234567892"));
    }

    @Test
    void testListarComPaginacao() throws Exception {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        transferenciaDTO.setId(1L);
        when(transferenciaService.listarComPaginacao(any())).thenReturn(Collections.singletonList(Transferencia.builder()
                .id(1L)
                .contaOrigem("1234567891")
                .contaDestino("1234567892")
                .valorTransferencia(BigDecimal.valueOf(100.00))
                .valorTaxa(BigDecimal.valueOf(1.00))
                .dataTransferencia(LocalDate.now())
                .dataAgendamento(LocalDate.now())
                .status(StatusTransferencia.AGENDADA)
                .build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/transferencias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].contaOrigem").value("1234567891"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].contaDestino").value("1234567892"));
    }

    @Test
    void testAgendar() throws Exception {
        when(transferenciaService.agendar(any())).thenReturn(Transferencia.builder().build());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/transferencias")
                        .content("{\n" +
                                "  \"contaOrigem\": \"4514387243\",\n" +
                                "  \"contaDestino\": \"1844115089\",\n" +
                                "  \"valorTransferencia\": 354.22,\n" +
                                "  \"dataTransferencia\": \"22/06/2025\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
