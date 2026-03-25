package org.example.exception;
/**
 * Excepción personalizada para indicar que un campo requerido está vacío
 * en el contexto de la gestión de tareas.
 * Se utiliza RuntimeException para indicar que es una excepción no verificada ya que es validaciom.
 */
public class EmptyFieldException extends RuntimeException  {
    public EmptyFieldException(String message) {
        super(message);
    }
}
