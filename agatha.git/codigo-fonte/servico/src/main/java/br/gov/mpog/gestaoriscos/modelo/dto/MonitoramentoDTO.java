package br.gov.mpog.gestaoriscos.modelo.dto;

import java.util.Calendar;
import java.util.List;

public class MonitoramentoDTO extends EntidadeBaseDTO {

    private String nome;

    private OrgaoDTO orgao;

    private Calendar dtCadastro;

    private Boolean perfilNucleo;

    private List<OrgaoDTO> secretarias;

    private Boolean operadorMacropocesso;

    private List<MacroprocessoDTO> macroprocessos;

    private Boolean operadorCategoria;

    private List<CategoriaDTO> categorias;

    private Boolean operadorIntegridade;

    private List<String> integridades;

    private Boolean operadorNivelRisco;

    private List<String> niveisRisco;

    private Boolean operadorResidual;

    private List<MonitoramentoRiscoDTO> riscosResiduais;

    private Boolean operadorInerente;

    private List<MonitoramentoRiscoDTO> riscosInerentes;

    private Boolean operadorConclusao;

    private Calendar dtInicio;

    private Calendar dtFim;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public OrgaoDTO getOrgao() {
        return orgao;
    }

    public void setOrgao(OrgaoDTO orgao) {
        this.orgao = orgao;
    }

    public Calendar getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Calendar dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public Boolean getPerfilNucleo() {
        return perfilNucleo;
    }

    public void setPerfilNucleo(Boolean perfilNucleo) {
        this.perfilNucleo = perfilNucleo;
    }

    public List<OrgaoDTO> getSecretarias() {
        return secretarias;
    }

    public void setSecretarias(List<OrgaoDTO> secretarias) {
        this.secretarias = secretarias;
    }

    public Boolean getOperadorMacropocesso() {
        return operadorMacropocesso;
    }

    public void setOperadorMacropocesso(Boolean operadorMacropocesso) {
        this.operadorMacropocesso = operadorMacropocesso;
    }

    public List<MacroprocessoDTO> getMacroprocessos() {
        return macroprocessos;
    }

    public void setMacroprocessos(List<MacroprocessoDTO> macroprocessos) {
        this.macroprocessos = macroprocessos;
    }

    public Boolean getOperadorCategoria() {
        return operadorCategoria;
    }

    public void setOperadorCategoria(Boolean operadorCategoria) {
        this.operadorCategoria = operadorCategoria;
    }

    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public Boolean getOperadorIntegridade() {
        return operadorIntegridade;
    }

    public void setOperadorIntegridade(Boolean operadorIntegridade) {
        this.operadorIntegridade = operadorIntegridade;
    }

    public List<String> getIntegridades() {
        return integridades;
    }

    public void setIntegridades(List<String> integridades) {
        this.integridades = integridades;
    }

    public Boolean getOperadorNivelRisco() {
        return operadorNivelRisco;
    }

    public void setOperadorNivelRisco(Boolean operadorNivelRisco) {
        this.operadorNivelRisco = operadorNivelRisco;
    }

    public List<String> getNiveisRisco() {
        return niveisRisco;
    }

    public void setNiveisRisco(List<String> niveisRisco) {
        this.niveisRisco = niveisRisco;
    }

    public Boolean getOperadorResidual() {
        return operadorResidual;
    }

    public void setOperadorResidual(Boolean operadorResidual) {
        this.operadorResidual = operadorResidual;
    }

    public List<MonitoramentoRiscoDTO> getRiscosResiduais() {
        return riscosResiduais;
    }

    public void setRiscosResiduais(List<MonitoramentoRiscoDTO> riscosResiduais) {
        this.riscosResiduais = riscosResiduais;
    }

    public Boolean getOperadorInerente() {
        return operadorInerente;
    }

    public void setOperadorInerente(Boolean operadorInerente) {
        this.operadorInerente = operadorInerente;
    }

    public List<MonitoramentoRiscoDTO> getRiscosInerentes() {
        return riscosInerentes;
    }

    public void setRiscosInerentes(List<MonitoramentoRiscoDTO> riscosInerentes) {
        this.riscosInerentes = riscosInerentes;
    }

    public Boolean getOperadorConclusao() {
        return operadorConclusao;
    }

    public void setOperadorConclusao(Boolean operadorConclusao) {
        this.operadorConclusao = operadorConclusao;
    }

    public Calendar getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Calendar dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Calendar getDtFim() {
        return dtFim;
    }

    public void setDtFim(Calendar dtFim) {
        this.dtFim = dtFim;
    }
}
