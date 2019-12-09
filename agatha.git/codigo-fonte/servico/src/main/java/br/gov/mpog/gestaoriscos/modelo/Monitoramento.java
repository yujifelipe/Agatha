package br.gov.mpog.gestaoriscos.modelo;

import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "tb_monitoramento")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_monitoramento SET ic_excluido=true WHERE id_monitoramento=?")
public class Monitoramento extends EntidadeBase {

    private static final String ID_MONITORAMENTO = "id_monitoramento";
    private static final String SCHEMA_AUD_GESTAORISCOS = "aud_gestaoriscos";
    private static final String SCHEMA_GESTAORISCOS = "gestaoriscos";

    @Id
    @Column(name = ID_MONITORAMENTO)
    @SequenceGenerator(allocationSize = 1, sequenceName = "sk_monitoramento", name = "sk_monitoramento")
    @GeneratedValue(generator = "sk_monitoramento", strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_orgao")
    private Orgao orgao;

    @Column(name = "ic_perfil_nucleo")
    private Boolean perfilNucleo;

    @NotNull
    @Column(name = "no_monitoramento", nullable = false)
    private String nome;

    @Column(name = "dt_cadastro")
    private Calendar dtCadastro;

    @Column(name = "ic_operador_macroprocesso")
    private Boolean operadorMacropocesso;

    @Column(name = "ic_operador_categoria")
    private Boolean operadorCategoria;

    @Column(name = "ic_operador_integridade")
    private Boolean operadorIntegridade;

    @Column(name = "ic_operador_nivel_risco")
    private Boolean operadorNivelRisco;

    @Column(name = "ic_operador_residual")
    private Boolean operadorResidual;

    @Column(name = "ic_operador_inerente")
    private Boolean operadorInerente;

    @Column(name = "ic_operador_conclusao")
    private Boolean operadorConclusao;

    @Column(name = "ds_integridades")
    private String integridades;

    @Column(name = "ds_niveis_risco")
    private String niveisRisco;

    @Column(name = "dt_inicio")
    private Calendar dtInicio;

    @Column(name = "dt_fim")
    private Calendar dtFim;

    @Audited
    @AuditJoinTable(name = "tb_monitoramento_secretaria_aud", schema = SCHEMA_AUD_GESTAORISCOS)
    @ManyToMany
    @JoinTable(name = "tb_monitoramento_secretaria", schema = SCHEMA_GESTAORISCOS, joinColumns = {
            @JoinColumn(name = ID_MONITORAMENTO)}, inverseJoinColumns = {
            @JoinColumn(name = "id_secretaria")})
    private List<Orgao> secretarias;

    @Audited
    @AuditJoinTable(name = "tb_monitoramento_macroprocesso_aud", schema = SCHEMA_AUD_GESTAORISCOS)
    @ManyToMany
    @JoinTable(name = "tb_monitoramento_macroprocesso", schema = SCHEMA_GESTAORISCOS, joinColumns = {
            @JoinColumn(name = ID_MONITORAMENTO)}, inverseJoinColumns = {
            @JoinColumn(name = "id_macroprocesso")})
    private List<Macroprocesso> macroprocessos;

    @Audited
    @AuditJoinTable(name = "tb_monitoramento_categoria_aud", schema = SCHEMA_AUD_GESTAORISCOS)
    @ManyToMany
    @JoinTable(name = "tb_monitoramento_categoria", schema = SCHEMA_GESTAORISCOS, joinColumns = {
            @JoinColumn(name = ID_MONITORAMENTO)}, inverseJoinColumns = {
            @JoinColumn(name = "id_categoria")})
    private List<Categoria> categorias;

    @Audited
    @AuditJoinTable(name = "tb_monitoramento_risco_residual_aud", schema = SCHEMA_AUD_GESTAORISCOS)
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "tb_monitoramento_risco_residual", schema = SCHEMA_GESTAORISCOS, joinColumns = {
            @JoinColumn(name = ID_MONITORAMENTO)}, inverseJoinColumns = {
            @JoinColumn(name = "id_monitoramento_risco")})
    private List<MonitoramentoRisco> riscosResiduais;

    @Audited
    @AuditJoinTable(name = "tb_monitoramento_risco_inerente_aud", schema = SCHEMA_AUD_GESTAORISCOS)
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "tb_monitoramento_risco_inerente", schema = SCHEMA_GESTAORISCOS, joinColumns = {
            @JoinColumn(name = ID_MONITORAMENTO)}, inverseJoinColumns = {
            @JoinColumn(name = "id_monitoramento_risco")})
    private List<MonitoramentoRisco> riscosInerentes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public Boolean getPerfilNucleo() {
        return perfilNucleo;
    }

    public void setPerfilNucleo(Boolean perfilNucleo) {
        this.perfilNucleo = perfilNucleo;
    }

    @NotNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NotNull String nome) {
        this.nome = nome;
    }

    public Calendar getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Calendar dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public Boolean getOperadorMacropocesso() {
        return operadorMacropocesso;
    }

    public void setOperadorMacropocesso(Boolean operadorMacropocesso) {
        this.operadorMacropocesso = operadorMacropocesso;
    }

    public Boolean getOperadorCategoria() {
        return operadorCategoria;
    }

    public void setOperadorCategoria(Boolean operadorCategoria) {
        this.operadorCategoria = operadorCategoria;
    }

    public Boolean getOperadorIntegridade() {
        return operadorIntegridade;
    }

    public void setOperadorIntegridade(Boolean operadorIntegridade) {
        this.operadorIntegridade = operadorIntegridade;
    }

    public Boolean getOperadorNivelRisco() {
        return operadorNivelRisco;
    }

    public void setOperadorNivelRisco(Boolean operadorNivelRisco) {
        this.operadorNivelRisco = operadorNivelRisco;
    }

    public Boolean getOperadorResidual() {
        return operadorResidual;
    }

    public void setOperadorResidual(Boolean operadorResidual) {
        this.operadorResidual = operadorResidual;
    }

    public Boolean getOperadorInerente() {
        return operadorInerente;
    }

    public void setOperadorInerente(Boolean operadorInerente) {
        this.operadorInerente = operadorInerente;
    }

    public Boolean getOperadorConclusao() {
        return operadorConclusao;
    }

    public void setOperadorConclusao(Boolean operadorConclusao) {
        this.operadorConclusao = operadorConclusao;
    }

    public String getIntegridades() {
        return integridades;
    }

    public void setIntegridades(String integridades) {
        this.integridades = integridades;
    }

    public String getNiveisRisco() {
        return niveisRisco;
    }

    public void setNiveisRisco(String niveisRisco) {
        this.niveisRisco = niveisRisco;
    }

    public List<Orgao> getSecretarias() {
        return secretarias;
    }

    public void setSecretarias(List<Orgao> secretarias) {
        this.secretarias = secretarias;
    }

    public List<Macroprocesso> getMacroprocessos() {
        return macroprocessos;
    }

    public void setMacroprocessos(List<Macroprocesso> macroprocessos) {
        this.macroprocessos = macroprocessos;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<MonitoramentoRisco> getRiscosResiduais() {
        return riscosResiduais;
    }

    public void setRiscosResiduais(List<MonitoramentoRisco> riscosResiduais) {
        this.riscosResiduais = riscosResiduais;
    }

    public List<MonitoramentoRisco> getRiscosInerentes() {
        return riscosInerentes;
    }

    public void setRiscosInerentes(List<MonitoramentoRisco> riscosInerentes) {
        this.riscosInerentes = riscosInerentes;
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
