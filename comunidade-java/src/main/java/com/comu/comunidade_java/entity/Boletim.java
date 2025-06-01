package com.comu.comunidade_java.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString; 
import lombok.EqualsAndHashCode; 
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_boletim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boletim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 5, max = 150, message = "Título deve ter entre 5 e 150 caracteres")
    @Column(nullable = false, length = 150)
    private String title;

    @NotBlank(message = "Localização é obrigatória")
    @Size(min = 5, max = 200, message = "Localização deve ter entre 5 e 200 caracteres")
    @Column(nullable = false, length = 200)
    private String location;

    @NotBlank(message = "Conteúdo é obrigatório")
    @Lob 
    @Column(nullable = false) 
    private String content;

    @NotBlank(message = "Gravidade é obrigatória")
    @Column(nullable = false, length = 50)
    private String severity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    // Assumindo que o relacionamento com User já estava como abaixo
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "user_id", nullable = false) 
    @NotNull(message = "O utilizador criador do boletim é obrigatório")
    @ToString.Exclude 
    @EqualsAndHashCode.Exclude 
    private User user; 

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}