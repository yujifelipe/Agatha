package br.gov.mpog.gestaoriscos.modelo;

import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "tb_acompanhamento")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_acompanhamento SET ic_excluido=true WHERE id_acompanhamento=?")
@NoArgsConstructor
public class Acompanhamento extends EntidadeBase {

    @Id
    @Column(name = "id_acompanhamento")
    @SequenceGenerator(allocationSize = 1, sequenceName = "sk_acompanhamento", name = "sk_acompanhamento")
    @GeneratedValue(generator = "sk_acompanhamento", strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dt_cadastro", nullable = false)
    private Calendar dtCadastro;

    @Column(name = "ds_status", nullable = false, length = AnnotationNumberUtil.L10)
    private String status;

    @Column(name = "ds_implementado", nullable = false, length = AnnotationNumberUtil.L15)
    private String implementado;

    @Column(name = "ds_justificativa", length = AnnotationNumberUtil.L500)
    private String justificativa;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plano_controle", nullable = false)
    private PlanoControle planoControle;

    @Audited
    @AuditJoinTable(name = "tb_acompanhamento_anexo_aud", schema = "aud_gestaoriscos")
    @ManyToMany
    @JoinTable(name = "tb_acompanhamento_anexo", schema = "gestaoriscos", joinColumns = {
            @JoinColumn(name = "id_acompanhamento")}, inverseJoinColumns = {
            @JoinColumn(name = "id_anexo")})
    private List<ArquivoAnexo> anexos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Calendar dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImplementado() {
        return implementado;
    }

    public void setImplementado(String implementado) {
        this.implementado = implementado;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public PlanoControle getPlanoControle() {
        return planoControle;
    }

    public void setPlanoControle(PlanoControle planoControle) {
        this.planoControle = planoControle;
    }

    public List<ArquivoAnexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<ArquivoAnexo> anexos) {
        this.anexos = anexos;
    }
}