package com.alura.challenge.literalura.repository;

import com.alura.challenge.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreContainsIgnoreCase(String nombre);
    
    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :año AND (a.fechaDeFallecimiento IS NULL OR a.fechaDeFallecimiento > :año)")
    List<Autor> findAutoresVivosEnAño(@Param("año") int año);
}
