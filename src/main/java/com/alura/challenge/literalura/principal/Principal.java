package com.alura.challenge.literalura.principal;

import com.alura.challenge.literalura.model.*;
import com.alura.challenge.literalura.repository.AutorRepository;
import com.alura.challenge.literalura.repository.LibroRepository;
import com.alura.challenge.literalura.service.ConsumoAPI;
import com.alura.challenge.literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    ****************************************
                    *        CATÁLOGO DE LIBROS           *
                    *              LiterAlura              *
                    ****************************************
                    
                    Elija la opción a través de su número:
                    
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Estadísticas de libros por idioma
                    7 - Top 10 libros más descargados
                    
                    0 - Salir
                    ****************************************
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibroWeb();
                        break;
                    case 2:
                        mostrarLibrosRegistrados();
                        break;
                    case 3:
                        mostrarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosEnAño();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 6:
                        estadisticasPorIdioma();
                        break;
                    case 7:
                        top10LibrosMasDescargados();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese un número válido");
                teclado.nextLine();
            }
        }
    }

    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        System.out.println("JSON recibido: " + json);
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        
        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado");
            return libroBuscado.get();
        } else {
            System.out.println("Libro no encontrado");
            return null;
        }
    }

    private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibro();
        if (datos != null) {
            // Verificar si el libro ya existe
            Optional<Libro> libroExistente = libroRepository.findByTituloContainsIgnoreCase(datos.titulo());
            if (libroExistente.isPresent()) {
                System.out.println("El libro ya está registrado en la base de datos:");
                System.out.println(libroExistente.get());
                return;
            }

            // Crear nuevo libro
            Libro libro = new Libro(datos);
            
            // Procesar autor
            if (datos.autor() != null && !datos.autor().isEmpty()) {
                DatosAutor datosAutor = datos.autor().get(0);
                Optional<Autor> autorExistente = autorRepository.findByNombreContainsIgnoreCase(datosAutor.nombre());
                
                Autor autor;
                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor(datosAutor);
                    // No guardar el autor aquí, dejar que JPA lo maneje automáticamente
                }
                libro.setAutor(autor);
            }

            libroRepository.save(libro);
            System.out.println("Libro guardado exitosamente:");
            System.out.println(libro);
        }
    }

    private void mostrarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
        } else {
            System.out.println("\n********** LIBROS REGISTRADOS **********");
            libros.forEach(System.out::println);
        }
    }

    private void mostrarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
        } else {
            System.out.println("\n********** AUTORES REGISTRADOS **********");
            autores.forEach(autor -> {
                System.out.println(autor);
                System.out.println("Libros: " + 
                    autor.getLibros().stream()
                        .map(Libro::getTitulo)
                        .collect(Collectors.joining(", ")));
                System.out.println("***************************");
            });
        }
    }

    private void listarAutoresVivosEnAño() {
        System.out.println("Ingrese el año para buscar autores vivos:");
        try {
            var año = teclado.nextInt();
            teclado.nextLine();
            
            List<Autor> autoresVivos = autorRepository.findAutoresVivosEnAño(año);
            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + año);
            } else {
                System.out.println("\n********** AUTORES VIVOS EN " + año + " **********");
                autoresVivos.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Año inválido");
            teclado.nextLine();
        }
    }

    private void listarLibrosPorIdioma() {
        var menuIdiomas = """
                Ingrese el idioma para buscar los libros:
                es - español
                en - inglés
                fr - francés
                pt - portugués
                """;
        System.out.println(menuIdiomas);
        var idioma = teclado.nextLine();
        
        List<Libro> librosPorIdioma = libroRepository.findByIdioma(idioma);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma: " + idioma);
        } else {
            System.out.println("\n********** LIBROS EN " + idioma.toUpperCase() + " **********");
            librosPorIdioma.forEach(System.out::println);
        }
    }

    private void estadisticasPorIdioma() {
        System.out.println("\n********** ESTADÍSTICAS POR IDIOMA **********");
        
        String[] idiomas = {"es", "en", "fr", "pt"};
        String[] nombresIdiomas = {"Español", "Inglés", "Francés", "Portugués"};
        
        for (int i = 0; i < idiomas.length; i++) {
            Long cantidad = libroRepository.contarLibrosPorIdioma(idiomas[i]);
            System.out.println(nombresIdiomas[i] + ": " + cantidad + " libros");
        }
        
        Long totalLibros = libroRepository.count();
        System.out.println("\nTotal de libros registrados: " + totalLibros);
    }

    private void top10LibrosMasDescargados() {
        List<Libro> topLibros = libroRepository.findTop10ByOrderByNumeroDeDescargasDesc();
        if (topLibros.isEmpty()) {
            System.out.println("No hay libros registrados");
        } else {
            System.out.println("\n********** TOP 10 LIBROS MÁS DESCARGADOS **********");
            for (int i = 0; i < Math.min(10, topLibros.size()); i++) {
                Libro libro = topLibros.get(i);
                System.out.println((i + 1) + ". " + libro.getTitulo() + 
                                   " - " + String.format("%.0f", libro.getNumeroDeDescargas()) + " descargas");
            }
        }
    }
}
