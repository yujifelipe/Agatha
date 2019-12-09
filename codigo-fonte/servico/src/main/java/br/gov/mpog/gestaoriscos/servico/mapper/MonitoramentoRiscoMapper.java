package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.MonitoramentoRisco;
import br.gov.mpog.gestaoriscos.modelo.dto.MonitoramentoRiscoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MonitoramentoRiscoMapper extends EntidadeBaseMapper<MonitoramentoRiscoDTO, MonitoramentoRisco> {

    @Override
    @Mapping(target = "niveis", expression = "java(String.join(\",\",dto.getNiveis()))")
    MonitoramentoRisco toEntity(MonitoramentoRiscoDTO dto);

    @Override
    @Mapping(target = "niveis", expression = "java(java.util.Arrays.asList(entity.getNiveis().split(\",\")))")
    MonitoramentoRiscoDTO toDto(MonitoramentoRisco entity);

}
