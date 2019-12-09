package br.gov.mpog.gestaoriscos.util;

import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import br.gov.mpog.gestaoriscos.servico.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UsuarioUtil {

    private UsuarioUtil() {
        throw new IllegalAccessError("Classe Utiliataria");
    }

    public static String getCpfUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        return principal.getUsername();
    }

    public static UsuarioDTO getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        String cpf = principal.getUsername();

        UsuarioService usuarioService = SistemaSpringUtils.getInstance().getBean(UsuarioService.class);
        return usuarioService.findByCpf(cpf);
    }

}
