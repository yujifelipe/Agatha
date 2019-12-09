package br.gov.mpog.gestaoriscos.modelo;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tb_historico_evento_risco")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_historico_evento_risco SET ic_excluido=true WHERE id_historico_evento_risco=?")
public class HistoricoEventoRisco extends EntidadeBase {

    @Id
    @Column(name = "id_historico_evento_risco")
    @SequenceGenerator(allocationSize = 1, sequenceName = "sk_historico_evento_risco", name = "sk_historico_evento_risco")
    @GeneratedValue(generator = "sk_historico_evento_risco", strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dt_cadastro", nullable = false)
    private Calendar dtCadastro;

    @Column(name = "ds_evento", nullable = false, length = AnnotationNumberUtil.L1000)
    private String evento;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento_risco", nullable = false)
    private EventoRisco eventoRisco;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

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

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public EventoRisco getEventoRisco() {
        return eventoRisco;
    }

    public void setEventoRisco(EventoRisco eventoRisco) {
        this.eventoRisco = eventoRisco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}