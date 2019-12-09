package br.gov.mpog.gestaoriscos.modelo.dto;

import java.util.Calendar;
import java.util.List;

public class AcompanhamentoDTO extends EntidadeBaseDTO {

    private String status;

    private Calendar dtCadastro;

    private String justificativa;

    private String implementado;

    private Long planoControleId;

    private List<ArquivoAnexoDTO> anexos;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Calendar getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Calendar dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getImplementado() {
        return implementado;
    }

    public void setImplementado(String implementado) {
        this.implementado = implementado;
    }

    public Long getPlanoControleId() {
        return planoControleId;
    }

    public void setPlanoControleId(Long planoControleId) {
        this.planoControleId = planoControleId;
    }

    public List<ArquivoAnexoDTO> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<ArquivoAnexoDTO> anexos) {
        this.anexos = anexos;
    }
}