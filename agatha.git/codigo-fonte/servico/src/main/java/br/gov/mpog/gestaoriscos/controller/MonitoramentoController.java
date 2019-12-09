package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.CategoriaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.GraficoMonitoramentoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MonitoramentoDetalhadoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MacroprocessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MonitoramentoDTO;
import br.gov.mpog.gestaoriscos.servico.MonitoramentoService;
import br.gov.mpog.gestaoriscos.util.Mensagens;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Monitoramento.
 */
@RestController
@RequestMapping(value = "/monitoramentos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MonitoramentoController {

    private final Logger log = LoggerFactory.getLogger(MonitoramentoController.class);
    private static final String ENTITY_NAME = "monitoramento";
    private final MonitoramentoService monitoramentoService;

    @Autowired
    public MonitoramentoController(MonitoramentoService monitoramentoService) {
        this.monitoramentoService = monitoramentoService;
    }

    /**
     * POST: Create a new monitoramento.
     *
     * @param monitoramentoDTO the monitoramentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monitoramentoDTO, or with status 400 (Bad Request) if the monitoramento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MonitoramentoDTO> createMonitoramento(@Valid @RequestBody MonitoramentoDTO monitoramentoDTO) throws URISyntaxException {
        log.debug("REST request to save Monitoramento : {}", monitoramentoDTO);
        if (monitoramentoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new monitoramento cannot already have an ID")).body(null);
        }
        MonitoramentoDTO result = monitoramentoService.save(monitoramentoDTO);
        return ResponseEntity.created(new URI("/api/monitoramento/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * PUT: Updates an existing monitoramento.
     *
     * @param monitoramentoDTO the monitoramentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monitoramentoDTO,
     * or with status 400 (Bad Request) if the monitoramentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the monitoramentoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<MonitoramentoDTO> updateMonitoramento(@Valid @RequestBody MonitoramentoDTO monitoramentoDTO) throws URISyntaxException {
        log.debug("REST request to update Monitoramento : {}", monitoramentoDTO);
        if (monitoramentoDTO.getId() == null) {
            return createMonitoramento(monitoramentoDTO);
        }
        MonitoramentoDTO result = monitoramentoService.save(monitoramentoDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, monitoramentoDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the monitoramentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of monitoramento in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MonitoramentoDTO>> getAllMonitoramentos(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get a page of Monitoramentos");
        Page<MonitoramentoDTO> page = monitoramentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/monitoramentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" monitoramento.
     *
     * @param id the id of the monitoramentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monitoramentoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<MonitoramentoDTO> getMonitoramento(@PathVariable Long id) {
        log.debug("REST request to get Monitoramento : {}", id);
        MonitoramentoDTO monitoramentoDTO = monitoramentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(monitoramentoDTO));
    }

    /**
     * DELETE  /:id : delete the "id" monitoramento.
     *
     * @param id the id of the monitoramentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteMonitoramento(@PathVariable Long id) {
        log.debug("REST request to delete Monitoramento : {}", id);

        monitoramentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/macroprocessos")
    public ResponseEntity<List<MacroprocessoDTO>> getAllMacroProcessos() {
        log.debug("REST request to all macroprocessos");
        return new ResponseEntity<>(monitoramentoService.getAllMacroProcessos(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "categorias")
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        log.debug("REST request to all categorias");
        return new ResponseEntity<>(monitoramentoService.getAllCategorias(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "gerar-grafico")
    public ResponseEntity<MonitoramentoDetalhadoDTO> gerarGrafico(@RequestBody MonitoramentoDTO monitoramentoDTO) {
        log.debug("REST request to all categorias");
        return new ResponseEntity<>(monitoramentoService.gerarGrafico(monitoramentoDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "grafico-risco-residual")
    public ResponseEntity<List<GraficoMonitoramentoDTO>> gerarGraficoRiscoResidual(@RequestBody MonitoramentoDTO monitoramentoDTO) {
        log.debug("REST request to all categorias");
        return new ResponseEntity<>(monitoramentoService.gerarGraficoRiscoResidual(monitoramentoDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "grafico-categoria-risco")
    public ResponseEntity<List<GraficoMonitoramentoDTO>> gerarGraficoCategoriaRisco(@RequestBody MonitoramentoDTO monitoramentoDTO) {
        log.debug("REST request to all categorias");
        return new ResponseEntity<>(monitoramentoService.gerarGraficoCategoriaRisco(monitoramentoDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "gerar-relatorio", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> gerarRelatorioMonitoramento(@RequestBody MonitoramentoDTO monitoramentoDTO) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"Monitoramento\"");

        byte[] pdfBytes = monitoramentoService.gerarRelatorioMonitoramento(monitoramentoDTO);

        headers.setContentLength(pdfBytes.length);
        return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(pdfBytes)), headers, HttpStatus.OK);
    }
}



