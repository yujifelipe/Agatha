package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Monitoramento;
import br.gov.mpog.gestaoriscos.modelo.dto.MonitoramentoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        OrgaoMapper.class,
        MacroprocessoMapper.class,
        CategoriaMapper.class,
        MonitoramentoRiscoMapper.class
})
public interface MonitoramentoMapper extends EntidadeBaseMapper<MonitoramentoDTO, Monitoramento> {

    @Override
    @Mapping(target = "integridades", expression = "java(String.join(\",\",dto.getIntegridades()))")
    @Mapping(target = "niveisRisco", expression = "java(String.join(\",\",dto.getNiveisRisco()))")
    Monitoramento toEntity(MonitoramentoDTO dto);

    @Override
    @Mapping(target = "integridades", expression = "java(java.util.Arrays.asList(entity.getIntegridades().split(\",\")))")
    @Mapping(target = "niveisRisco", expression = "java(java.util.Arrays.asList(entity.getNiveisRisco().split(\",\")))")
    MonitoramentoDTO toDto(Monitoramento entity);
}
