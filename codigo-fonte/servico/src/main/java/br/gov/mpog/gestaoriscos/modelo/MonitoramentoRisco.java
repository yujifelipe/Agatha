package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "tb_monitoramento_risco")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_monitoramento_risco SET ic_excluido=true WHERE id_monitoramento_risco=?")
public class MonitoramentoRisco extends EntidadeBase {

    @Id
    @Column(name = "id_monitoramento_risco")
    @SequenceGenerator(allocationSize = 1, sequenceName = "sk_monitoramento_risco", name = "sk_monitoramento_risco")
    @GeneratedValue(generator = "sk_monitoramento_risco", strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ds_fator", length = AnnotationNumberUtil.L30)
    private String fator;

    @Column(name = "ds_niveis", length = AnnotationNumberUtil.L30)
    private String niveis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFator() {
        return fator;
    }

    public void setFator(String fator) {
        this.fator = fator;
    }

    public String getNiveis() {
        return niveis;
    }

    public void setNiveis(String niveis) {
        this.niveis = niveis;
    }
}
