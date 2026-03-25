package org.example.service;

import org.example.exception.EmptyFieldException;
import org.example.exception.InvalidOperationException;
import org.example.exception.TaskNotFoundException;
import org.example.model.TaskModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    // =========================
    // PRUEBAS DE createTask()
    // =========================

    @Test
    void createTask_DeberiaCrearUnaTarea_CuandoDatosSonValidos() {
        taskService.createTask("Estudiar", "Repasar JUnit");

        List<TaskModel> tasks = taskService.listTasks();

        assertEquals(1, tasks.size(),
                "La lista debe contener una tarea después de crearla.");

        assertEquals("Estudiar", tasks.get(0).getTitle(),
                "El título de la tarea debe coincidir con el valor ingresado.");

        assertEquals("Repasar JUnit", tasks.get(0).getDescription(),
                "La descripción de la tarea debe coincidir con el valor ingresado.");

        assertEquals(1, tasks.get(0).getId(),
                "El ID de la primera tarea creada debe ser 1.");

        assertFalse(tasks.get(0).isCompleted(),
                "Una tarea recién creada no debe estar completada.");
    }

    @Test
    void createTask_DeberiaPermitirDescripcionVacia() {
        taskService.createTask("Leer", "");

        List<TaskModel> tasks = taskService.listTasks();

        assertEquals(1, tasks.size(),
                "La tarea debe crearse aunque la descripción esté vacía.");

        assertEquals("", tasks.get(0).getDescription(),
                "La descripción debe mantenerse vacía si fue ingresada vacía.");
    }

    @Test
    void createTask_DeberiaPermitirDescripcionNula() {
        taskService.createTask("Comprar", null);

        List<TaskModel> tasks = taskService.listTasks();

        assertEquals(1, tasks.size(),
                "La tarea debe crearse aunque la descripción sea nula.");

        assertNull(tasks.get(0).getDescription(),
                "La descripción debe ser nula si fue enviada como nula.");
    }

    @Test
    void createTask_DeberiaAsignarIdsIncrementales() {
        taskService.createTask("Tarea 1", "Descripción 1");
        taskService.createTask("Tarea 2", "Descripción 2");
        taskService.createTask("Tarea 3", "Descripción 3");

        List<TaskModel> tasks = taskService.listTasks();

        assertEquals(3, tasks.size(),
                "Deben existir tres tareas creadas en la lista.");

        assertEquals(1, tasks.get(0).getId(),
                "La primera tarea debe tener ID 1.");
        assertEquals(2, tasks.get(1).getId(),
                "La segunda tarea debe tener ID 2.");
        assertEquals(3, tasks.get(2).getId(),
                "La tercera tarea debe tener ID 3.");
    }

    @Test
    void createTask_DeberiaLanzarExcepcion_CuandoTituloEsNulo() {
        EmptyFieldException exception = assertThrows(
                EmptyFieldException.class,
                () -> taskService.createTask(null, "Descripción"),
                "Debe lanzarse EmptyFieldException cuando el título es nulo."
        );

        assertEquals("El título de la tarea no puede estar vacío.", exception.getMessage(),
                "El mensaje de la excepción debe indicar que el título está vacío.");

        assertTrue(taskService.listTasks().isEmpty(),
                "No debe crearse ninguna tarea si el título es nulo.");
    }

    @Test
    void createTask_DeberiaLanzarExcepcion_CuandoTituloEstaVacio() {
        EmptyFieldException exception = assertThrows(
                EmptyFieldException.class,
                () -> taskService.createTask("", "Descripción"),
                "Debe lanzarse EmptyFieldException cuando el título está vacío."
        );

        assertEquals("El título de la tarea no puede estar vacío.", exception.getMessage(),
                "El mensaje de la excepción debe indicar que el título está vacío.");

        assertEquals(0, taskService.listTasks().size(),
                "La lista debe permanecer vacía cuando no se puede crear la tarea.");
    }

    @Test
    void createTask_DeberiaLanzarExcepcion_CuandoTituloSoloTieneEspacios() {
        EmptyFieldException exception = assertThrows(
                EmptyFieldException.class,
                () -> taskService.createTask("   ", "Descripción"),
                "Debe lanzarse EmptyFieldException cuando el título solo contiene espacios."
        );

        assertEquals("El título de la tarea no puede estar vacío.", exception.getMessage(),
                "El mensaje debe coincidir con el definido en la excepción.");

        assertTrue(taskService.listTasks().isEmpty(),
                "No debe agregarse ninguna tarea cuando el título solo tiene espacios.");
    }

    // =========================
    // PRUEBAS DE listTasks()
    // =========================

    @Test
    void listTasks_DeberiaRetornarListaVacia_CuandoNoHayTareas() {
        List<TaskModel> tasks = taskService.listTasks();

        assertNotNull(tasks,
                "La lista retornada no debe ser nula.");
        assertTrue(tasks.isEmpty(),
                "La lista debe estar vacía cuando no se han creado tareas.");
    }

    @Test
    void listTasks_DeberiaRetornarUnaCopiaYNoLaListaOriginal() {
        taskService.createTask("Estudiar", "POO");

        List<TaskModel> copy = taskService.listTasks();
        copy.clear();

        assertEquals(1, taskService.listTasks().size(),
                "Modificar la lista retornada no debe afectar la lista interna del servicio.");
    }

    // =========================
    // PRUEBAS DE findById()
    // =========================

    @Test
    void findById_DeberiaRetornarLaTareaCorrecta_CuandoExiste() throws TaskNotFoundException {
        taskService.createTask("Hacer taller", "JUnit");
        taskService.createTask("Dormir", "8 horas");

        TaskModel task = taskService.findById(2);

        assertNotNull(task,
                "La tarea encontrada no debe ser nula.");
        assertEquals(2, task.getId(),
                "El ID de la tarea encontrada debe coincidir con el solicitado.");
        assertEquals("Dormir", task.getTitle(),
                "El título de la tarea encontrada debe coincidir.");
    }

    @Test
    void findById_DeberiaLanzarExcepcion_CuandoIdNoExiste() {
        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.findById(99),
                "Debe lanzarse TaskNotFoundException cuando el ID no existe."
        );

        assertEquals("No existe la tarea con ID: 99", exception.getMessage(),
                "El mensaje debe indicar el ID que no fue encontrado.");
    }

    @Test
    void findById_DeberiaLanzarExcepcion_CuandoIdEsCero() {
        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.findById(0),
                "Debe lanzarse TaskNotFoundException cuando el ID es cero."
        );

        assertEquals("No existe la tarea con ID: 0", exception.getMessage(),
                "El mensaje debe indicar correctamente el ID cero.");
    }

    @Test
    void findById_DeberiaLanzarExcepcion_CuandoIdEsNegativo() {
        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.findById(-1),
                "Debe lanzarse TaskNotFoundException cuando el ID es negativo."
        );

        assertEquals("No existe la tarea con ID: -1", exception.getMessage(),
                "El mensaje debe indicar correctamente el ID negativo.");
    }

    // =========================
    // PRUEBAS DE completeTask()
    // =========================

    @Test
    void completeTask_DeberiaMarcarLaTareaComoCompletada_CuandoExiste() throws TaskNotFoundException {
        taskService.createTask("Hacer ejercicio", "30 minutos");

        taskService.completeTask(1);

        TaskModel task = taskService.findById(1);

        assertTrue(task.isCompleted(),
                "La tarea debe quedar marcada como completada.");
    }

    @Test
    void completeTask_DeberiaLanzarExcepcion_CuandoLaTareaNoExiste() {
        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.completeTask(50),
                "Debe lanzarse TaskNotFoundException cuando la tarea no existe."
        );

        assertEquals("No existe la tarea con ID: 50", exception.getMessage(),
                "El mensaje debe indicar el ID que no fue encontrado.");
    }

    @Test
    void completeTask_DeberiaLanzarExcepcion_CuandoLaTareaYaEstaCompletada() throws TaskNotFoundException {
        taskService.createTask("Enviar correo", "A profesor");
        taskService.completeTask(1);

        InvalidOperationException exception = assertThrows(
                InvalidOperationException.class,
                () -> taskService.completeTask(1),
                "Debe lanzarse InvalidOperationException si la tarea ya estaba completada."
        );

        assertEquals("La tarea con ID: 1 ya está completada.", exception.getMessage(),
                "El mensaje debe indicar que la tarea ya estaba completada.");
    }

    @Test
    void completeTask_DeberiaLanzarExcepcion_CuandoIdEsCero() {
        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.completeTask(0),
                "Debe lanzarse TaskNotFoundException cuando el ID es cero."
        );

        assertEquals("No existe la tarea con ID: 0", exception.getMessage(),
                "El mensaje debe indicar que no existe tarea con ID cero.");
    }

    @Test
    void completeTask_DeberiaLanzarExcepcion_CuandoIdEsNegativo() {
        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.completeTask(-10),
                "Debe lanzarse TaskNotFoundException cuando el ID es negativo."
        );

        assertEquals("No existe la tarea con ID: -10", exception.getMessage(),
                "El mensaje debe indicar que no existe tarea con ID negativo.");
    }

    // =========================
    // PRUEBAS DE deleteTask()
    // =========================

    @Test
    void deleteTask_DeberiaEliminarLaTarea_CuandoExiste() throws TaskNotFoundException {
        taskService.createTask("Tarea A", "Descripción A");
        taskService.createTask("Tarea B", "Descripción B");

        taskService.deleteTask(1);

        List<TaskModel> tasks = taskService.listTasks();

        assertEquals(1, tasks.size(),
                "Después de eliminar una tarea, debe quedar una sola en la lista.");

        assertEquals(2, tasks.get(0).getId(),
                "La tarea restante debe ser la que tenía ID 2.");
    }

    @Test
    void deleteTask_DeberiaLanzarExcepcion_CuandoLaTareaNoExiste() {
        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.deleteTask(100),
                "Debe lanzarse TaskNotFoundException cuando se intenta eliminar una tarea inexistente."
        );

        assertEquals("No existe la tarea con ID: 100", exception.getMessage(),
                "El mensaje debe indicar el ID que no fue encontrado.");
    }

    @Test
    void deleteTask_DeberiaLanzarExcepcion_CuandoIdEsCero() {
        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.deleteTask(0),
                "Debe lanzarse TaskNotFoundException cuando se intenta eliminar con ID cero."
        );

        assertEquals("No existe la tarea con ID: 0", exception.getMessage(),
                "El mensaje debe indicar que no existe una tarea con ID cero.");
    }

    @Test
    void deleteTask_DeberiaLanzarExcepcion_CuandoIdEsNegativo() {
        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.deleteTask(-5),
                "Debe lanzarse TaskNotFoundException cuando se intenta eliminar con un ID negativo."
        );

        assertEquals("No existe la tarea con ID: -5", exception.getMessage(),
                "El mensaje debe indicar que no existe una tarea con ID negativo.");
    }
}