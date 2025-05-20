package br.com.fiap.medhora.repository;

import br.com.fiap.medhora.model.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Integer> {

    boolean existsByNomePacienteAndMotivoConsulta(String nomePaciente, String motivoConsulta);

    List<AgendamentoEntity> findByNomePaciente(String nomePaciente);

    List<AgendamentoEntity> findByNomePacienteAndDataHoraConsultaAfter(String nomePaciente, LocalDateTime dataHora);

    List<AgendamentoEntity> findByDataHoraConsultaAfter(LocalDateTime dataHora);}
