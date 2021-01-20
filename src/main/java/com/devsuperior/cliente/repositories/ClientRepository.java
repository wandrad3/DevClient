package com.devsuperior.cliente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.cliente.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
