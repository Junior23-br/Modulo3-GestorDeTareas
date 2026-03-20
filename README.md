# Gestor de Tareas en Java

Aplicación de consola desarrollada en Java para la gestión de tareas.

## 📌 Descripción

Permite administrar tareas pendientes mediante una interfaz de línea de comandos. El sistema soporta operaciones básicas como creación, listado, actualización y eliminación de tareas (CRUD) garantizando un manejo robusto de errores y trazabilidad de eventos.

Este proyecto fue desarrollado como parte del programa **Java Senior AI** de la academia **Dev Senior Code**.

## 🚀 Funcionalidades

* ✅ Crear tareas con ID, descripción y estado
* 📋 Listar todas las tareas registradas
* ✔️ Marcar tareas como completadas
* ❌ Eliminar tareas por ID
* 🔚 Salida controlada del sistema

## 🛠️ Tecnologías y herramientas

* **Manejo de excepciones personalizado**
* **Java**
* **SLF4J + Log4j2** → Logging eficiente
* **JUnit** → Pruebas unitarias

## ⚙️ Buenas prácticas implementadas

* Separación entre excepciones verificadas y no verificadas
* Creación de excepciones personalizadas
* Registro de eventos con diferentes niveles (INFO, DEBUG, WARN, ERROR)
* Código modular y legible
* Pruebas para funcionalidades clave

## 📂 Estructura del proyecto

```
src/
 ├── model/
 ├── service/
 ├── exception/
 └──main/
```

## 🧪 Pruebas

El proyecto incluye pruebas unitarias que validan:

* Creación de tareas
* Eliminación de tareas
* Manejo adecuado de excepciones

## 👨‍💻 Autor

Desarrollado como proyecto académico dentro del programa **Java Senior AI** – Dev Senior Code por el estudiante ***David Batero***
