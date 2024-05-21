package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.Repository.LibroRepository;
import com.aluracursos.desafio.model.Datos;
import com.aluracursos.desafio.model.DatosLibros;
import com.aluracursos.desafio.model.Libro;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository repositorio;
    private List<Libro> librosJ;
    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }
    //var datos = conversor.obtenerDatos(json, Datos.class);
    //System.out.println(datos);

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n1 - Buscar Libro 
                    2 - Mostrar libros registrados
                    3 - Mostrar libros autores registrados
                    4 - Buscar por año
                    5 - Libros por Idioma
                    6 - 
                    7 - 
                    8 - 
                    9 - 
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarDatosLibros();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresporAno();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 6:

                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }




    private Optional<DatosLibros> buscarDatosLibros() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        //var json = consumoAPI.obtenerDatos(URL_BASE);
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        //var datos = conversor.obtenerDatos(json, DatosLibros.class);
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        DatosLibros datos = libroBuscado.get();
        Libro libro = new Libro(datos);
                if (libroBuscado.isPresent()) {
                            System.out.println("Libro Encontrado ");
                            System.out.println("Titulo: " + libroBuscado.get().titulo());
                            System.out.println("Autor: " + libroBuscado.get().autor().get(0).nombre());
                            for (int i = 0; i < libroBuscado.get().idiomas().size(); i++) {
                                System.out.println("Idioma: " + libroBuscado.get().idiomas().get(i));
                            }
                            System.out.println("Numero de Descargas: " + libroBuscado.get().numeroDeDescargas());
                            repositorio.save(libro);
                } else

                    System.out.println("Libro no encontrado");

return null;
    }

    private void mostrarLibrosRegistrados() {
        librosJ = repositorio.findAll();
        int i = librosJ.size();
        System.out.println("Libros Encontrados ");
        for (int j = 0; j < i; j++) {
            System.out.println("\n===================================================");
            System.out.println("Titulo: " + librosJ.get(j).getTitulo());
            System.out.println("Autor: " + librosJ.get(j).getAutor());
            System.out.println("Idioma: " + librosJ.get(j).getIdiomas());
        }
    }

    private void mostrarAutoresRegistrados() {
        librosJ = repositorio.findAll();
        int i = librosJ.size();
        System.out.println("Autores Registrados ");
        for (int j = 0; j < i; j++) {
            System.out.println("\n===================================================");
            System.out.println("Autor: " + librosJ.get(j).getAutor());
            System.out.println("Fecha de Nacimiento: " + librosJ.get(j).getFechaDeNacimiento());
            System.out.println("Fecha de Nacimiento: " + librosJ.get(j).getMuerte());
            System.out.println("Libros: " + librosJ.get(j).getTitulo());
        }
    }
    private void mostrarAutoresporFecha() {
        System.out.println("Ingrese fecha del Autor que desea buscar");
        var fechaLibro = teclado.nextLine();
        Optional<Libro> fechas = repositorio.findByFechaDeNacimiento(fechaLibro);
        if(fechas.isPresent()){
            System.out.println("La serie buscada es: " + fechas.get().getAutor());
        }
    }

    private void mostrarAutoresporAno() {
        System.out.println("Ingrese el año inicial de busqueda: ");
        var inicio = teclado.nextLine();
        System.out.println("Ingrese el año final de busqueda: ");
        var fin = teclado.nextLine();
        List<Libro> librosPorFechaDeNacimiento = repositorio.findByFechaDeNacimientoBetween(inicio, fin);
        System.out.println(librosPorFechaDeNacimiento);
            librosJ = new ArrayList<>();
            librosJ = librosPorFechaDeNacimiento;
            System.out.println(librosJ.size());
        for (int i = 0; i != librosJ.size(); i++) {
            System.out.println("================================\n");
            System.out.println("Autor: "+librosJ.get(i).getAutor());
            System.out.println("Titulo: "+librosJ.get(i).getTitulo());
            System.out.println("Fecha de Nacimiento: "+librosJ.get(i).getFechaDeNacimiento());
            System.out.println("Fecha de Muerte: "+librosJ.get(i).getMuerte());
        }

    }
    private void librosPorIdioma() {
        System.out.println("Elija el idioma de los libros a buscar: ");
        System.out.println("Ingles = EN");
        System.out.println("Español = ES");
        var idioma = teclado.nextLine().toLowerCase();
        var idiomaOK = "["+idioma+"]";
        System.out.println(idiomaOK);
        List<Libro> libroIdioma = repositorio.findByIdiomas(idiomaOK);
        librosJ = libroIdioma;

        if(librosJ.isEmpty())
        {
            System.out.println("Libros no encontrados :(");
        }
        else {
        System.out.println(librosJ.size());
        for (int i = 0; i != librosJ.size(); i++) {
            System.out.println("================================\n");
            System.out.println("Autor: " + librosJ.get(i).getAutor());
            System.out.println("Titulo: " + librosJ.get(i).getTitulo());
            System.out.println("Fecha de Nacimiento: " + librosJ.get(i).getFechaDeNacimiento());
            System.out.println("Fecha de Muerte: " + librosJ.get(i).getMuerte());
            //ystem.out.println(i);
        }

        }

    }

}






