package br.gov.mpog.gestaoriscos.modelo.dto;

public class MacroprocessoDTO extends EntidadeBaseDescricaoStatusSearchDTO {

    private Long secretariaId;

    private String secretariaSigla;

    public Long getSecretariaId() {
        return secretariaId;
    }

    public void setSecretariaId(Long secretariaId) {
        this.secretariaId = secretariaId;
    }

    public String getSecretariaSigla() {
        return secretariaSigla;
    }

    public void setSecretariaSigla(String secretariaSigla) {
        this.secretariaSigla = secretariaSigla;
    }
}