package br.gov.mpog.gestaoriscos.modelo.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GraficoMonitoramentoDTO implements Serializable {

    private String nome;

    private Long quantidade;

    public GraficoMonitoramentoDTO(String nome, Long quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }
}
