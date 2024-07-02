package com.devsuperior.desafio3.controllers;

import com.devsuperior.desafio3.dto.ClientDto;
import com.devsuperior.desafio3.entities.Client;
import com.devsuperior.desafio3.repositories.ClientRepository;
import com.devsuperior.desafio3.services.ClientService;
import com.devsuperior.desafio3.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService service;
    ClientRepository repository;


    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable Long id) {
        try {
            ClientDto dto = service.findById(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
    }

    @GetMapping
    public ResponseEntity<Page<ClientDto>> findAll(Pageable pageable) {
        try {
            Page<ClientDto> dto = service.findAll(pageable);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
    }

    @PostMapping
    public ResponseEntity<ClientDto> insert(@Valid @RequestBody ClientDto dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);


    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id,@Valid @RequestBody ClientDto dto) {
        try {
            dto = service.update(id, dto);
            return ResponseEntity.ok(dto);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }


    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete( @PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
    }
}
