package org.example.exception;
/**
 * Excepción personalizada para indicar que una operación
 * no es válida en el contexto de la gestión de tareas.
 * Se utiliza RuntimeException para indicar que es una excepción no verificada.
 */
public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
