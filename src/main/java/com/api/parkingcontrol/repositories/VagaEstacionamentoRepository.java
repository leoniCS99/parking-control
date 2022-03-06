
package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.VagaEstacionamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VagaEstacionamentoRepository  extends JpaRepository<VagaEstacionamentoModel, UUID> {

    boolean existsByPlacaCarro(String placaCarro);

    boolean existsByNumeroVagaEstacionamento(String numeroVagaEstacionamento);

    boolean existsByApartamentoAndBloco(String apartamento, String bloco);

}

