package org.example.model;

/**
 * Clase que representa un modelo de tarea en el sistema de gestión de tareas.
 * Contiene información sobre el título, descripción, estado de completitud y un identificador único para cada tarea.
 */
public class TaskModel {
    private String title, description;
    private boolean completed;
    private int id;

    public TaskModel(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.completed = false;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getId() {
        return id;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Título: " + title +
                " | Descripción: " + description +
                " | Estado: " + (completed ? "Completada" : "Pendiente");
    }
}
