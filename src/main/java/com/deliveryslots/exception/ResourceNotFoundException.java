package com.deliveryslots.exception;

public class ResourceNotFoundException extends RuntimeException {
    // Excepción personalizada se lanza cuando no se encuentra un recurso (ventana, zona, reserva, comuna).
    // El controller devolverará un 404 Not Found con el mensaje de error.
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
