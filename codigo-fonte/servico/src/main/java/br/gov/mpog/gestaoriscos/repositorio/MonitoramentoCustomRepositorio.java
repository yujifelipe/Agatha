package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Monitoramento;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.dto.GraficoMonitoramentoDTO;
import java.util.List;

public interface MonitoramentoCustomRepositorio {

    Long countMacroprocessoByFiltro(Monitoramento monitoramento);

    Long countProcessoByFiltro(Monitoramento monitoramento);

    Long countEventoRiscoByFiltro(Monitoramento monitoramento);

    Long countCausaByFiltro(Monitoramento monitoramento);

    Long countConsequenciaByFiltro(Monitoramento monitoramento);

    List<GraficoMonitoramentoDTO> getCategoriaRiscoCountByFiltro(Monitoramento monitoramento);

    GraficoMonitoramentoDTO countNivelRiscoBaixoByFilter(Monitoramento monitoramento);

    GraficoMonitoramentoDTO countNivelRiscoModeradoByFilter(Monitoramento monitoramento);

    GraficoMonitoramentoDTO countNivelRiscoAltoByFilter(Monitoramento monitoramento);

    GraficoMonitoramentoDTO countNivelRiscoCriticoByFilter(Monitoramento monitoramento);

    List<Processo> getProcessosByFiltro(Monitoramento monitoramento);
}
