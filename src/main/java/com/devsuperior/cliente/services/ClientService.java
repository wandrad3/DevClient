package com.devsuperior.cliente.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devsuperior.cliente.dto.ClientDTO;
import com.devsuperior.cliente.entity.Client;
import com.devsuperior.cliente.repositories.ClientRepository;
import com.devsuperior.cliente.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		
		return clientRepository.findAll(pageRequest).map(x -> new ClientDTO(x));
	}
	
	
	public ClientDTO findById(Long id) {
		
		Optional<Client> obj = clientRepository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		return new ClientDTO(entity);
	}

}
