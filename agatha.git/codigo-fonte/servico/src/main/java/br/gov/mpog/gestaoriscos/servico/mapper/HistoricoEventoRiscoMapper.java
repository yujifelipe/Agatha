package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.HistoricoEventoRisco;
import br.gov.mpog.gestaoriscos.modelo.dto.HistoricoEventoRiscoDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Evento and its DTO EventoDTO.
 */
@Mapper(componentModel = "spring")
public interface HistoricoEventoRiscoMapper extends EntidadeMapper<HistoricoEventoRiscoDTO, HistoricoEventoRisco> {

    @Override
    @Mapping(target = "eventoRisco", ignore = true)
    @Mapping(target = "usuario.nome", source = "usuarioNome")
    HistoricoEventoRisco toEntity(HistoricoEventoRiscoDTO historicoEventoRiscoDTO);

    @Override
    @InheritInverseConfiguration
    HistoricoEventoRiscoDTO toDto(HistoricoEventoRisco historicoEventoRisco);
}