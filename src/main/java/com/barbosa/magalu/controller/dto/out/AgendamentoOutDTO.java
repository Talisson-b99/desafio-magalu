package com.barbosa.magalu.controller.dto.out;

import com.barbosa.magalu.infrastructure.enums.EnumStatusNotificacao;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record AgendamentoOutDTO(
        Long id,
        String emailDestinatario,
        String telefoneDestinatario,
        String mensagem,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime dataHoraEnvio,
        EnumStatusNotificacao statusNotificacao
) {
}
