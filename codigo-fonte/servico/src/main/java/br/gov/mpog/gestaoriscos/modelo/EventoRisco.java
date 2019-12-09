package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import br.gov.mpog.gestaoriscos.util.AnnotationStringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

@Entity
@Audited
@Table(name = "tb_evento_risco")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_evento_risco SET ic_excluido=true WHERE id_evento_risco=?")
@NoArgsConstructor
public class EventoRisco extends EntidadeBase {

    @Id
    @Column(name = "id_evento_risco")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_evento_risco", name = "sk_evento_risco")
    @GeneratedValue(generator = "sk_evento_risco", strategy = GenerationType.AUTO)
    private Long id;

    @NotAudited
    @Column(name = "dt_atualizacao")
    private Calendar dtAtualizacao;

    @Column(name = "ic_risco_integridade")
    private Boolean riscoIntegridade;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_identificacao", nullable = false)
    private Identificacao identificacao;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Evento evento;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Categoria categoria;

    @JsonIgnore
    @OrderBy("id")
    @Where(clause = "ic_excluido='false'")
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = AnnotationStringUtil.EVENTO_RISCO, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EventoCausa> causas;

    @JsonIgnore
    @OrderBy("id")
    @Where(clause = "ic_excluido='false'")
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = AnnotationStringUtil.EVENTO_RISCO, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EventoConsequencia> consequencias;

    @JsonIgnore
    @OrderBy("id")
    @Where(clause = "ic_excluido='false'")
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = AnnotationStringUtil.EVENTO_RISCO, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ControleEvento> controleEventos;

    @JsonIgnore
    @OrderBy("id")
    @Where(clause = "ic_excluido='false'")
    @OneToMany(mappedBy = AnnotationStringUtil.EVENTO_RISCO, fetch = FetchType.LAZY)
    private List<PlanoControle> planos;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "id_calculo_risco_inerente")
    private CalculoRisco calculoRiscoInerente;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "id_calculo_risco_residual")
    private CalculoRisco calculoRiscoResidual;

    @Column(length = AnnotationNumberUtil.L4000, name = "ds_justificativa_resposta_risco")
    private String justificativaRespostaRisco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_resposta_risco")
    private RespostaRisco respostaRisco;

    @JsonIgnore
    @OrderBy("id")
    @Where(clause = "ic_excluido='false'")
    @OneToMany(mappedBy = AnnotationStringUtil.EVENTO_RISCO, fetch = FetchType.LAZY)
    private List<PlanoControle> controles;

    @NotAudited
    @JsonIgnore
    @OrderBy("id")
    @Where(clause = "ic_excluido='false'")
    @OneToMany(mappedBy = AnnotationStringUtil.EVENTO_RISCO, fetch = FetchType.LAZY)
    private List<HistoricoEventoRisco> historico;

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

    public Boolean getRiscoIntegridade() {
        return riscoIntegridade;
    }

    public void setRiscoIntegridade(Boolean riscoIntegridade) {
        this.riscoIntegridade = riscoIntegridade;
    }

    public Identificacao getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(Identificacao identificacao) {
        this.identificacao = identificacao;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<EventoCausa> getCausas() {
        return causas;
    }

    public void setCausas(List<EventoCausa> causas) {
        this.causas = causas;
    }

    public List<EventoConsequencia> getConsequencias() {
        return consequencias;
    }

    public void setConsequencias(List<EventoConsequencia> consequencias) {
        this.consequencias = consequencias;
    }

    public CalculoRisco getCalculoRiscoInerente() {
        return calculoRiscoInerente;
    }

    public void setCalculoRiscoInerente(CalculoRisco calculoRiscoInerente) {
        this.calculoRiscoInerente = calculoRiscoInerente;
    }

    public CalculoRisco getCalculoRiscoResidual() {
        return calculoRiscoResidual;
    }

    public void setCalculoRiscoResidual(CalculoRisco calculoRiscoResidual) {
        this.calculoRiscoResidual = calculoRiscoResidual;
    }

    public List<ControleEvento> getControleEventos() {
        return controleEventos;
    }

    public void setControleEventos(List<ControleEvento> controleEventos) {
        this.controleEventos = controleEventos;
    }

    public List<PlanoControle> getPlanos() {
        return planos;
    }

    public void setPlanos(List<PlanoControle> planos) {
        this.planos = planos;
    }

    public String getJustificativaRespostaRisco() {
        return justificativaRespostaRisco;
    }

    public void setJustificativaRespostaRisco(String justificativaRespostaRisco) {
        this.justificativaRespostaRisco = justificativaRespostaRisco;
    }

    public RespostaRisco getRespostaRisco() {
        return respostaRisco;
    }

    public void setRespostaRisco(RespostaRisco respostaRisco) {
        this.respostaRisco = respostaRisco;
    }

    public List<PlanoControle> getControles() {
        return controles;
    }

    public void setControles(List<PlanoControle> controles) {
        this.controles = controles;
    }

    public List<HistoricoEventoRisco> getHistorico() {
        return historico;
    }

    public void setHistorico(List<HistoricoEventoRisco> historico) {
        this.historico = historico;
    }

    @PrePersist
    protected void onCreate() {
        dtAtualizacao = Calendar.getInstance();
    }

    @PreUpdate
    protected void onUpdate() {
        dtAtualizacao = Calendar.getInstance();
    }
}