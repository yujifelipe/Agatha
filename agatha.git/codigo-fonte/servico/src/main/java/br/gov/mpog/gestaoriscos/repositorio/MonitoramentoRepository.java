package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Monitoramento;
import java.util.Calendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MonitoramentoRepository extends JpaRepository<Monitoramento, Long> {

    Page<Monitoramento> findByOrgaoIdAndPerfilNucleoOrderByDtCadastroDesc(Long orgaoId, Boolean isPerfilNucleo, Pageable pageable);

    @Query("SELECT mo.dtCadastro FROM Monitoramento mo WHERE mo.id = :monitoramentoId")
    Calendar getDataCadastroById(@Param("monitoramentoId") Long monitoramentoId);

}
