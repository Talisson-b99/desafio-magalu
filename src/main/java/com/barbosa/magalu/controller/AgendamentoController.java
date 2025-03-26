package com.barbosa.magalu.controller;

import com.barbosa.magalu.business.AgendamentoService;
import com.barbosa.magalu.controller.dto.in.AgendamentoInDTO;
import com.barbosa.magalu.controller.dto.out.AgendamentoOutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoOutDTO> gravarAgendamento(@RequestBody AgendamentoInDTO agendamento) {
        return ResponseEntity.ok(agendamentoService.gravarAgendamento(agendamento));
    }
}
