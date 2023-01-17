# Sistema VTV Java Spring boot API REST

Api Rest para el manejo inspecciones de Verificación Técnica Vehicular usando Spring boot, Spring Security, JPA, MySQL.

## Requerimientos
- JDK 17 o superior
- Maven
- Tomcat
- MySQL Workbench 8.0

## Setup

## Pasos para el Setup

**1. Clonar la aplicación**

```bash
git clone https://github.com/manolomanolero/Sistema-VTV.git
```

**2. Cambiar las variables de entorno**

+ Agregar en `env.properties` los datos de usuario y contraseña propios de tu gestor de base de datos

**3. Para ejecutar la app**

```bash
mvn spring-boot:run
```

La app va a correr localmente en <http://localhost:8080>.

## Explorar los endpoints

La documentacion de todos los endpoints se puede ver en:
- Se puede importar desde Postman el archivo "Sistema VTV.postman_collection.json"
