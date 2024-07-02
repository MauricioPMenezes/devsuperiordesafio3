package com.devsuperior.desafio3.services;

import com.devsuperior.desafio3.dto.ClientDto;
import com.devsuperior.desafio3.entities.Client;
import com.devsuperior.desafio3.repositories.ClientRepository;
import com.devsuperior.desafio3.services.exceptions.DatabaseException;
import com.devsuperior.desafio3.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;


    private void CopyDtoToEntity(ClientDto dto, Client entity) {

        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());

    }

    public ClientDto findById(Long id) {
        Client client = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Recurso não encontrado!"));
        return new ClientDto(client);
    }

    @Transactional(readOnly = true)
    public Page<ClientDto> findAll(Pageable pageable){
        Page<Client> result= repository.findAll(pageable);
        return result.map(x->new ClientDto(x));

    }


    @Transactional
    public ClientDto insert(ClientDto dto){
        try {
            Client entity = new Client();
            CopyDtoToEntity(dto,entity);
            entity =repository.save(entity);
            return new ClientDto(entity);

        }catch (DataIntegrityViolationException e){
            throw new ResourceNotFoundException("CPF ja cadastrado");
        }


    }
    @Transactional
    public ClientDto update(Long id,ClientDto dto){

        try {
            Client  entity =repository.getReferenceById(id);
            CopyDtoToEntity(dto,entity);
            entity =repository.save(entity);
            return new ClientDto(entity);
        }catch (EntityNotFoundException e  ){

            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }



}
