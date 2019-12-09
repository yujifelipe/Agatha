package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import br.gov.mpog.gestaoriscos.servico.UsuarioService;
import br.gov.mpog.gestaoriscos.util.Mensagens;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    private final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    private static final String ENTITY_NAME = "usuario";

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RequestMapping(value = "/usuario-logado", method = RequestMethod.GET)
    public UsuarioDTO getUsuarioLogado(HttpServletRequest request) {
        br.gov.nuvem.ecidadao.security.Usuario usuarioBrasilCidadao =
                (br.gov.nuvem.ecidadao.security.Usuario) request.getAttribute("user");
        return usuarioService.findByCpf(StringUtil.removerMascaraCpf(usuarioBrasilCidadao.getCpf()));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity save(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        log.debug("REST request to create a Usuario : {}", usuarioDTO);
        if (usuarioService.isCPFCadastrado(usuarioDTO.getCpf())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, Mensagens.ERROR, Mensagens.US001_14)).body(null);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_2, "")).body(usuarioService.save(usuarioDTO));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity update(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        log.debug("REST request to update a Usuario : {}", usuarioDTO);
        if (usuarioService.isCPFCadastradoDiferenteIdUsuario(usuarioDTO.getCpf(), usuarioDTO.getId())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, Mensagens.ERROR, Mensagens.US001_14)).body(null);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, "")).body(usuarioService.update(usuarioDTO));
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.debug("REST request to get a Usuario : {}", id);
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete a Usuario : {}", id);
        if (usuarioService.hasProcesso(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, Mensagens.ERROR, Mensagens.US001_1)).body(null);
        }else{
            usuarioService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
        }
    }

}
