package org.example.service;
import org.example.exception.EmptyFieldException;
import org.example.exception.InvalidOperationException;
import org.example.exception.TaskNotFoundException;
import org.example.model.TaskModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;


public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    //Se crea una lista para almacenar las tareas y un contador para asignar IDs únicos a cada tarea.
    private List<TaskModel> tasks = new ArrayList<>();
    private int nextId = 1;
    /**
     * Crea una nueva tarea con el título y descripción proporcionados.
     * Si el título está vacío o es nulo, se lanza una excepción EmptyFieldException
     * @param title El título de la tarea a crear. No puede ser nulo ni vacío.
     * @param description La descripción de la tarea a crear. Puede ser nula o vacía, pero no es obligatoria.
     */
    public void createTask( String title, String description) {
        logger.debug("Intentando crear tarea con título: {}", title);
        if(title == null || title.trim().isEmpty()) {
            logger.warn("No se pudo crear la tarea porque el título está vacío.");
            throw new EmptyFieldException("El título de la tarea no puede estar vacío.");
        }
        // Se crea una nueva instancia de TaskModel con título, descripción y un ID único, se agrega a la lista de tareas.
        TaskModel task = new TaskModel(title, description, nextId++);
        tasks.add(task);
        logger.info("Tarea creada correctamente con ID: {}", task.getId());
    }
    /**
     * Devuelve una lista de todas las tareas actualmente almacenadas en el sistema.
     * @return una lista que representa la copia de tareas
     */
    public List<TaskModel> listTasks() {
        logger.debug("Lista de tareas. Cantidad actual: {}", tasks.size());
        return new ArrayList<>(tasks);
    }
    /**
     * Busca una tarea por ID, luego marca esa tarea como completada. En caso de no encontrar la tarea, se lanza
     * una excepción TaskNotFoundException.
     * @param id El identificador único de la tarea que se desea marcar como completada.
     * @throws TaskNotFoundException si no se encuentra una tarea con el ID proporcionado en la lista de tareas.
     */
    public void completeTask(int id) throws TaskNotFoundException {
        logger.debug("Intentando completar tarea con ID: {}", id);
        TaskModel task = findById(id);
        if(task.isCompleted()) {
            logger.warn("Se intentó completar una tarea ya completada. ID: {}", id);
            throw new InvalidOperationException("La tarea con ID: " + id + " ya está completada.");
        }
        task.setCompleted(true);
        logger.info("Tarea marcada como completada. ID: {}", id);
    }
    /**
     * Busca una tarea por ID, luego elimina esa tarea de la lista de tareas. En caso de no encontrar la tarea, se lanza
     * una excepción TaskNotFoundException.
     * @param id El identificador único de la tarea que se desea eliminar.
     * @throws TaskNotFoundException si no se encuentra una tarea con el ID proporcionado en la lista de tareas.
     */
    public void deleteTask(int id) throws TaskNotFoundException {
        logger.debug("Intentando eliminar tarea con ID: {}", id);
        TaskModel task = findById(id);
        tasks.remove(task);
        logger.info("Tarea eliminada correctamente. ID: {}", id);
    }
    /**
     * Método interno (clave) que busca una tarea por su ID en la lista de tareas. Si se encuentra la tarea,
     * se devuelve el objeto TaskModel correspondiente. Si no se encuentra la tarea, se lanza una
     * excepción TaskNotFoundException con un mensaje que indica que no existe la tarea con el ID proporcionado.
     * @param id El identificador único de la tarea que se desea buscar.
     * @return El objeto TaskModel que corresponde a la tarea encontrada con el ID proporcionado.
     * @throws TaskNotFoundException si no se encuentra una tarea con el ID proporcionado en la lista de tareas.
     */
    //Stream API para buscar la tarea por ID, si no se encuentra se lanza una excepción personalizada.
    public TaskModel findById(int id) throws TaskNotFoundException {
        logger.debug("Buscando tarea con ID: {}", id);
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("No existe la tarea con ID: {}", id);
                        return new TaskNotFoundException("No existe la tarea con ID: " + id);
                });
    }

}