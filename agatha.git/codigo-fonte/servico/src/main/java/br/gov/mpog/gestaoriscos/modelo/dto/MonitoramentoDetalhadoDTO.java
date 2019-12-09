package br.gov.mpog.gestaoriscos.modelo.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitoramentoDetalhadoDTO implements Serializable {

    private Long macroprocesso;

    private Long processo;

    private Long eventoRisco;

    private Long causa;

    private Long consequencia;

}
