package com.devsuperior.cliente.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.cliente.dto.ClientDTO;
import com.devsuperior.cliente.entity.Client;
import com.devsuperior.cliente.repositories.ClientRepository;
import com.devsuperior.cliente.services.exceptions.DataBaseException;
import com.devsuperior.cliente.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {

		return clientRepository.findAll(pageRequest).map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {

		Optional<Client> obj = clientRepository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		return new ClientDTO(entity);
	}

	@Transactional
	public void delete(Long id) {

		try {
			clientRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity Violation");
		}

	}

	@Transactional
	public ClientDTO insert(ClientDTO clientDto) {
		Client entity = new Client();
		copyDtoToEntity(clientDto,entity);
		entity = clientRepository.save(entity);
		
		return new ClientDTO(entity);
		
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDto) {
		
		
		try {
			
			Client entity = clientRepository.getOne(id);
			copyDtoToEntity(clientDto, entity);
			entity = clientRepository.save(entity);
			return new ClientDTO(entity);
			
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		
		
	}
	
	private void copyDtoToEntity(ClientDTO clientDto, Client entity) {
		
		entity.setName(clientDto.getName());
		entity.setCpf(clientDto.getCpf());
		entity.setIncome(clientDto.getIncome());
		entity.setBirthDate(clientDto.getBirthDate());
		entity.setChildren(clientDto.getChildren());
	}

	

}
