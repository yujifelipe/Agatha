package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;

import java.util.Calendar;
import java.util.List;
import javax.validation.constraints.Size;

public class EventoRiscoDTO {

    private Long id;

    private Calendar dtAtualizacao;

    private boolean excluido;

    private Boolean riscoIntegridade;

    private IdentificacaoDTO identificacao;

    private EventoDTO evento;

    private CategoriaDTO categoria;

    private List<EventoCausaDTO> causas;

    private List<EventoConsequenciaDTO> consequencias;

    private List<ControleEventoDTO> controleEventos;

    private CalculoRiscoDTO calculoRiscoInerente;

    private CalculoRiscoDTO calculoRiscoResidual;

    @Size(max = AnnotationNumberUtil.L4000)
    private String justificativaRespostaRisco;

    private RespostaRiscoDTO respostaRisco;

    private List<PlanoControleDTO> controles;

    private List<HistoricoEventoRiscoDTO> historico;

    private String cpf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getDtAtualizacao() {
        return dtAtualizacao;
    }

    public void setDtAtualizacao(Calendar dtAtualizacao) {
        this.dtAtualizacao = dtAtualizacao;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public Boolean getRiscoIntegridade() {
        return riscoIntegridade;
    }

    public void setRiscoIntegridade(Boolean riscoIntegridade) {
        this.riscoIntegridade = riscoIntegridade;
    }

    public IdentificacaoDTO getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(IdentificacaoDTO identificacao) {
        this.identificacao = identificacao;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public List<EventoCausaDTO> getCausas() {
        return causas;
    }

    public void setCausas(List<EventoCausaDTO> causas) {
        this.causas = causas;
    }

    public List<EventoConsequenciaDTO> getConsequencias() {
        return consequencias;
    }

    public void setConsequencias(List<EventoConsequenciaDTO> consequencias) {
        this.consequencias = consequencias;
    }

    public List<ControleEventoDTO> getControleEventos() {
        return controleEventos;
    }

    public void setControleEventos(List<ControleEventoDTO> controleEventos) {
        this.controleEventos = controleEventos;
    }

    public CalculoRiscoDTO getCalculoRiscoInerente() {
        return calculoRiscoInerente;
    }

    public void setCalculoRiscoInerente(CalculoRiscoDTO calculoRiscoInerente) {
        this.calculoRiscoInerente = calculoRiscoInerente;
    }

    public CalculoRiscoDTO getCalculoRiscoResidual() {
        return calculoRiscoResidual;
    }

    public void setCalculoRiscoResidual(CalculoRiscoDTO calculoRiscoResidual) {
        this.calculoRiscoResidual = calculoRiscoResidual;
    }

    public String getJustificativaRespostaRisco() {
        return justificativaRespostaRisco;
    }

    public void setJustificativaRespostaRisco(String justificativaRespostaRisco) {
        this.justificativaRespostaRisco = justificativaRespostaRisco;
    }

    public RespostaRiscoDTO getRespostaRisco() {
        return respostaRisco;
    }

    public void setRespostaRisco(RespostaRiscoDTO respostaRisco) {
        this.respostaRisco = respostaRisco;
    }

    public List<PlanoControleDTO> getControles() {
        return controles;
    }

    public void setControles(List<PlanoControleDTO> controles) {
        this.controles = controles;
    }

    public List<HistoricoEventoRiscoDTO> getHistorico() {
        return historico;
    }

    public void setHistorico(List<HistoricoEventoRiscoDTO> historico) {
        this.historico = historico;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}