package br.gov.mpog.gestaoriscos.modelo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "tb_macroprocesso")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_macroprocesso SET ic_excluido=true WHERE id_macroprocesso=?")
@AttributeOverrides({
        @AttributeOverride(name = "descricao", column = @Column(name = "ds_macroprocesso"))
})
public class Macroprocesso extends EntidadeBaseDescricaoStatusSearch {

    @Id
    @Column(name = "id_macroprocesso")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_macroprocesso", name = "sk_macroprocesso")
    @GeneratedValue(generator = "sk_macroprocesso", strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_secretaria")
    private Orgao secretaria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orgao getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(Orgao secretaria) {
        this.secretaria = secretaria;
    }
}
