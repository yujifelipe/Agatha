package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.HistoricoEventoRisco;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HistoricoEventoRisco entity.
 */
public interface HistoricoEventoRiscoRepository extends JpaRepository<HistoricoEventoRisco, Long> {
}