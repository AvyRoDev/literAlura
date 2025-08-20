package com.alura.challenge.literalura.repository;

import com.alura.challenge.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);
    
    List<Libro> findByIdioma(String idioma);
    
    @Query("SELECT COUNT(l) FROM Libro l WHERE l.idioma = :idioma")
    Long contarLibrosPorIdioma(String idioma);
    
    @Query("SELECT l FROM Libro l ORDER BY l.numeroDeDescargas DESC")
    List<Libro> findTop10ByOrderByNumeroDeDescargasDesc();
}
