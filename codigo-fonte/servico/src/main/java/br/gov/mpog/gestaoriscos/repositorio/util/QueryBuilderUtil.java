package br.gov.mpog.gestaoriscos.repositorio.util;

import br.gov.mpog.gestaoriscos.modelo.Monitoramento;
import br.gov.mpog.gestaoriscos.modelo.MonitoramentoRisco;
import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import br.gov.mpog.gestaoriscos.util.AnnotationStringUtil;
import br.gov.mpog.gestaoriscos.util.StringUtil;

import java.sql.Timestamp;
import java.util.stream.Collectors;

/**
 * Utility class for handling pagination.
 * <p>
 * <p>
 * Pagination uses the same principles as the <a href="https://developer.github.com/v3/#pagination">Github API</a>,
 * and follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 */
public final class QueryBuilderUtil {

    public static String WHERE = " WHERE ";

    public static String AND = " AND ";

    public static String OR = " OR ";

    private QueryBuilderUtil() {
        throw new IllegalAccessError("Classe Utiliataria");
    }

    public static String taxonomiaOrgaoBaseBuildQuerySearchByDescricao(String entidade, String descricao) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append("SELECT DISTINCT entity.descricao FROM ").append(entidade).append(" entity LEFT OUTER JOIN entity.orgao org WHERE org.id IS NULL AND ( ");

        if (!taxonomiaOrgaoBaseBuildSearchParameters(queryStringBuffer, descricao)) {
            return null;
        }

        queryStringBuffer.append(" ORDER BY entity.descricao ASC");

