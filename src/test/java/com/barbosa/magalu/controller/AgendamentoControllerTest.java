package com.barbosa.magalu.controller;

import com.barbosa.magalu.business.AgendamentoService;
import com.barbosa.magalu.controller.dto.in.AgendamentoInDTO;
import com.barbosa.magalu.controller.dto.out.AgendamentoOutDTO;

import com.barbosa.magalu.infrastructure.enums.EnumStatusNotificacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AgendamentoControllerTest {

    @InjectMocks
    AgendamentoController agendamentoController;

    @Mock
    AgendamentoService service;



    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private AgendamentoInDTO agendamentoInDTO;
    private AgendamentoOutDTO agendamentoOutDTO;

    @BeforeEach
    void setUp (){

        mockMvc = MockMvcBuilders.standaloneSetup(agendamentoController).build();

        objectMapper.registerModule(new JavaTimeModule());

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
    void deveCriarAgendamentoComSucesso() throws Exception {
        when(service.gravarAgendamento(agendamentoInDTO)).thenReturn(agendamentoOutDTO);

        mockMvc.perform(post("/agendamento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agendamentoInDTO))

                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.emailDestinatario").value("email@email.com"))
                .andExpect(jsonPath("$.telefoneDestinatario").value("1111"))
                .andExpect(jsonPath("$.mensagem").value("teste"))
                .andExpect(jsonPath("$.dataHoraEnvio").exists())
                .andExpect(jsonPath("$.statusNotificacao").exists());

        verify(service, times(1)).gravarAgendamento(agendamentoInDTO);
        ;
    }

    @Test
    void deveBuscarAgendamentoPorIdComSucesso() throws Exception{
        when(service.buscarAgendamentoPorId(1L)).thenReturn(agendamentoOutDTO);

        mockMvc.perform(get("/agendamento/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.emailDestinatario").value("email@email.com"))
                .andExpect(jsonPath("$.telefoneDestinatario").value("1111"))
                .andExpect(jsonPath("$.mensagem").value("teste"))
                .andExpect(jsonPath("$.dataHoraEnvio").exists())
                .andExpect(jsonPath("$.statusNotificacao").exists());

        verify(service, times(1)).buscarAgendamentoPorId(1L);
    }

    @Test
    void deveCancelarAgendamentoComSucesso()throws Exception{
        doNothing().when(service).cancelarAgendamento(1L);

        mockMvc.perform(patch("/agendamento/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(service, times(1)).cancelarAgendamento(1L);

    }
}
