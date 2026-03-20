package com.airline.impactanalysis.domain.exception;

public class InvalidDisruptionEventException extends RuntimeException {
    public InvalidDisruptionEventException(String message) {
        super("Evento de disrupción inválido: " + message);
    }
}
