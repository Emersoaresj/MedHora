package br.com.fiap.medhora.infrastructure.repository;

import br.com.fiap.medhora.model.NotificacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificacaoRepository extends JpaRepository<NotificacaoEntity, Integer> {

    boolean existsByNomePacienteAndDataHoraConsulta(String nomePaciente, LocalDateTime dataHoraConsulta);
}
