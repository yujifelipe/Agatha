package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.PlanoControle;
import br.gov.mpog.gestaoriscos.modelo.dto.CategoriaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.GraficoMonitoramentoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MonitoramentoDetalhadoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MacroprocessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MonitoramentoDTO;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MonitoramentoService {


    MonitoramentoDTO save(MonitoramentoDTO monitoramentoDTO);

    /**
     * Get all the monitoramentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MonitoramentoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" monitoramentos.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MonitoramentoDTO findOne(Long id);

    /**
     * Delete the "id" monitoramentos.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<MacroprocessoDTO> getAllMacroProcessos();

    List<CategoriaDTO> getAllCategorias();

    MonitoramentoDetalhadoDTO gerarGrafico(MonitoramentoDTO monitoramentoDTO);

    List<GraficoMonitoramentoDTO> gerarGraficoRiscoResidual(MonitoramentoDTO monitoramentoDTO);

    List<GraficoMonitoramentoDTO> gerarGraficoCategoriaRisco(MonitoramentoDTO monitoramentoDTO);

    byte[] gerarRelatorioMonitoramento(MonitoramentoDTO monitoramentoDTO) throws IOException;

    String getPlanoControleStatus(PlanoControle planoControle);
}
