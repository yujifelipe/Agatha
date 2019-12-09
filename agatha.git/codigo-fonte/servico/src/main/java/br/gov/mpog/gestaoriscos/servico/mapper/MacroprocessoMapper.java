package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Macroprocesso;
import br.gov.mpog.gestaoriscos.modelo.dto.MacroprocessoDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Macroprocesso and its DTO MacroprocessoDTO.
 */
@Mapper(componentModel = "spring")
public interface MacroprocessoMapper extends EntidadeBaseMapper<MacroprocessoDTO, Macroprocesso> {

    @Override
    @Mapping(target = "secretaria.id", source = "secretariaId")
    @Mapping(target = "secretaria.sigla", source = "secretariaSigla")
    Macroprocesso toEntity(MacroprocessoDTO macroprocessoDTO);

    @Override
    @InheritInverseConfiguration
    MacroprocessoDTO toDto(Macroprocesso macroprocesso);
}
