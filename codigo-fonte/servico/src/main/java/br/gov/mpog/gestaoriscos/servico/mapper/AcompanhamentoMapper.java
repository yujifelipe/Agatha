package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Acompanhamento;
import br.gov.mpog.gestaoriscos.modelo.dto.AcompanhamentoDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        ArquivoAnexoMapper.class
})
public interface AcompanhamentoMapper extends EntidadeBaseMapper<AcompanhamentoDTO, Acompanhamento> {

    @Override
    @Mapping(target = "planoControle.id", source = "planoControleId")
    Acompanhamento toEntity(AcompanhamentoDTO acompanhamentoDTO);

    @Override
    @InheritInverseConfiguration
    AcompanhamentoDTO toDto(Acompanhamento acompanhamento);
}
