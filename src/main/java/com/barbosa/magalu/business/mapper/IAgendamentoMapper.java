package com.barbosa.magalu.business.mapper;

import com.barbosa.magalu.controller.dto.in.AgendamentoInDTO;
import com.barbosa.magalu.controller.dto.out.AgendamentoOutDTO;
import com.barbosa.magalu.infrastructure.entities.Agendamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface IAgendamentoMapper {

    Agendamento paraEntity(AgendamentoInDTO agendamento);

    AgendamentoOutDTO paraOut(Agendamento agendamento);

    @Mapping(target = "dataHoraModificacao", expression = "java(LocalDateTime.now())")
    @Mapping(target = "statusNotificacao", expression = "java(EnumStatusNotificacao.CANCELADO)")
    Agendamento paraEntityCancelamento(Agendamento agendamento);
}