        return queryStringBuffer.toString();
    }

    public static String taxonomiaOrgaoBaseBuildQueryFindBySearchAndOrgaoId(String entidade, Long orgaoId, String descricao) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append("SELECT DISTINCT entity FROM ").append(entidade).append(" entity LEFT OUTER JOIN entity.orgao org WHERE entity.status = true AND ");

        if (orgaoId != null) {
            queryStringBuffer.append(" (org.id IS NULL  OR org.id = ").append(orgaoId).append(") AND ");
        } else {
            queryStringBuffer.append(" org.id IS NULL AND ");
        }

        if (!taxonomiaOrgaoBaseBuildSearchParameters(queryStringBuffer, descricao)) {
            return null;
        }

        queryStringBuffer.append(" ORDER BY entity.descricao ASC");

        return queryStringBuffer.toString();
    }

    private static Boolean taxonomiaOrgaoBaseBuildSearchParameters(StringBuffer queryStringBuffer, String descricao) {
        descricao = descricao.trim();
        String[] search = descricao.split(" ");

        Boolean hasQuery = false;
        for (String aSearch : search) {

            if (aSearch.length() >= AnnotationNumberUtil.L3) {
                if (hasQuery) {
                    queryStringBuffer.append(" OR ");
                }
                queryStringBuffer.append(" LOWER(entity.search) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE).append(StringUtil.removerAcento(aSearch)).append(AnnotationStringUtil.QUERY_LIKE).append("') ");
                hasQuery = true;
            }
        }

        queryStringBuffer.append(" ) ");

        return hasQuery;
    }

    private static void adicionaAnd(StringBuffer queryStringBuffer) {
        if (queryStringBuffer.length() > 0) {
            queryStringBuffer.append(AND);
        }
    }

    private static void adicionaOr(StringBuffer queryStringBuffer) {
        if (queryStringBuffer.length() > 0) {
            queryStringBuffer.append(OR);
        }
    }

    public static String getMonitoramentoJoin() {
        StringBuffer queryStringBuffer = new StringBuffer();

//      Status do processo
        queryStringBuffer.append("LEFT OUTER JOIN p.status sts ");
//      Macroprocessos
        queryStringBuffer.append("LEFT OUTER JOIN p.macroprocesso mp ");
//      Secretarias
        queryStringBuffer.append("LEFT OUTER JOIN p.analise a ");
        queryStringBuffer.append("LEFT OUTER JOIN a.secretaria sc ");
//      Fatores
        queryStringBuffer.append("LEFT OUTER JOIN p.calculadora cl ");
        queryStringBuffer.append("LEFT OUTER JOIN cl.impactos imp ");
//      Categorias
        queryStringBuffer.append("LEFT OUTER JOIN p.identificacao i ");
        queryStringBuffer.append("LEFT OUTER JOIN i.eventos e ");
        queryStringBuffer.append("LEFT OUTER JOIN e.categoria ct ");
//      Nivel Residual
        queryStringBuffer.append("LEFT OUTER JOIN e.calculoRiscoResidual crr ");
        queryStringBuffer.append("LEFT OUTER JOIN crr.pesos pr ");
//      Nivel Inerente
        queryStringBuffer.append("LEFT OUTER JOIN e.calculoRiscoInerente cri ");
        queryStringBuffer.append("LEFT OUTER JOIN cri.pesos pi ");
//      Planos de Controle
        queryStringBuffer.append("LEFT OUTER JOIN e.planos plc ");

        return queryStringBuffer.toString();
    }

    public static String getMonitoramentoWhere(Monitoramento monitoramento) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append(getDecisaoWhere());

        if (!monitoramento.getSecretarias().isEmpty()) {
            adicionaAnd(queryStringBuffer);
            queryStringBuffer.append(getSecretariaWhere(monitoramento));
        }

        String andsWhere = getMonitoramentoAnd(monitoramento);

        if (andsWhere.length() > 0) {
            adicionaAnd(queryStringBuffer);
            queryStringBuffer.append(andsWhere);
        }

        String orsWhere = getMonitoramentoOr(monitoramento);

        if (orsWhere.length() > 0) {
            adicionaAnd(queryStringBuffer);
            queryStringBuffer.append(orsWhere);
        }

        return queryStringBuffer.toString();
    }

    private static String getMonitoramentoAnd(Monitoramento monitoramento) {
        StringBuffer queryStringBuffer = new StringBuffer();

        if (!monitoramento.getOperadorMacropocesso() && !monitoramento.getMacroprocessos().isEmpty()) {
            queryStringBuffer.append(getMacroprocessoWhere(monitoramento));
        }

        if (!monitoramento.getOperadorCategoria() && !monitoramento.getCategorias().isEmpty()) {
            adicionaAnd(queryStringBuffer);
            queryStringBuffer.append(getCategoriaWhere(monitoramento));
        }

        if (!monitoramento.getOperadorIntegridade() && !monitoramento.getIntegridades().isEmpty() && monitoramento.getIntegridades().split(",").length == 1) {
            adicionaAnd(queryStringBuffer);
            queryStringBuffer.append(getIntegridadeWhere(monitoramento));
        }

        if (!monitoramento.getOperadorResidual() && !monitoramento.getRiscosResiduais().isEmpty()) {
            adicionaAnd(queryStringBuffer);
            queryStringBuffer.append(getRiscoResidualWhere(monitoramento));
        }

        if (!monitoramento.getOperadorInerente() && !monitoramento.getRiscosInerentes().isEmpty()) {
            adicionaAnd(queryStringBuffer);
            queryStringBuffer.append(getRiscoInerenteWhere(monitoramento));
        }

        if (!monitoramento.getOperadorConclusao() && monitoramento.getDtInicio() != null && monitoramento.getDtFim() != null) {
            adicionaAnd(queryStringBuffer);
            queryStringBuffer.append(getConclusaoWhere(monitoramento));
        }

        if (queryStringBuffer.length() > 0) {
            return " ( " + queryStringBuffer.toString() + " ) ";
        }
        return "";
    }

    private static String getMonitoramentoOr(Monitoramento monitoramento) {
        StringBuffer queryStringBuffer = new StringBuffer();

        if (monitoramento.getOperadorMacropocesso() && !monitoramento.getMacroprocessos().isEmpty()) {
            queryStringBuffer.append(getMacroprocessoWhere(monitoramento));
        }

        if (monitoramento.getOperadorCategoria() && !monitoramento.getCategorias().isEmpty()) {
            adicionaOr(queryStringBuffer);
            queryStringBuffer.append(getCategoriaWhere(monitoramento));
        }

        if (monitoramento.getOperadorIntegridade() && !monitoramento.getIntegridades().isEmpty() && monitoramento.getIntegridades().split(",").length == 1) {
            adicionaOr(queryStringBuffer);
            queryStringBuffer.append(getIntegridadeWhere(monitoramento));
        }

        if (monitoramento.getOperadorResidual() && !monitoramento.getRiscosResiduais().isEmpty()) {
            adicionaOr(queryStringBuffer);
            queryStringBuffer.append(getRiscoResidualWhere(monitoramento));
        }

        if (monitoramento.getOperadorInerente() && !monitoramento.getRiscosInerentes().isEmpty()) {
            adicionaOr(queryStringBuffer);
            queryStringBuffer.append(getRiscoInerenteWhere(monitoramento));
        }

        if (monitoramento.getOperadorConclusao() && monitoramento.getDtInicio() != null && monitoramento.getDtFim() != null) {
            adicionaOr(queryStringBuffer);
            queryStringBuffer.append(getConclusaoWhere(monitoramento));
        }

        if (queryStringBuffer.length() > 0) {
            return " ( " + queryStringBuffer.toString() + " ) ";
        }
        return "";
    }

    private static String getDecisaoWhere() {
        return " sts.nome = 'Finalizado' ";
    }

    public static String getSecretariaWhere(Monitoramento monitoramento) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append(" sc.id IN ( ");
        queryStringBuffer.append(monitoramento.getSecretarias().stream().map(orgao -> String.valueOf(orgao.getId())).collect(Collectors.joining(", ")));
        queryStringBuffer.append(" ) ");

        return queryStringBuffer.toString();
    }

    public static String getMacroprocessoWhere(Monitoramento monitoramento) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append(" mp.id IN ( ");
        queryStringBuffer.append(monitoramento.getMacroprocessos().stream().map(macroprocesso -> String.valueOf(macroprocesso.getId())).collect(Collectors.joining(", ")));
        queryStringBuffer.append(" ) ");

        return queryStringBuffer.toString();
    }

    private static String getCategoriaWhere(Monitoramento monitoramento) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append(" ct.id IN ( ");
        queryStringBuffer.append(monitoramento.getCategorias().stream().map(categoria -> String.valueOf(categoria.getId())).collect(Collectors.joining(", ")));
        queryStringBuffer.append(" ) ");

        return queryStringBuffer.toString();
    }

    private static String getIntegridadeWhere(Monitoramento monitoramento) {
        StringBuffer queryStringBuffer = new StringBuffer();
        queryStringBuffer.append(" e.riscoIntegridade = ");

        String risco = monitoramento.getIntegridades().split(",")[0];

        queryStringBuffer.append(risco);
        queryStringBuffer.append(" ");

        return queryStringBuffer.toString();
    }

    private static String getRiscoResidualWhere(Monitoramento monitoramento) {
        StringBuffer queryStringBuffer = new StringBuffer();

        for (MonitoramentoRisco risco : monitoramento.getRiscosResiduais()) {
            if (queryStringBuffer.toString().length() > 0) {
                queryStringBuffer.append(QueryBuilderUtil.OR);
            } else {
                queryStringBuffer.append(" ( ");
            }

            queryStringBuffer.append(" imp.nome = '");
            queryStringBuffer.append(risco.getFator());
            queryStringBuffer.append("' ");

            queryStringBuffer.append(QueryBuilderUtil.AND);

            queryStringBuffer.append(" pr.peso IN ( ");
            queryStringBuffer.append(risco.getNiveis());
            queryStringBuffer.append(" ) ");

        }

        queryStringBuffer.append(" ) ");

        return queryStringBuffer.toString();
    }

    private static String getRiscoInerenteWhere(Monitoramento monitoramento) {
        StringBuffer queryStringBuffer = new StringBuffer();

        for (MonitoramentoRisco risco : monitoramento.getRiscosInerentes()) {
            if (queryStringBuffer.toString().length() > 0) {
                queryStringBuffer.append(QueryBuilderUtil.OR);
            } else {
                queryStringBuffer.append(" ( ");
            }

            queryStringBuffer.append(" imp.nome = '");
            queryStringBuffer.append(risco.getFator());
            queryStringBuffer.append("' ");

            queryStringBuffer.append(QueryBuilderUtil.AND);

            queryStringBuffer.append(" pi.peso IN ( ");
            queryStringBuffer.append(risco.getNiveis());
            queryStringBuffer.append(" ) ");

        }

        queryStringBuffer.append(" ) ");

        return queryStringBuffer.toString();
    }

    private static String getConclusaoWhere(Monitoramento monitoramento) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append(" plc.dtConclusao BETWEEN '");
        queryStringBuffer.append(new Timestamp(monitoramento.getDtInicio().getTimeInMillis()));
        queryStringBuffer.append("'");
        queryStringBuffer.append(QueryBuilderUtil.AND);
        queryStringBuffer.append("'");
        queryStringBuffer.append(new Timestamp(monitoramento.getDtFim().getTimeInMillis()));
        queryStringBuffer.append("'");
        queryStringBuffer.append(" ");


        return queryStringBuffer.toString();
    }
}
