package com.comu.comunidade_java.controller; // Pacote ajustado

import com.comu.comunidade_java.dto.BoletimRequestDTO; 
import com.comu.comunidade_java.dto.BoletimResponseDTO; 
import com.comu.comunidade_java.service.BoletimService; 
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize; // Descomente se usar segurança baseada em roles
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boletins")
@Tag(name = "Boletins", description = "API para gestão de Boletins")
public class BoletimController {

    @Autowired
    private BoletimService boletimService;

    @Operation(summary = "Lista todos os boletins")
    @GetMapping
    public ResponseEntity<Page<BoletimResponseDTO>> getAllBoletins(
            @PageableDefault(size = 10, sort = "timestamp,desc") Pageable pageable,
            @Parameter(description = "Filtrar por gravidade") @RequestParam(required = false) String severity,
            @Parameter(description = "Filtrar por título") @RequestParam(required = false) String title) {
        return ResponseEntity.ok(boletimService.findAll(pageable, severity, title));
    }

    @Operation(summary = "Busca um boletim por ID")
    @GetMapping("/{id}")
    public ResponseEntity<BoletimResponseDTO> getBoletimById(@PathVariable Long id) {
        return ResponseEntity.ok(boletimService.findById(id));
    }

    @Operation(summary = "Cria um novo boletim")
    @PostMapping
    public ResponseEntity<BoletimResponseDTO> createBoletim(@Valid @RequestBody BoletimRequestDTO boletimRequestDTO) {
        BoletimResponseDTO createdBoletim = boletimService.create(boletimRequestDTO);
        return new ResponseEntity<>(createdBoletim, HttpStatus.CREATED);
    }
    @Operation(summary = "Atualiza um boletim")
    @PutMapping("/{id}")
    public ResponseEntity<BoletimResponseDTO> updateBoletim(@PathVariable Long id, @Valid @RequestBody BoletimRequestDTO boletimRequestDTO) {
        return ResponseEntity.ok(boletimService.update(id, boletimRequestDTO));
    }

    @Operation(summary = "Deleta um boletim")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoletim(@PathVariable Long id) {
        boletimService.delete(id);
        return ResponseEntity.noContent().build();
    }
}