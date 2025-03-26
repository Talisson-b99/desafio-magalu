package com.barbosa.magalu.infrastructure.repositories;

import com.barbosa.magalu.infrastructure.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
