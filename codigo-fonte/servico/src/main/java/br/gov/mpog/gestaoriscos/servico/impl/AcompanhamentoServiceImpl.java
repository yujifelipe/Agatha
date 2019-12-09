package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Acompanhamento;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.dto.AcompanhamentoDTO;
import br.gov.mpog.gestaoriscos.repositorio.AcompanhamentoRepository;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.StatusProcessoRepository;
import br.gov.mpog.gestaoriscos.servico.AcompanhamentoService;
import br.gov.mpog.gestaoriscos.servico.mapper.AcompanhamentoMapper;
import java.util.Calendar;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Acompanhamento.
 */
@Service
@Transactional
public class AcompanhamentoServiceImpl implements AcompanhamentoService {

    private final Logger log = LoggerFactory.getLogger(AcompanhamentoServiceImpl.class);

    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private StatusProcessoRepository statusProcessoRepository;

    @Autowired
    private AcompanhamentoMapper acompanhamentoMapper;

    /**
     * Save a Acompanhamento.
     *
     * @param AcompanhamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AcompanhamentoDTO createAcompanhamento(AcompanhamentoDTO AcompanhamentoDTO) {
        log.debug("Request to save Acompanhamento : {}", AcompanhamentoDTO);
        Acompanhamento acompanhamento = acompanhamentoMapper.toEntity(AcompanhamentoDTO);
        acompanhamento.setDtCadastro(Calendar.getInstance());
        acompanhamento = acompanhamentoRepository.save(acompanhamento);

        this.alterarStatusProcessoNaoFinalizado(acompanhamento.getPlanoControle().getId());

        return acompanhamentoMapper.toDto(acompanhamento);
    }

    /**
     * Get all the Acompanhamentos.
     *
     * @param planoControleId - Plano de controle Id
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AcompanhamentoDTO> findAll(Long planoControleId) {
        log.debug("Request to get all Acompanhamentos by Evento Risco Id", planoControleId);
        List<Acompanhamento> result = acompanhamentoRepository.findByPlanoControleIdOrderByDtCadastroDesc(planoControleId);
        return acompanhamentoMapper.toDto(result);
    }

    /**
     * Delete the  Acompanhamento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Acompanhamento : {}", id);
        this.alterarStatusProcessoNaoFinalizado(acompanhamentoRepository.findOne(id).getPlanoControle().getId());
        acompanhamentoRepository.delete(id);
    }

    private void alterarStatusProcessoNaoFinalizado(Long planoControleId) {
        Processo processo = processoRepository.findByPlanoControleId(planoControleId);
        processo.setStatus(statusProcessoRepository.findByNomeIgnoreCase("não finalizado"));
        processo.setDecisao(null);
        processo.setJustificativa("");
        processoRepository.save(processo);
    }
}