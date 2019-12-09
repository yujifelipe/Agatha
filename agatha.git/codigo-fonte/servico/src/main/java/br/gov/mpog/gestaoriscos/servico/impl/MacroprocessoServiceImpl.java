package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Macroprocesso;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.dto.MacroprocessoDTO;
import br.gov.mpog.gestaoriscos.repositorio.MacroprocessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.impl.TaxonomiaBaseCustomRepositorioImpl;
import br.gov.mpog.gestaoriscos.servico.MacroprocessoService;
import br.gov.mpog.gestaoriscos.servico.mapper.MacroprocessoMapper;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Macroprocesso.
 */
@Service
@Transactional
public class MacroprocessoServiceImpl implements MacroprocessoService {

    private final Logger log = LoggerFactory.getLogger(MacroprocessoServiceImpl.class);

    private final MacroprocessoRepository macroprocessoRepository;

    private final TaxonomiaBaseCustomRepositorioImpl taxonomiaBaseCustomRepositorio;

    private final ProcessoRepository processoRepository;

    private final MacroprocessoMapper macroprocessoMapper;

    @Autowired
    public MacroprocessoServiceImpl(MacroprocessoRepository macroprocessoRepository, TaxonomiaBaseCustomRepositorioImpl taxonomiaBaseCustomRepositorio, ProcessoRepository processoRepository, MacroprocessoMapper macroprocessoMapper) {
        this.macroprocessoRepository = macroprocessoRepository;
        this.taxonomiaBaseCustomRepositorio = taxonomiaBaseCustomRepositorio;
        this.processoRepository = processoRepository;
        this.macroprocessoMapper = macroprocessoMapper;
    }

    /**
     * Save a macroprocesso.
     *
     * @param macroprocessoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MacroprocessoDTO save(MacroprocessoDTO macroprocessoDTO) {
        log.debug("Request to save Macroprocesso : {}", macroprocessoDTO);
        Macroprocesso macroprocesso = macroprocessoMapper.toEntity(macroprocessoDTO);

        if (macroprocesso.getSecretaria() == null || macroprocesso.getSecretaria().getId() == null) {
            macroprocesso.setSecretaria(null);
        }

        macroprocesso.setSearch(StringUtil.removerAcento(macroprocesso.getDescricao()));
        macroprocesso = macroprocessoRepository.save(macroprocesso);
        return macroprocessoMapper.toDto(macroprocesso);
    }

    /**
     * Get all the macroprocessos.
     *
     * @param pageable     the pagination information
     * @param secretariaId - secretaria
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MacroprocessoDTO> findAll(Pageable pageable, String descricao, Boolean status, Long secretariaId) {
        log.debug("Request to get all Macroprocessos");
        Page<Macroprocesso> result;

        if (secretariaId != null && status != null) {
            result = macroprocessoRepository.findBySearchContainingIgnoreCaseAndStatusAndSecretariaIdOrderByDescricaoAsc(descricao, status, secretariaId, pageable);
        } else if (secretariaId != null) {
            result = macroprocessoRepository.findBySearchContainingIgnoreCaseAndSecretariaIdOrderByDescricaoAsc(descricao, secretariaId, pageable);
        } else if (status != null) {
            result = macroprocessoRepository.findBySearchContainingIgnoreCaseAndStatusOrderByDescricaoAsc(descricao, status, pageable);
        } else {
            result = macroprocessoRepository.findBySearchContainingIgnoreCaseOrderByDescricaoAsc(descricao, pageable);
        }
        return result.map(macroprocessoMapper::toDto);
    }

    /**
     * Get one macroprocesso by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MacroprocessoDTO findOne(Long id) {
        log.debug("Request to get Macroprocesso : {}", id);
        Macroprocesso macroprocesso = macroprocessoRepository.findOne(id);
        return macroprocessoMapper.toDto(macroprocesso);
    }

    /**
     * Delete the  macroprocesso by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Macroprocesso : {}", id);
        macroprocessoRepository.delete(id);
    }

    /**
     * Verifica se existe alguma Macroprocesso com a mesma descrição
     *
     * @param macroprocessoDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    @Override
    public Boolean verificarExistencia(MacroprocessoDTO macroprocessoDTO) {
        Macroprocesso result = macroprocessoRepository.findBySearchIgnoreCase(StringUtil.removerAcento(macroprocessoDTO.getDescricao()));
        return !(result == null || result.getId().equals(macroprocessoDTO.getId()));
    }

    @Override
    public List<String> searchByDescricao(String descricao) {
        return taxonomiaBaseCustomRepositorio.searchByDescricao(descricao, "Macroprocesso");
    }

    @Override
    public Boolean hasProcesso(Long id) {
        List<Processo> processos = processoRepository.findByMacroprocessoId(id);
        return !processos.isEmpty();
    }
}