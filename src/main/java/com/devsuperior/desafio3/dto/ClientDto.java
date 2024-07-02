package com.devsuperior.desafio3.dto;

import com.devsuperior.desafio3.entities.Client;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ClientDto {

    private Long id;

    @Size(min=10 , max = 50 , message = "Nome precisa ter entre 10 e 50 caracteres")
    @NotBlank(message = "Campo Nome Requerido")
    private String name;

    @NotBlank(message = "Campo CPF Requerido")
    private String cpf;


    @NotBlank(message = "Caso não tenha renda entre com 0")
    private Double income; //renda do cliente

    @NotBlank(message = "Campo CPF Requerido")
    private LocalDate birthDate;

    @NotBlank(message = "Caso não tenha filhos entre com 0")
    private Integer children;

    public ClientDto(Long id, String cpf, String name, Double income, LocalDate birthDate, Integer children) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.income = income;
        this.birthDate = birthDate;
        this.children = children;
    }

    public ClientDto(Client entity){
        id = entity.getId();
        name = entity.getName();
        cpf = entity.getCpf();
        income = entity.getIncome();
        birthDate = entity.getBirthDate();
        children = entity.getChildren();

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public Double getIncome() {
        return income;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getChildren() {
        return children;
    }
}
