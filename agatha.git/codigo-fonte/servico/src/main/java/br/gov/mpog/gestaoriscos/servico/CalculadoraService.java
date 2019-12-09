package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.CalculadoraDTO;

/**
 * Service Interface for managing Calculadora.
 */
public interface CalculadoraService {

    /**
     * Save a calculadora.
     *
     * @param calculadoraDTO the entity to save
     * @return the persisted entity
     */
    CalculadoraDTO save(CalculadoraDTO calculadoraDTO);

    CalculadoraDTO getCalculadoraBase();

}
