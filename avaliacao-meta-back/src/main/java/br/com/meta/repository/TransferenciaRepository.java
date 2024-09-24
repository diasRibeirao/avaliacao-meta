package br.com.meta.repository;

import br.com.meta.model.Transferencia;
import br.com.meta.model.enums.StatusTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    List<Transferencia> findByDataTransferenciaAndStatus(LocalDate dataTransferencia, StatusTransferencia status);

}
