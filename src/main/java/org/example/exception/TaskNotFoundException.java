package org.example.exception;

/**
 * Excepción personalizada para indicar que una tarea no se ha encontrado
 * en el sistema de gestión de tareas.
 * Se utiliza Exception para indicar que es una excepción verificada, lo que indica a manejarla explícitamente en el código.
 *
 */
public class TaskNotFoundException extends Exception{
    public TaskNotFoundException(String message) {
        super(message);
    }
}
