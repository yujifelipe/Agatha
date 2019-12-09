package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Macroprocesso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Macroprocesso entity.
 */
public interface MacroprocessoRepository extends JpaRepository<Macroprocesso, Long> {

    Macroprocesso findBySearchIgnoreCase(String descricao);

    Page<Macroprocesso> findBySearchContainingIgnoreCaseOrderByDescricaoAsc(String descricao, Pageable pageable);

    Page<Macroprocesso> findBySearchContainingIgnoreCaseAndStatusOrderByDescricaoAsc(String descricao, Boolean status, Pageable pageable);

    List<Macroprocesso> findByStatusTrueOrderByDescricaoAsc();

    List<Macroprocesso> findBySecretariaIdAndStatusTrueOrderByDescricaoAsc(Long secretariaId);

    Page<Macroprocesso> findBySearchContainingIgnoreCaseAndStatusAndSecretariaIdOrderByDescricaoAsc(String descricao, Boolean status, Long secretariaId, Pageable pageable);

    Page<Macroprocesso> findBySearchContainingIgnoreCaseAndSecretariaIdOrderByDescricaoAsc(String descricao, Long secretariaId, Pageable pageable);
}