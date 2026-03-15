package com.deliveryslots.exception;

public class SlotExhaustedException extends RuntimeException {
    // Excepción personalizada se lanza cuando se intenta reservar una ventana-zona que ya no tiene capacidad disponible.
    // El controller devolverá un 409 Conflict con el mensaje de error.
    public SlotExhaustedException(String message) {
        super(message);
    }
}
