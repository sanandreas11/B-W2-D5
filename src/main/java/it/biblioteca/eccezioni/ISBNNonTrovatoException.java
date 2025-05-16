package it.biblioteca.eccezioni;

public class ISBNNonTrovatoException extends RuntimeException {
    public ISBNNonTrovatoException(String isbn) {
        super("Elemento con ISBN " + isbn + " non trovato.");
    }
}
