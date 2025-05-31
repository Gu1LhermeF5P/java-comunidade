package com.comu.entity; 

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_boletim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boletim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para Oracle, pode preferir SEQUENCE
    private Long id;

    @NotBlank(message = "Remetente é obrigatório")
    @Size(min = 2, max = 100, message = "Remetente deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String sender;

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 5, max = 150, message = "Título deve ter entre 5 e 150 caracteres")
    @Column(nullable = false, length = 150)
    private String title;

    @NotBlank(message = "Localização é obrigatória")
    @Size(min = 5, max = 200, message = "Localização deve ter entre 5 e 200 caracteres")
    @Column(nullable = false, length = 200)
    private String location;

    @NotBlank(message = "Conteúdo é obrigatório")
    @Column(nullable = false, columnDefinition = "TEXT") // Oracle usa CLOB para TEXT longo
    private String content;

    @NotBlank(message = "Gravidade é obrigatória")
    @Column(nullable = false, length = 50)
    private String severity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}
