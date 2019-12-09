package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Calculadora;
import br.gov.mpog.gestaoriscos.modelo.ImpactoCalculadora;
import br.gov.mpog.gestaoriscos.modelo.ProbabilidadeCalculadora;
import br.gov.mpog.gestaoriscos.modelo.dto.CalculadoraDTO;
import br.gov.mpog.gestaoriscos.repositorio.CalculadoraRepository;
import br.gov.mpog.gestaoriscos.servico.CalculadoraService;
import br.gov.mpog.gestaoriscos.servico.mapper.CalculadoraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Calculadora.
 */
@Service
@Transactional
public class CalculadoraServiceImpl implements CalculadoraService {

    private final Logger log = LoggerFactory.getLogger(CalculadoraServiceImpl.class);

    @Autowired
    private CalculadoraRepository calculadoraRepository;

    @Autowired
    private CalculadoraMapper calculadoraMapper;

    /**
     * Save a calculadora.
     *
     * @param calculadoraDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CalculadoraDTO save(CalculadoraDTO calculadoraDTO) {
        log.debug("Request to save Calculadora : {}", calculadoraDTO);
        Calculadora calculadora = calculadoraMapper.calculadoraDTOToCalculadora(calculadoraDTO);

        for (ImpactoCalculadora impactoCalculadora : calculadora.getImpactos()) {
            impactoCalculadora.setCalculadora(calculadora);
        }

        for (ProbabilidadeCalculadora probabilidadeCalculadora : calculadora.getProbabilidades()) {
            probabilidadeCalculadora.setCalculadora(calculadora);
        }

        calculadora.setCalculadoraBase(true);
        calculadora = calculadoraRepository.save(calculadora);
        return calculadoraMapper.calculadoraToCalculadoraDTO(calculadora);
    }

    @Override
    @Transactional(readOnly = true)
    public CalculadoraDTO getCalculadoraBase() {
        log.debug("Request to get Calculadora base");
        Calculadora result = calculadoraRepository.findTop1ByCalculadoraBaseTrueOrderByIdAsc();
        return calculadoraMapper.calculadoraToCalculadoraDTO(result);
    }
}
