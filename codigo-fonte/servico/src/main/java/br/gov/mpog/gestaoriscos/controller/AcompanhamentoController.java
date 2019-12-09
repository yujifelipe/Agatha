package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.AcompanhamentoDTO;
import br.gov.mpog.gestaoriscos.servico.AcompanhamentoService;
import br.gov.mpog.gestaoriscos.util.Mensagens;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Acompanhamento.
 */
@RestController
@RequestMapping(value = "/acompanhamentos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AcompanhamentoController {

    private final Logger log = LoggerFactory.getLogger(AcompanhamentoController.class);

    private final AcompanhamentoService acompanhamentoService;

    @Autowired
    public AcompanhamentoController(AcompanhamentoService acompanhamentoService) {
        this.acompanhamentoService = acompanhamentoService;
    }

    /**
     * POST: Create a new acompanhamento.
     *
     * @param acompanhamentoDTO the acompanhamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acompanhamentoDTO, or with status 400 (Bad Request) if the acompanhamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AcompanhamentoDTO> createAcompanhamento(@Valid @RequestBody AcompanhamentoDTO acompanhamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Acompanhamento : {}", acompanhamentoDTO);
        AcompanhamentoDTO result = acompanhamentoService.createAcompanhamento(acompanhamentoDTO);
        return ResponseEntity.created(new URI("/api/acompanhamentos/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * GET: get all the acompanhamentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of acompanhamentos in body
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AcompanhamentoDTO>> getAllAcompanhamentos(@RequestParam(value = "planoControleId") Long planoControleId) {
        log.debug("REST request to get a page of Acompanhamentos by planoControleId", planoControleId);
        return new ResponseEntity<>(acompanhamentoService.findAll(planoControleId), HttpStatus.OK);
    }

    /**
     * DELETE  /:id : delete the "id" acompanhamento.
     *
     * @param id the id of the acompanhamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteAcompanhamento(@PathVariable Long id) {
        log.debug("REST request to delete Acompanhamento : {}", id);
        acompanhamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
    }

}