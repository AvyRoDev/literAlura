# 📚 LiterAlura - Catálogo de Libros

> Challenge de Alura - Aplicación Java Spring Boot para gestionar biblioteca personal consumiendo API Gutendx

## 📋 Descripción

Aplicación de consola desarrollada en Java con Spring Boot que permite crear y gestionar un catálogo personal de libros. Utiliza la API de **Project Gutenberg** para buscar información de libros y almacena los datos en una base de datos H2 con persistencia.

## ✨ Funcionalidades

- 📖 **Buscar libro por título** - Conecta con API Gutendx
- 📋 **Listar libros registrados** - Muestra biblioteca personal
- 👨‍💼 **Listar autores registrados** - Catálogo de autores
- 🗓️ **Buscar autores vivos en determinado año** - Filtro temporal
- 🌍 **Listar libros por idioma** - Organización por idiomas
- 📊 **Estadísticas por idioma** - Análisis de datos
- 🏆 **Top 10 libros más descargados** - Rankings populares

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.3**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database** (con persistencia en archivo)
- **Maven**
- **Jackson** (para JSON)
- **API REST Client**

## 📁 Estructura del Proyecto

literAlura/ ├── src/main/java/com/alura/challenge/literalura/ │ ├── model/ # Entidades JPA (Libro, Autor) │ ├── repository/ # Interfaces JPA Repository │ ├── service/ # Lógica de negocio y consumo API │ ├── principal/ # Menú principal y lógica UI │ └── LiteraluraApplication.java ├── src/main/resources/ │ └── application.properties ├── pom.xml └── README.md


## 🚀 Instalación y Ejecución

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+

### Pasos para ejecutar

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/AvyRoDev/literAlura.git
   cd literAlura
Ejecutar la aplicación

mvn spring-boot:run
Usar el menú interactivo

Sigue las opciones del menú en consola
Los datos se guardan automáticamente en literalura-db.mv.db
🎯 Uso de la Aplicación
Menú Principal
********** BIENVENIDO A LITERALURA **********
1 - Buscar libro por título
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en determinado año
5 - Listar libros por idioma
6 - Estadísticas por idioma
7 - Top 10 libros más descargados
0 - Salir
Ejemplo de Búsqueda
Busca: "Don Quixote"
La app conecta con Gutendx API
Muestra resultados y permite guardar
Los datos persisten entre sesiones
🌐 API Externa
Gutendx API: https://gutendx.com/

Biblioteca digital del Proyecto Gutenberg
Más de 70,000 libros gratuitos
Información detallada de autores y metadatos
💾 Base de Datos
H2 Database en modo archivo
Persistencia automática entre ejecuciones
Tablas: libros y autores
Relación: Un autor puede tener múltiples libros
🏗️ Patrones y Arquitectura
Repository Pattern - Para acceso a datos
Service Layer - Lógica de negocio
DTO Pattern - Para mapeo de API
JPA/Hibernate - ORM para persistencia
📈 Challenge Completado
✅ Consumo de API externa
✅ Persistencia de datos
✅ Búsquedas y filtros
✅ Estadísticas y reportes
✅ Interfaz de usuario interactiva
✅ Gestión de errores

👨‍💻 Desarrollador
Asiul Rosillo - GitHub

Proyecto desarrollado como parte del Challenge LiterAlura de Alura
