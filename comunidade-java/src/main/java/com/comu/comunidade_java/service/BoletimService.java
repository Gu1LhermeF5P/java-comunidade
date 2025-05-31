package com.comu.comunidade_java.service; // Pacote ajustado

import com.comu.comunidade_java.dto.BoletimRequestDTO; // Import ajustado
import com.comu.comunidade_java.dto.BoletimResponseDTO; // Import ajustado
import com.comu.comunidade_java.entity.Boletim; // Import ajustado
import com.comu.comunidade_java.exception.ResourceNotFoundException; // Import ajustado
import com.comu.comunidade_java.repository.BoletimRepository; // Import ajustado
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BoletimService {

    @Autowired
    private BoletimRepository boletimRepository;

    private BoletimResponseDTO convertToResponseDTO(Boletim boletim) {
        BoletimResponseDTO dto = new BoletimResponseDTO();
        dto.setId(boletim.getId());
        dto.setSender(boletim.getSender());
        dto.setTitle(boletim.getTitle());
        dto.setLocation(boletim.getLocation());
        dto.setContent(boletim.getContent());
        dto.setSeverity(boletim.getSeverity());
        dto.setTimestamp(boletim.getTimestamp());
        return dto;
    }

    private Boletim convertToEntity(BoletimRequestDTO dto) {
        Boletim boletim = new Boletim();
        boletim.setSender(dto.getSender());
        boletim.setTitle(dto.getTitle());
        boletim.setLocation(dto.getLocation());
        boletim.setContent(dto.getContent());
        boletim.setSeverity(dto.getSeverity());
        if (dto.getTimestamp() == null) {
             boletim.setTimestamp(LocalDateTime.now());
        } else {
            boletim.setTimestamp(dto.getTimestamp());
        }
        return boletim;
    }

    @Transactional(readOnly = true)
    public Page<BoletimResponseDTO> findAll(Pageable pageable, String severityFilter, String titleFilter) {
        Page<Boletim> boletins;
        if (severityFilter != null && !severityFilter.isEmpty()) {
            boletins = boletimRepository.findBySeverityIgnoreCase(severityFilter, pageable);
        } else if (titleFilter != null && !titleFilter.isEmpty()) {
            boletins = boletimRepository.findByTitleContainingIgnoreCase(titleFilter, pageable);
        }
        else {
            boletins = boletimRepository.findAll(pageable);
        }
        return boletins.map(this::convertToResponseDTO);
    }

    @Transactional(readOnly = true)
    public BoletimResponseDTO findById(Long id) {
        Boletim boletim = boletimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boletim não encontrado com id: " + id));
        return convertToResponseDTO(boletim);
    }

    @Transactional
    public BoletimResponseDTO create(BoletimRequestDTO boletimRequestDTO) {
        Boletim boletim = convertToEntity(boletimRequestDTO);
        boletim = boletimRepository.save(boletim);
        return convertToResponseDTO(boletim);
    }

    @Transactional
    public BoletimResponseDTO update(Long id, BoletimRequestDTO boletimRequestDTO) {
        Boletim existingBoletim = boletimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boletim não encontrado para atualização com id: " + id));

        existingBoletim.setSender(boletimRequestDTO.getSender());
        existingBoletim.setTitle(boletimRequestDTO.getTitle());
        existingBoletim.setLocation(boletimRequestDTO.getLocation());
        existingBoletim.setContent(boletimRequestDTO.getContent());
        existingBoletim.setSeverity(boletimRequestDTO.getSeverity());
        
        Boletim updatedBoletim = boletimRepository.save(existingBoletim);
        return convertToResponseDTO(updatedBoletim);
    }

    @Transactional
    public void delete(Long id) {
        if (!boletimRepository.existsById(id)) {
            throw new ResourceNotFoundException("Boletim não encontrado para exclusão com id: " + id);
        }
        boletimRepository.deleteById(id);
    }
}