package com.barbosa.magalu.business;

import com.barbosa.magalu.business.mapper.IAgendamentoMapper;
import com.barbosa.magalu.controller.dto.in.AgendamentoInDTO;
import com.barbosa.magalu.controller.dto.out.AgendamentoOutDTO;
import com.barbosa.magalu.infrastructure.entities.Agendamento;
import com.barbosa.magalu.infrastructure.enums.EnumStatusNotificacao;
import com.barbosa.magalu.infrastructure.exception.NotFoundException;
import com.barbosa.magalu.infrastructure.repositories.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private IAgendamentoMapper iAgendamentoMapper;

    private AgendamentoInDTO agendamentoInDTO;
    private AgendamentoOutDTO agendamentoOutDTO;
    private Agendamento agendamentoEntity;

    @BeforeEach
    void setUp (){

        agendamentoEntity = new Agendamento(1L, "email@email.com", "1111",
                LocalDateTime.of(2025, 1, 2, 11, 11 , 11),
                LocalDateTime.now(),
                null,
                "teste",
                EnumStatusNotificacao.AGENDADO

        );

        agendamentoInDTO = new AgendamentoInDTO("email@email.com", "1111", "teste", LocalDateTime.of(2025, 1, 2, 11, 11 , 11));
        agendamentoOutDTO = new AgendamentoOutDTO(1L,
                "email@email.com",
                "1111",
                "teste",
                LocalDateTime.of(2025, 1, 2, 11, 11 , 11),
                EnumStatusNotificacao.AGENDADO
        );
    }

    @Test
    void deveGravarAgendamentoComSucesso(){
        when(iAgendamentoMapper.paraEntity(agendamentoInDTO)).thenReturn(agendamentoEntity);
        when(agendamentoRepository.save(agendamentoEntity)).thenReturn(agendamentoEntity);
        when(iAgendamentoMapper.paraOut(agendamentoEntity)).thenReturn(agendamentoOutDTO);

        AgendamentoOutDTO out = agendamentoService.gravarAgendamento(agendamentoInDTO);

        verify(iAgendamentoMapper, times(1)).paraEntity(agendamentoInDTO);
        verify(agendamentoRepository, times(1)).save(agendamentoEntity);
        verify(iAgendamentoMapper, times(1)).paraOut(agendamentoEntity);
        assertThat(out).usingRecursiveComparison().isEqualTo(agendamentoOutDTO);
    }

    @Test
    void deveBuscarAgendamentoPorIdComSucesso(){
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamentoEntity));
        when(iAgendamentoMapper.paraOut(agendamentoEntity)).thenReturn(agendamentoOutDTO);

        AgendamentoOutDTO out = agendamentoService.buscarAgendamentoPorId(1L);

        verify(agendamentoRepository, times(1)).findById(1L);
        verify(iAgendamentoMapper, times(1)).paraOut(agendamentoEntity);
        assertThat(out).usingRecursiveComparison().isEqualTo(agendamentoOutDTO);
    }

    @Test
    void deveBuscarAgendamentoPorIdComExcecao(){
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            agendamentoService.buscarAgendamentoPorId(1L);
        });

        verifyNoMoreInteractions(iAgendamentoMapper);
        assertEquals("Agendamento não encontrado", exception.getMessage());
    }

    @Test
    void deveCancelarAgendamentoComSucesso(){

        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamentoEntity));

        Agendamento agendamentoCancelado = new Agendamento();
        agendamentoCancelado.setId(1L);
        agendamentoCancelado.setStatusNotificacao(EnumStatusNotificacao.CANCELADO);

        when(iAgendamentoMapper.paraEntityCancelamento(agendamentoEntity)).thenReturn(agendamentoCancelado);

        agendamentoService.cancelarAgendamento(1L);

        ArgumentCaptor<Agendamento> captor = ArgumentCaptor.forClass(Agendamento.class);

        verify(agendamentoRepository).save(captor.capture());
        Agendamento agendamentoSalvo = captor.getValue();

        assertEquals(EnumStatusNotificacao.CANCELADO, agendamentoSalvo.getStatusNotificacao());

        verify(agendamentoRepository, times(1)).findById(1L);
        verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
        verify(iAgendamentoMapper, times(1)).paraEntityCancelamento(agendamentoEntity);
    }

}
