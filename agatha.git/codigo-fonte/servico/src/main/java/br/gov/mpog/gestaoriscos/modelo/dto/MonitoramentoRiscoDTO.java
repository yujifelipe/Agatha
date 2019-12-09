package br.gov.mpog.gestaoriscos.modelo.dto;

import java.util.List;

public class MonitoramentoRiscoDTO extends EntidadeBaseDTO {

    private String fator;

    private List<String> niveis;

    public String getFator() {
        return fator;
    }

    public void setFator(String fator) {
        this.fator = fator;
    }

    public List<String> getNiveis() {
        return niveis;
    }

    public void setNiveis(List<String> niveis) {
        this.niveis = niveis;
    }
}
