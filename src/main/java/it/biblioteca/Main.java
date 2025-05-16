package it.biblioteca;

import it.biblioteca.model.*;
import it.biblioteca.eccezioni.ISBNNonTrovatoException;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Archivio archivio = new Archivio();
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        while (continua) {
            System.out.println("\n--- MENU CATALOGO BIBLIOTECARIO ---");
            System.out.println("1. Aggiungi libro");
            System.out.println("2. Aggiungi rivista");
            System.out.println("3. Cerca per ISBN");
            System.out.println("4. Rimuovi per ISBN");
            System.out.println("5. Cerca per anno");
            System.out.println("6. Cerca per autore");
            System.out.println("7. Aggiorna elemento");
            System.out.println("8. Mostra statistiche");
            System.out.println("9. Mostra tutti gli elementi");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");
            int scelta = scanner.nextInt();
            scanner.nextLine(); // consuma newline

            try {
                switch (scelta) {
                    case 1 -> {
                        System.out.print("ISBN: ");
                        String isbn = scanner.nextLine();
                        System.out.print("Titolo: ");
                        String titolo = scanner.nextLine();
                        System.out.print("Anno: ");
                        int anno = scanner.nextInt();
                        System.out.print("Pagine: ");
                        int pagine = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Autore: ");
                        String autore = scanner.nextLine();
                        System.out.print("Genere: ");
                        String genere = scanner.nextLine();
                        archivio.aggiungiElemento(new Libro(isbn, titolo, anno, pagine, autore, genere));
                    }
                    case 2 -> {
                        System.out.print("ISBN: ");
                        String isbn = scanner.nextLine();
                        System.out.print("Titolo: ");
                        String titolo = scanner.nextLine();
                        System.out.print("Anno: ");
                        int anno = scanner.nextInt();
                        System.out.print("Pagine: ");
                        int pagine = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Periodicità (SETTIMANALE/MENSILE/SEMESTRALE): ");
                        Periodicita p = Periodicita.valueOf(scanner.nextLine().toUpperCase());
                        archivio.aggiungiElemento(new Rivista(isbn, titolo, anno, pagine, p));
                    }
                    case 3 -> {
                        System.out.print("ISBN da cercare: ");
                        String isbn = scanner.nextLine();
                        System.out.println(archivio.cercaPerISBN(isbn));
                    }
                    case 4 -> {
                        System.out.print("ISBN da rimuovere: ");
                        String isbn = scanner.nextLine();
                        archivio.rimuoviPerISBN(isbn);
                        System.out.println("Elemento rimosso.");
                    }
                    case 5 -> {
                        System.out.print("Anno: ");
                        int anno = scanner.nextInt();
                        scanner.nextLine();
                        List<ElementoCatalogo> risultati = archivio.cercaPerAnno(anno);
                        risultati.forEach(System.out::println);
                    }
                    case 6 -> {
                        System.out.print("Autore: ");
                        String autore = scanner.nextLine();
                        List<Libro> risultati = archivio.cercaPerAutore(autore);
                        risultati.forEach(System.out::println);
                    }
                    case 7 -> {
                        System.out.print("ISBN dell'elemento da aggiornare: ");
                        String isbn = scanner.nextLine();
                        System.out.println("Inserisci nuovo elemento con stesso ISBN.");
                        System.out.print("Titolo: ");
                        String titolo = scanner.nextLine();
                        System.out.print("Anno: ");
                        int anno = scanner.nextInt();
                        System.out.print("Pagine: ");
                        int pagine = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("È un libro (l) o una rivista (r)? ");
                        String tipo = scanner.nextLine().toLowerCase();

                        ElementoCatalogo nuovo;
                        if (tipo.equals("l")) {
                            System.out.print("Autore: ");
                            String autore = scanner.nextLine();
                            System.out.print("Genere: ");
                            String genere = scanner.nextLine();
                            nuovo = new Libro(isbn, titolo, anno, pagine, autore, genere);
                        } else {
                            System.out.print("Periodicità: ");
                            Periodicita p = Periodicita.valueOf(scanner.nextLine().toUpperCase());
                            nuovo = new Rivista(isbn, titolo, anno, pagine, p);
                        }
                        archivio.aggiornaElemento(isbn, nuovo);
                        System.out.println("Elemento aggiornato.");
                    }
                    case 8 -> archivio.stampaStatistiche();
                    case 9 -> archivio.stampaTuttiGliElementi();
                    case 0 -> continua = false;
                    default -> System.out.println("Scelta non valida.");
                }
            } catch (IllegalArgumentException | ISBNNonTrovatoException e) {
                System.out.println("Errore: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Errore imprevisto: " + e);
            }
        }

        System.out.println("Chiusura del programma.");
        scanner.close();
    }
}
