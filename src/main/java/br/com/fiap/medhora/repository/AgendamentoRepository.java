package br.com.fiap.medhora.repository;

import br.com.fiap.medhora.model.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Integer> {

    boolean existsByNomePacienteAndMotivoConsulta(String nomePaciente, String motivoConsulta);
}
