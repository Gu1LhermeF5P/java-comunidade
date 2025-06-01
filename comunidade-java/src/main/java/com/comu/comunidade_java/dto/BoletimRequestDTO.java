package com.comu.comunidade_java.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BoletimRequestDTO {
    // @NotBlank(message = "Remetente é obrigatório")
    // @Size(min = 2, max = 100)
    private String sender;

    // @NotBlank(message = "Título é obrigatório")
    // @Size(min = 5, max = 150)
    private String title;

    // @NotBlank(message = "Localização é obrigatória")
    // @Size(min = 5, max = 200)
    private String location;

    // @NotBlank(message = "Conteúdo é obrigatório")
    private String content;

    // @NotBlank(message = "Gravidade é obrigatória")
    private String severity;

    private LocalDateTime timestamp;
}