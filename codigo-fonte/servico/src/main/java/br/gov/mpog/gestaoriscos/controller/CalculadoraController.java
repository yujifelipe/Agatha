package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.CalculadoraDTO;
import br.gov.mpog.gestaoriscos.servico.CalculadoraService;
import br.gov.mpog.gestaoriscos.util.Mensagens;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Calculadora.
 */
@RestController
@RequestMapping(value = "/calculadoras", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CalculadoraController {

    private final Logger log = LoggerFactory.getLogger(CalculadoraController.class);

    private final CalculadoraService service;

    @Autowired
    public CalculadoraController(CalculadoraService service) {
        this.service = service;
    }

    /**
     * PUT: Updates an existing calculadora.
     *
     * @param calculadoraDTO the calculadoraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calculadoraDTO,
     * or with status 400 (Bad Request) if the calculadoraDTO is not valid,
     * or with status 500 (Internal Server Error) if the calculadoraDTO couldnt be updated
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CalculadoraDTO> updateCalculadora(@Valid @RequestBody CalculadoraDTO calculadoraDTO) {
        log.debug("REST request to update Calculadora : {}", calculadoraDTO);
        CalculadoraDTO result = service.save(calculadoraDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US027_1, calculadoraDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the calculadoras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calculadoras in body
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CalculadoraDTO> getCalculadoraBase() {
        log.debug("REST request to get Calculadora Base");
        return new ResponseEntity<>(service.getCalculadoraBase(), HttpStatus.OK);
    }
}
