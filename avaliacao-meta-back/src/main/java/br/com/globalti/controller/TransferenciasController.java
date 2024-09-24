package br.com.meta.controller;

import br.com.meta.model.converter.TransferenciaConverter;
import br.com.meta.model.dto.TransferenciaAgendarDTO;
import br.com.meta.model.dto.TransferenciaDTO;
import br.com.meta.service.TransferenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Tag(name = "Transferências")
@RequestMapping(path = "/v1/transferencias", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransferenciasController {

    private TransferenciaService service;

    private TransferenciaConverter converter;

    public TransferenciasController(TransferenciaService service, TransferenciaConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Operation(summary = "Buscar um transferência agendada pelo seu id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TransferenciaDTO> buscarPeloId(@PathVariable Long id) {
        var beneficiario = converter.parse(service.buscarPeloId(id));
        return ResponseEntity.ok().body(beneficiario);
    }

    @GetMapping
    @Operation(summary = "Buscar todas as transferência agendadas cadastradas")
    public ResponseEntity<List<TransferenciaDTO>> listarComPaginacao(@PageableDefault(size = 50, sort = { "dataTransferencia" }) Pageable paginacao) {
        List<TransferenciaDTO> list = converter.parse(service.listarComPaginacao(paginacao));

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Agendar uma transferência financeira ")
    @PostMapping
    public ResponseEntity<Void> agendar(@Valid @RequestBody TransferenciaAgendarDTO agendar) {
        var transferencia = service.agendar(
                converter.parseTransferenciaAgendarDTO(agendar)
        );

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transferencia.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
