package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Acompanhamento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Acompanhamento entity.
 */
public interface AcompanhamentoRepository extends JpaRepository<Acompanhamento, Long> {

    List<Acompanhamento> findByPlanoControleIdOrderByDtCadastroDesc(Long planoControleId);

}