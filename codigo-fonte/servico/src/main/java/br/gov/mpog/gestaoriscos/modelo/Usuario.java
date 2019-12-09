package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Audited
@Entity
@Table(name = "tb_usuario")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_usuario SET ic_excluido=true WHERE id_usuario=?")
public class Usuario {

    @Id
    @Column(name = "id_usuario")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_usuario", name = "sk_usuario")
    @GeneratedValue(generator = "sk_usuario", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = AnnotationNumberUtil.L100, name = "no_usuario")
    private String nome;

    @Column(length = AnnotationNumberUtil.L11, name = "nu_cpf")
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "id_orgao")
    private Orgao orgao;

    @Column(name = "email", nullable = false, length = AnnotationNumberUtil.L50, unique = true)
    private String email;

    @NotNull
    @NotAudited
    @Column(name = "ic_excluido", columnDefinition = "boolean default false")
    private boolean excluido;

    @JsonIgnore
    @Where(clause = "ic_excluido = 'false'")
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Permissao> permissoes;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String cpf) {
        this();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    public Usuario(Long id, String nome, String cpf, Long idOrgao) {
        this(id, nome, cpf);
        this.orgao = new Orgao(idOrgao);
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }
}
