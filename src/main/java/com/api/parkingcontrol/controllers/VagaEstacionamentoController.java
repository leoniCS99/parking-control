
package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.VagaEstacionamentoDto;
import com.api.parkingcontrol.models.VagaEstacionamentoModel;
import com.api.parkingcontrol.services.VagaEstacionamentoService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vaga-estacionamento")
public class VagaEstacionamentoController {

    final VagaEstacionamentoService vagaEstacionamentoService;

    public VagaEstacionamentoController(VagaEstacionamentoService vagaEstacionamentoService) {
        this.vagaEstacionamentoService = vagaEstacionamentoService;
    }

    @PostMapping
    public ResponseEntity<Object> salvarVagaEstacionamento(@RequestBody @Valid VagaEstacionamentoDto vagaEstacionamentoDto){
        if (vagaEstacionamentoService.existePlacaVeiculo(vagaEstacionamentoDto.getPlacaCarro())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Está placa do Veiculo já está em Utilização!");
        }

        if (vagaEstacionamentoService.existeNumeroDeVaga(vagaEstacionamentoDto.getNumeroVagaEstacionamento())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: O numero da vagad de Estacionamento já está em uso!");
        }

        if (vagaEstacionamentoService.existeApartamentoBloco(vagaEstacionamentoDto.getApartamento(), vagaEstacionamentoDto.getBloco())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: o bloco do apartamento já está em utilização!");
        }

        var vagaEstacionamentoModel = new VagaEstacionamentoModel();
        BeanUtils.copyProperties(vagaEstacionamentoDto, vagaEstacionamentoModel);
        vagaEstacionamentoModel.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(vagaEstacionamentoService.salvar(vagaEstacionamentoModel));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<VagaEstacionamentoModel>> getAllvagaestacionamento(@PageableDefault(page = 0,size = 10,sort = "id",direction = Sort.Direction.ASC)Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(vagaEstacionamentoService.findAll(pageable));
    }

    @GetMapping
    public ResponseEntity<Object> getOneVagaEstacionamento(@PathVariable(value = "id")UUID id){
        Optional<VagaEstacionamentoModel> vagaEstacionamentoModelOptional = vagaEstacionamentoService.finById(id);
        if (!vagaEstacionamentoModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga Estacionamento não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(vagaEstacionamentoModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletaVagaEstacionamento(@PathVariable(value = "id") UUID id){
        Optional<VagaEstacionamentoModel> vagaEstacionamentoModelOptional = vagaEstacionamentoService.finById(id);
        if (!vagaEstacionamentoModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga Não encontrada.");
        }

         vagaEstacionamentoService.delete(vagaEstacionamentoModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Vaga de estacionamento foi deletada com Sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> upadateVagaEstacionamento(@PathVariable(value = "id") UUID id,
                                                            @RequestBody @Valid VagaEstacionamentoDto vagaEstacionamentoDto){
        Optional<VagaEstacionamentoModel> vagaEstacionamentoModelOptional = vagaEstacionamentoService.finById(id);
        if (!vagaEstacionamentoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga estacionamento Não encontrada");
        }

        var vagaEstacionamentoModel = vagaEstacionamentoModelOptional.get();
        vagaEstacionamentoModel.setNumeroVagaEstacionamento(vagaEstacionamentoDto.getNumeroVagaEstacionamento());
        vagaEstacionamentoModel.setPlacaCarro(vagaEstacionamentoDto.getPlacaCarro());
        vagaEstacionamentoModel.setMarcaCarro(vagaEstacionamentoDto.getMarcaCarro());
        vagaEstacionamentoModel.setModeloCarro(vagaEstacionamentoDto.getModeloCarro());
        vagaEstacionamentoModel.setCorCarro(vagaEstacionamentoDto.getCorCarro());
        vagaEstacionamentoModel.setNomeResponsavel(vagaEstacionamentoDto.getNomeResponsavel());
        vagaEstacionamentoModel.setApartamento(vagaEstacionamentoDto.getApartamento());
        vagaEstacionamentoModel.setBloco(vagaEstacionamentoDto.getBloco());

        return ResponseEntity.status(HttpStatus.OK).body(vagaEstacionamentoService.salvar(vagaEstacionamentoModel));
    }
}


