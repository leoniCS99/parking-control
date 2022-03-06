
package com.api.parkingcontrol.services;
import com.api.parkingcontrol.models.VagaEstacionamentoModel;
import com.api.parkingcontrol.repositories.VagaEstacionamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VagaEstacionamentoService {

    final VagaEstacionamentoRepository vagaEstacionamentoRepository;

    public VagaEstacionamentoService(VagaEstacionamentoRepository vagaEstacionamentoRepository) {
        this.vagaEstacionamentoRepository = vagaEstacionamentoRepository;
    }

    @Transactional
    public VagaEstacionamentoModel salvar(VagaEstacionamentoModel vagaEstacionamentoModel) {
        return vagaEstacionamentoRepository.save(vagaEstacionamentoModel);
    }

    public boolean existePlacaVeiculo(String placaCarro) {
        return vagaEstacionamentoRepository.existsByPlacaCarro(placaCarro);
    }

    public boolean existeNumeroDeVaga(String numeroVagaEstacionamento) {
        return vagaEstacionamentoRepository.existsByNumeroVagaEstacionamento(numeroVagaEstacionamento);
    }


    public boolean existeApartamentoBloco(String apartamento, String bloco) {
        return vagaEstacionamentoRepository.existsByApartamentoAndBloco(apartamento,bloco);
    }

    public Page<VagaEstacionamentoModel> findAll(Pageable pageable) {
        return vagaEstacionamentoRepository.findAll(pageable);

    }

    public Optional<VagaEstacionamentoModel> finById(UUID id) {
        return vagaEstacionamentoRepository.findById(id);
    }

    @Transactional
    public void delete(VagaEstacionamentoModel vagaEstacionamentoModel) {
       vagaEstacionamentoRepository.delete(vagaEstacionamentoModel);
    }
}
