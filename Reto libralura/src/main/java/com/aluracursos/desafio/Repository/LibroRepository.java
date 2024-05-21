package com.aluracursos.desafio.Repository;

import com.aluracursos.desafio.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByFechaDeNacimiento(String fecha);

    //@Query("SELECT l FROM f.fecha_De_Nacimiento WHERE f.fecha_De_Nacimiento BETWEEN :inicio AND :fin")
    //List<Libro> libroPorFechaDeNacimiento(String inicio, String fin);

    List<Libro> findByFechaDeNacimientoBetween(String inicio, String fin);

    //@Query("SELECT l FROM l.idiomas")
    List<Libro> findByIdiomas(String idioma);
}

