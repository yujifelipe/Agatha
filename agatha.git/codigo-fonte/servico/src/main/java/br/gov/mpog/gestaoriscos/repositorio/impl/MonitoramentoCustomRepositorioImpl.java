package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.modelo.Monitoramento;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.dto.GraficoMonitoramentoDTO;
import br.gov.mpog.gestaoriscos.repositorio.MonitoramentoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.util.QueryBuilderUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MonitoramentoCustomRepositorioImpl implements MonitoramentoCustomRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long countMacroprocessoByFiltro(Monitoramento monitoramento) {
        String queryString = "SELECT COUNT (DISTINCT mp) FROM Macroprocesso mp ";

        if (!monitoramento.getSecretarias().isEmpty()) {
            queryString += " LEFT OUTER JOIN mp.secretaria sc ";
        }

        String queryWhere = "";

        if (!monitoramento.getSecretarias().isEmpty()) {

            queryWhere += QueryBuilderUtil.WHERE;
            queryWhere += QueryBuilderUtil.getSecretariaWhere(monitoramento);

        }

        if (!monitoramento.getMacroprocessos().isEmpty()) {

            if (queryWhere.isEmpty()) {
                queryWhere += QueryBuilderUtil.WHERE;
            } else {
                queryWhere += QueryBuilderUtil.AND;
            }

            queryWhere += QueryBuilderUtil.getMacroprocessoWhere(monitoramento);

        }

        queryString += queryWhere;

        return em.createQuery(queryString, Long.class).getSingleResult();
    }

    @Override
    public Long countProcessoByFiltro(Monitoramento monitoramento) {
        String queryString = "SELECT COUNT (DISTINCT p) FROM Processo p " +
                QueryBuilderUtil.getMonitoramentoJoin() +
                " WHERE p.processo IS NOT NULL AND p.processo <> '' ";

        String monitoramentoWhere = QueryBuilderUtil.getMonitoramentoWhere(validaMonitoramento(monitoramento));

        if (monitoramentoWhere.length() > 0) {
            queryString += QueryBuilderUtil.AND;
            queryString += monitoramentoWhere;
        }

        return em.createQuery(queryString, Long.class).getSingleResult();
    }

    @Override
    public Long countEventoRiscoByFiltro(Monitoramento monitoramento) {
        String queryString = "SELECT COUNT (DISTINCT e) FROM Processo p " +
                QueryBuilderUtil.getMonitoramentoJoin() +
                " WHERE e.id IS NOT NULL ";

        String monitoramentoWhere = QueryBuilderUtil.getMonitoramentoWhere(validaMonitoramento(monitoramento));

        if (monitoramentoWhere.length() > 0) {
            queryString += QueryBuilderUtil.AND;
            queryString += monitoramentoWhere;
        }

        return em.createQuery(queryString, Long.class).getSingleResult();
    }

    @Override
    public Long countCausaByFiltro(Monitoramento monitoramento) {
        String queryString = "SELECT COUNT (DISTINCT c) FROM Processo p " +
                QueryBuilderUtil.getMonitoramentoJoin() +
                " LEFT OUTER JOIN e.causas c " +
                "WHERE c.id IS NOT NULL ";

        String monitoramentoWhere = QueryBuilderUtil.getMonitoramentoWhere(validaMonitoramento(monitoramento));

        if (monitoramentoWhere.length() > 0) {
            queryString += QueryBuilderUtil.AND;
            queryString += monitoramentoWhere;
        }

        return em.createQuery(queryString, Long.class).getSingleResult();
    }

    @Override
    public Long countConsequenciaByFiltro(Monitoramento monitoramento) {
        String queryString = "SELECT COUNT (DISTINCT e) FROM Processo p " +
                QueryBuilderUtil.getMonitoramentoJoin() +
                " LEFT OUTER JOIN e.consequencias c " +
                "WHERE c.id IS NOT NULL ";

        String monitoramentoWhere = QueryBuilderUtil.getMonitoramentoWhere(validaMonitoramento(monitoramento));

        if (monitoramentoWhere.length() > 0) {
            queryString += QueryBuilderUtil.AND;
            queryString += monitoramentoWhere;
        }

        return em.createQuery(queryString, Long.class).getSingleResult();
    }

    @Override
    public List<GraficoMonitoramentoDTO> getCategoriaRiscoCountByFiltro(Monitoramento monitoramento) {
        String queryString = "SELECT DISTINCT e.id FROM Processo p " +
                QueryBuilderUtil.getMonitoramentoJoin() +
                " WHERE e.id IS NOT NULL ";

        String monitoramentoWhere = QueryBuilderUtil.getMonitoramentoWhere(validaMonitoramento(monitoramento));

        if (monitoramentoWhere.length() > 0) {
            queryString += QueryBuilderUtil.AND;
            queryString += monitoramentoWhere;
        }

        String queryCategoria = "SELECT new br.gov.mpog.gestaoriscos.modelo.dto.GraficoMonitoramentoDTO(categoria.descricao, count ( categoria.id) ) FROM EventoRisco evento " +
                "INNER JOIN evento.categoria categoria " +
                "WHERE evento.id IN (" + queryString + ") " +
                "GROUP BY categoria.descricao ";

        return em.createQuery(queryCategoria, GraficoMonitoramentoDTO.class).getResultList();
    }

    @Override
    public GraficoMonitoramentoDTO countNivelRiscoBaixoByFilter(Monitoramento monitoramento) {
        String queryString = "SELECT new br.gov.mpog.gestaoriscos.modelo.dto.GraficoMonitoramentoDTO('Pequeno', COUNT ( DISTINCT crr.id) ) FROM Processo p " +
                QueryBuilderUtil.getMonitoramentoJoin() +
                " WHERE crr.nivel <= 3 ";

        String monitoramentoWhere = QueryBuilderUtil.getMonitoramentoWhere(validaMonitoramento(monitoramento));

        if (monitoramentoWhere.length() > 0) {
            queryString += QueryBuilderUtil.AND;
            queryString += monitoramentoWhere;
        }

        return em.createQuery(queryString, GraficoMonitoramentoDTO.class).getSingleResult();
    }

    @Override
    public GraficoMonitoramentoDTO countNivelRiscoModeradoByFilter(Monitoramento monitoramento) {
        String queryString = "SELECT new br.gov.mpog.gestaoriscos.modelo.dto.GraficoMonitoramentoDTO('Moderado', COUNT ( DISTINCT crr.id) ) FROM Processo p " +
                QueryBuilderUtil.getMonitoramentoJoin() +
                " WHERE crr.nivel >= 4 AND crr.nivel <= 6 ";

        String monitoramentoWhere = QueryBuilderUtil.getMonitoramentoWhere(validaMonitoramento(monitoramento));

        if (monitoramentoWhere.length() > 0) {
            queryString += QueryBuilderUtil.AND;
            queryString += monitoramentoWhere;
        }

        return em.createQuery(queryString, GraficoMonitoramentoDTO.class).getSingleResult();
    }

    @Override
    public GraficoMonitoramentoDTO countNivelRiscoAltoByFilter(Monitoramento monitoramento) {
        String queryString = "SELECT new br.gov.mpog.gestaoriscos.modelo.dto.GraficoMonitoramentoDTO('Alto', COUNT ( DISTINCT crr.id) ) FROM Processo p " +
                QueryBuilderUtil.getMonitoramentoJoin() +
                " WHERE crr.nivel >= 7 AND crr.nivel <= 15 ";

        String monitoramentoWhere = QueryBuilderUtil.getMonitoramentoWhere(validaMonitoramento(monitoramento));

        if (monitoramentoWhere.length() > 0) {
            queryString += QueryBuilderUtil.AND;
            queryString += monitoramentoWhere;
        }

        return em.createQuery(queryString, GraficoMonitoramentoDTO.class).getSingleResult();
    }

    @Override
    public GraficoMonitoramentoDTO countNivelRiscoCriticoByFilter(Monitoramento monitoramento) {
        String queryString = "SELECT new br.gov.mpog.gestaoriscos.modelo.dto.GraficoMonitoramentoDTO('Crítico', COUNT ( DISTINCT crr.id) ) FROM Processo p " +
                QueryBuilderUtil.getMonitoramentoJoin() +
                " WHERE crr.nivel >= 16 ";

        String monitoramentoWhere = QueryBuilderUtil.getMonitoramentoWhere(validaMonitoramento(monitoramento));

        if (monitoramentoWhere.length() > 0) {
            queryString += QueryBuilderUtil.AND;
            queryString += monitoramentoWhere;
        }

        return em.createQuery(queryString, GraficoMonitoramentoDTO.class).getSingleResult();
    }

    @Override
    public List<Processo> getProcessosByFiltro(Monitoramento monitoramento) {
        String queryString = "SELECT DISTINCT p FROM Processo p " +
                QueryBuilderUtil.getMonitoramentoJoin() +
                " WHERE p.processo IS NOT NULL AND p.processo <> '' ";

        String monitoramentoWhere = QueryBuilderUtil.getMonitoramentoWhere(validaMonitoramento(monitoramento));

        if (monitoramentoWhere.length() > 0) {
            queryString += QueryBuilderUtil.AND;
            queryString += monitoramentoWhere;
        }

        return em.createQuery(queryString, Processo.class).getResultList();
    }

    private Monitoramento validaMonitoramento(Monitoramento monitoramento) {
        if (monitoramento.getSecretarias() == null) {
            monitoramento.setSecretarias(new ArrayList<>(0));
        }

        if (monitoramento.getMacroprocessos() == null) {
            monitoramento.setMacroprocessos(new ArrayList<>(0));
        }

        if (monitoramento.getCategorias() == null) {
            monitoramento.setCategorias(new ArrayList<>(0));
        }

        return monitoramento;
    }
}