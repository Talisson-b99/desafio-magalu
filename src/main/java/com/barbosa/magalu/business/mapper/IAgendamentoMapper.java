package com.barbosa.magalu.business.mapper;

import com.barbosa.magalu.controller.dto.in.AgendamentoInDTO;
import com.barbosa.magalu.controller.dto.out.AgendamentoOutDTO;
import com.barbosa.magalu.infrastructure.entities.Agendamento;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface IAgendamentoMapper {

    Agendamento paraEntity(AgendamentoInDTO agendamento);

    AgendamentoOutDTO paraOut(Agendamento agendamento);
}
