package it.biblioteca;

import it.biblioteca.model.*;
import it.biblioteca.eccezioni.ISBNNonTrovatoException;

import java.util.*;
import java.util.stream.Collectors;

public class Archivio {
    private Map<String, ElementoCatalogo> catalogo = new HashMap<>();

    // Aggiunta
    public void aggiungiElemento(ElementoCatalogo elemento) {
        if (catalogo.containsKey(elemento.getIsbn())) {
            System.out.println("Elemento con ISBN già presente!");
            return;
        }
        catalogo.put(elemento.getIsbn(), elemento);
    }

    // Ricerca per ISBN
    public ElementoCatalogo cercaPerISBN(String isbn) {
        ElementoCatalogo elemento = catalogo.get(isbn);
        if (elemento == null) {
            throw new ISBNNonTrovatoException(isbn);
        }
        return elemento;
    }

    // Rimozione
    public void rimuoviPerISBN(String isbn) {
        if (catalogo.remove(isbn) == null) {
            throw new ISBNNonTrovatoException(isbn);
        }
    }

    // Ricerca per anno
    public List<ElementoCatalogo> cercaPerAnno(int anno) {
        return catalogo.values().stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }

    // Ricerca per autore (solo Libri)
    public List<Libro> cercaPerAutore(String autore) {
        return catalogo.values().stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(libro -> libro.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());
    }

    // Aggiornamento
    public void aggiornaElemento(String isbn, ElementoCatalogo nuovoElemento) {
        if (!catalogo.containsKey(isbn)) {
            throw new ISBNNonTrovatoException(isbn);
        }
        catalogo.put(isbn, nuovoElemento);
    }

    // Statistiche
    public void stampaStatistiche() {
        long numLibri = catalogo.values().stream().filter(e -> e instanceof Libro).count();
        long numRiviste = catalogo.values().stream().filter(e -> e instanceof Rivista).count();

        Optional<ElementoCatalogo> maxPagine = catalogo.values().stream()
                .max(Comparator.comparingInt(ElementoCatalogo::getNumeroPagine));

        double mediaPagine = catalogo.values().stream()
                .mapToInt(ElementoCatalogo::getNumeroPagine)
                .average()
                .orElse(0);

        System.out.println("Libri: " + numLibri);
        System.out.println("Riviste: " + numRiviste);
        maxPagine.ifPresent(e -> System.out.println("Elemento con più pagine: " + e));
        System.out.printf("Media pagine: %.2f\n", mediaPagine);
    }

    // Metodo per stampare tutto il catalogo
    public void stampaTuttiGliElementi() {
        catalogo.values().forEach(System.out::println);
    }
}
