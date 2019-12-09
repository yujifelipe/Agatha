package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Calculadora;
import br.gov.mpog.gestaoriscos.modelo.dto.CalculadoraDTO;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Calculadora and its DTO CalculadoraDTO.
 */
@Mapper(componentModel = "spring", uses = {ImpactoCalculadoraMapper.class, ProbabilidadeCalculadoraMapper.class})
public interface CalculadoraMapper {

    CalculadoraDTO calculadoraToCalculadoraDTO(Calculadora calculadora);

    List<CalculadoraDTO> calculadorasToCalculadoraDTOs(List<Calculadora> calculadoras);

    Calculadora calculadoraDTOToCalculadora(CalculadoraDTO calculadoraDTO);

    List<Calculadora> calculadoraDTOsToCalculadoras(List<CalculadoraDTO> calculadoraDTOs);
}
