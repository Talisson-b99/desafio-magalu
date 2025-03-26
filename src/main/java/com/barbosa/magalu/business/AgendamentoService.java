package com.barbosa.magalu.business;

import com.barbosa.magalu.business.mapper.IAgendamentoMapper;
import com.barbosa.magalu.controller.dto.in.AgendamentoInDTO;
import com.barbosa.magalu.controller.dto.out.AgendamentoOutDTO;
import com.barbosa.magalu.infrastructure.entities.Agendamento;
import com.barbosa.magalu.infrastructure.exception.NotFoundException;
import com.barbosa.magalu.infrastructure.repositories.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final IAgendamentoMapper agendamentoMapper;

    public AgendamentoOutDTO gravarAgendamento(AgendamentoInDTO agendamento){
        Agendamento agendamentoSave = agendamentoRepository.save(agendamentoMapper.paraEntity(agendamento));

        return agendamentoMapper.paraOut(agendamentoSave);
    }

    public AgendamentoOutDTO buscarAgendamentoPorId(Long id){
        return agendamentoMapper.paraOut(agendamentoRepository.findById(id).orElseThrow(() ->  new NotFoundException("Agendamento não encontrado")));
    }
}
