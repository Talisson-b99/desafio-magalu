package com.barbosa.magalu.controller;

import com.barbosa.magalu.business.AgendamentoService;
import com.barbosa.magalu.controller.dto.in.AgendamentoInDTO;
import com.barbosa.magalu.controller.dto.out.AgendamentoOutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoOutDTO> gravarAgendamento(@RequestBody AgendamentoInDTO agendamento) {
        return ResponseEntity.ok(agendamentoService.gravarAgendamento(agendamento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoOutDTO>buscarAgendamentoPorId(@PathVariable Long id){
        return ResponseEntity.ok(agendamentoService.buscarAgendamentoPorId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> cancelarAgendamento(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(agendamentoService.cancelarAgendamento(id));
    }
}
