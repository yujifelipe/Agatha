package br.gov.mpog.gestaoriscos.modelo.dto;

import java.util.Calendar;

public class HistoricoEventoRiscoDTO extends EntidadeBaseDTO {

    private String evento;

    private Calendar dtCadastro;

    private String usuarioNome;

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Calendar getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Calendar dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }
}