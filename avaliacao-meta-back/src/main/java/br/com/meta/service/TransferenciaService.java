package br.com.meta.service;

import br.com.meta.model.Transferencia;
import br.com.meta.model.enums.StatusTransferencia;
import br.com.meta.model.enums.TaxaTransferencia;
import br.com.meta.repository.TransferenciaRepository;
import br.com.meta.service.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class TransferenciaService {

    private final AtomicBoolean efetivandoTransferencia = new AtomicBoolean(false);

    private TransferenciaRepository repository;

    public TransferenciaService(TransferenciaRepository repository) {
        this.repository = repository;
    }

    public Transferencia buscarPeloId(Long id) {
        Optional<Transferencia> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Transferência não encontrada! Id: " + id));
    }

    public List<Transferencia> listarComPaginacao(Pageable paginacao) {
        return repository.findAll(paginacao).getContent();
    }

    @Transactional
    public Transferencia agendar(Transferencia transferencia) {
        TaxaTransferencia taxaTransferencia = TaxaTransferencia.toEnum(transferencia.getDiasAteTransferencia());

        transferencia.setValorTaxa(
                taxaTransferencia.getValor()
                        .add(transferencia.getValorTransferencia().multiply(taxaTransferencia.getTaxa()))
        );

        return repository.save(transferencia);
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void efetivarTransferencia() {
        try {
            if (efetivandoTransferencia.get()) {
                return;
            }

            log.info("Iniciando a efetivação das tranferências");
            efetivandoTransferencia.set(true);

            var transferenciasAgendadasHoje = repository
                    .findByDataTransferenciaAndStatus(LocalDate.now(), StatusTransferencia.AGENDADA);

            if (transferenciasAgendadasHoje.isEmpty()) {
                log.info("Não há transferências para efetivação");
                efetivandoTransferencia.set(false);
                return;
            }

            transferenciasAgendadasHoje.forEach(transferencia -> {
                transferencia.setStatus(StatusTransferencia.CONFIRMADA);
                repository.save(transferencia);
            });

            log.info("Finalizando a efetivação das tranferências");
        } catch (Exception e) {
            log.error("Erro ao efetivar as tranferências. " + e);
        } finally {
            efetivandoTransferencia.set(false);
        }
    }

}
