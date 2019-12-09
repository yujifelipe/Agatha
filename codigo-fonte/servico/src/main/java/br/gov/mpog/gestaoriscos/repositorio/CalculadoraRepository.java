package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Calculadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Calculadora entity.
 */
public interface CalculadoraRepository extends JpaRepository<Calculadora, Long> {

    Calculadora findTop1ByCalculadoraBaseTrueOrderByIdAsc();

    @Query("SELECT cal FROM Processo pro INNER JOIN pro.calculadora cal WHERE pro.id = :processoId ")
    Calculadora findByProcessoId(@Param("processoId") Long processoId);
}