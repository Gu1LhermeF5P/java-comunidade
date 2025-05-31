package com.comu.comunidade_java.dto; // Pacote ajustado

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BoletimResponseDTO {
    private Long id;
    private String sender;
    private String title;
    private String location;
    private String content;
    private String severity;
    private LocalDateTime timestamp;
}