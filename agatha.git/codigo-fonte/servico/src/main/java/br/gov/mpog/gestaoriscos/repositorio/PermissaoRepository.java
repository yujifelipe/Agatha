package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Perfil;
import br.gov.mpog.gestaoriscos.modelo.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Permissao entity.
 */
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    @Query(value = "SELECT pfl FROM Permissao prm INNER JOIN prm.perfil pfl INNER JOIN prm.usuario usr WHERE usr.cpf = :cpf AND LOWER(pfl.nome) = LOWER(:perfilNome) AND prm.excluido = false")
    Perfil findPerfilByUsuarioIdAndNomeIgnoreCase(@Param("cpf") String cpf, @Param("perfilNome") String perfilNome);

    @Query("SELECT permissao FROM Usuario u " +
            " INNER JOIN u.permissoes permissao " +
            " WHERE permissao.excluido = false " +
            " AND u.cpf = ?1 ")
    List<Permissao> findByUsuarioCpf(String cpf);

    Permissao findByUsuarioIdAndPerfilId(Long usuarioId, Long perfilId);

    @Modifying
    @Query("UPDATE Permissao set excluido = true WHERE id = ?1")
    void deletePermissaoById(Long idPermissao);

    @Modifying
    @Query("UPDATE Permissao set excluido = true WHERE id IN :idList")
    void deletePermissaoById(@Param("idList") List<Long> idList);

    @Modifying
    @Query("UPDATE Permissao p set p.excluido = true WHERE p.usuario.id = :usuarioId ")
    void deletePermissaoByUsuarioId(@Param("usuarioId") Long usuarioId);
}
