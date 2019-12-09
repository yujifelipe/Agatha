package br.gov.mpog.gestaoriscos.modelo.dto;

import java.util.List;

public class CalculadoraDTO {

    private Long id;

    private boolean excluido;

    private List<ImpactoCalculadoraDTO> impactos;

    private List<ProbabilidadeCalculadoraDTO> probabilidades;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public List<ImpactoCalculadoraDTO> getImpactos() {
        return impactos;
    }

    public void setImpactos(List<ImpactoCalculadoraDTO> impactos) {
        this.impactos = impactos;
    }

    public List<ProbabilidadeCalculadoraDTO> getProbabilidades() {
        return probabilidades;
    }

    public void setProbabilidades(List<ProbabilidadeCalculadoraDTO> probabilidades) {
        this.probabilidades = probabilidades;
    }
}
