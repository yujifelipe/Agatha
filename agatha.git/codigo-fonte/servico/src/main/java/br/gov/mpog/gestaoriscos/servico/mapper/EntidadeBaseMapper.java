package br.gov.mpog.gestaoriscos.servico.mapper;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <DTO>    - DTO type parameter.
 * @param <ENTITY> - Entity type parameter.
 */

public interface EntidadeBaseMapper<DTO, ENTITY> {

    ENTITY toEntity(DTO dto);

    DTO toDto(ENTITY entity);

    List<ENTITY> toEntity(List<DTO> dtoList);

    List<DTO> toDto(List<ENTITY> entityList);
}
