package org.example.app;
import org.example.exception.EmptyFieldException;
import org.example.exception.InvalidOperationException;
import org.example.exception.TaskNotFoundException;
import org.example.model.TaskModel;
import org.example.service.TaskService;
import java.util.List;
import java.util.Scanner;

/**
 *  Clase principal que sirve como punto de entrada para la aplicación de gestión de tareas.
 */
public class TaskManager {
    public static void main(String[] args) {
        // Se utiliza Scanner para leer la entrada del usuario desde la consola.
        Scanner scanner = new Scanner(System.in);
        // Se crea una instancia de TaskService para manejar la lógica de negocio relacionada con las tareas.
        TaskService taskService = new TaskService();
        // Se inicia un bucle que se ejecuta hasta que el usuario decida salir de la aplicación.
        boolean exit = false;
        try {
            while (!exit) {
                displayMenu();
                System.out.println("--- Seleccione una opción ---");
                int option = Integer.parseInt(scanner.nextLine());
                // Se utiliza un switch para manejar las diferentes opciones seleccionadas por el usuario,
                // llamando a los métodos correspondientes en TaskService.
                switch (option) {
                    case 1 -> createTask(scanner, taskService);
                    case 2 -> listTasks(taskService);
                    case 3 -> completeTask(scanner, taskService);
                    case 4 -> deleteTask(scanner, taskService);
                    case 5 -> exit = true;
                    default -> System.out.println("Opción no válida. Por favor, intente de nuevo.");
                }
            }
            // Se manejan diferentes tipos de excepciones que pueden ocurrir durante la ejecución del programa,
            // proporcionando mensajes de error claros para el usuario.
        }catch (NumberFormatException e) {
            System.out.println("Entrada no válida. Por favor, ingrese un número.");
        } catch (EmptyFieldException | InvalidOperationException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (TaskNotFoundException e) {
            System.out.println("Tarea no encontrada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        } finally {
            scanner.close();
            System.out.println("Programa finalizado.");
        }


    }
    /**
     * Método estático que muestra el menú principal de la aplicación al usuario.
     * Proporciona opciones para crear, listar, completar, eliminar tareas y salir de la aplicación.
     */
    private static void displayMenu() {
        System.out.println("--- Bienvenido al Gestor de Tareas ---");
        System.out.println("1. Crear tarea");
        System.out.println("2. Listar tareas");
        System.out.println("3. Completar tarea");
        System.out.println("4. Eliminar tarea");
        System.out.println("5. Salir");
    }

    /**
     * Método estático que maneja la creación de una nueva tarea. Solicita al usuario que ingrese el título y la
     * descripción de la tarea,
     * @param scanner Objeto Scanner utilizado para leer la entrada del usuario desde la consola.
     * @param taskService Instancia de TaskService que se utiliza para crear la nueva tarea en el sistema de gestión.
     */
    public static void createTask(Scanner scanner, TaskService taskService) {
        System.out.print("Ingrese el título de la tarea: ");
        String title = scanner.nextLine();
        System.out.print("Ingrese la descripción de la tarea ");
        String description = scanner.nextLine();
        taskService.createTask(title, description);
        System.out.println("Tarea creada exitosamente.");
    }
    /**
     * Método estático que maneja la visualización de todas las tareas actualmente almacenadas en el sistema.
     * Llama al método listTasks() de TaskService para obtener la lista de tareas y luego las muestra en la consola.
     * Si no hay tareas para mostrar, se informa al usuario.
     * @param taskService Instancia de TaskService que se utiliza para obtener la lista de tareas del sistema de gestión.
     */
    public static void listTasks(TaskService taskService) {
        List<TaskModel> tasks = taskService.listTasks();
        if(tasks.isEmpty()) {
            System.out.println("No hay tareas para mostrar.");
        } else {
            System.out.println("--- Lista de Tareas ---");
            for(TaskModel task : tasks) {
                System.out.println(task);
            }
        }
    }
    /**
     * Método estático que maneja la acción de marcar una tarea como completada. Solicita al usuario que ingrese el ID
     * de la tarea que desea completar, luego llama al método completeTask() de TaskService para marcar esa tarea como
     * completada. Si la tarea no se encuentra o ya está completada, se lanzan excepciones que son manejadas en el
     * bloque try-catch del método main.
     * @param scanner Objeto Scanner utilizado para leer la entrada del usuario desde la consola.
     * @param taskService Instancia de TaskService que se utiliza para marcar la tarea como completada en el sistema de gestión.
     * @throws TaskNotFoundException si no se encuentra una tarea con el ID proporcionado en la lista de tareas.
     */
    public static void completeTask(Scanner scanner, TaskService taskService) throws TaskNotFoundException {
        System.out.print("Ingrese el ID de la tarea a completar: ");
        int id = Integer.parseInt(scanner.nextLine());
        taskService.completeTask(id);
        System.out.println("Tarea marcada como completada.");
    }
    /**
     * Método estático que maneja la acción de eliminar una tarea. Solicita al usuario que ingrese el ID de la tarea
     * que desea eliminar, luego llama al método deleteTask() de TaskService para eliminar esa tarea del sistema de
     * gestión. Si la tarea no se encuentra, se lanza una excepción que es manejada en el bloque try-catch del método main.
     * @param scanner Objeto Scanner utilizado para leer la entrada del usuario desde la consola.
     * @param taskService Instancia de TaskService que se utiliza para eliminar la tarea en el sistema de gestión.
     * @throws TaskNotFoundException si no se encuentra una tarea con el ID proporcionado en la lista de tareas.
     */
    public static void deleteTask(Scanner scanner, TaskService taskService) throws TaskNotFoundException {
        System.out.print("Ingrese el ID de la tarea a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        taskService.deleteTask(id);
        System.out.println("Tarea eliminada exitosamente.");
    }


}