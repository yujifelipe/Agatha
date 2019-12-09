package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.AcompanhamentoDTO;
import java.util.List;

/**
 * Service Interface for managing Acompanhamento.
 */
public interface AcompanhamentoService {

    /**
     * Save a acompanhamento.
     *
     * @param acompanhamentoDTO the entity to save
     * @return the persisted entity
     */
    AcompanhamentoDTO createAcompanhamento(AcompanhamentoDTO acompanhamentoDTO);

    /**
     * Get all the acompanhamentos.
     *
     * @param planoControleId - plano controle id
     * @return the list of entities
     */
    List<AcompanhamentoDTO> findAll(Long planoControleId);

    /**
     * Delete the "id" acompanhamento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
