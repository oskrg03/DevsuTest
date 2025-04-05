# Microservicios de Clientes y Cuentas

Este repositorio contiene dos microservicios en Java usando Spring Boot para manejar la creación de clientes, cuentas y movimientos de cuenta. Los microservicios están diseñados para ser desplegados utilizando Docker y Docker Compose.

## Descripción

- **Microservicio de Clientes**: Maneja la creación y gestión de clientes.
- **Microservicio de Cuentas**: Maneja la creación de cuentas asociadas a clientes y los movimientos asociados.

## Requisitos

1. **Docker** y **Docker Compose** para ejecutar los servicios en contenedores.
2. **Java 11** o superior (para compilar y ejecutar los microservicios si no usas Docker).
3. **Postman** (opcional, para hacer pruebas de integración).

## Configuración de Docker Compose

El archivo `docker-compose.yml` define los siguientes servicios:

- **postgres_customer**: Contenedor de base de datos PostgreSQL para los clientes.
- **postgres_account**: Contenedor de base de datos PostgreSQL para las cuentas.
- **app_customer**: Microservicio que maneja la creación y gestión de clientes.
- **account_service**: Microservicio que maneja las cuentas y movimientos asociados.

### Estructura del `docker-compose.yml`

```yaml
version: '3.8'

services:
  postgres_customer:
    image: postgres:15
    container_name: customer-db
    environment:
      POSTGRES_DB: mss_customer
      POSTGRES_USER: customer
      POSTGRES_PASSWORD: customer123.
    ports:
      - "5432:5432"
    volumes:
      - pgdata_customer:/var/lib/postgresql/data
      - ./init_customer.sql:/docker-entrypoint-initdb.d/init.sql:ro

  postgres_account:
    image: postgres:15
    container_name: account-db
    environment:
      POSTGRES_DB: mss_account
      POSTGRES_USER: account
      POSTGRES_PASSWORD: account123.
    ports:
      - "5433:5432"
    volumes:
      - pgdata_account:/var/lib/postgresql/data
      - ./init_account.sql:/docker-entrypoint-initdb.d/init.sql:ro

  app_customer:
    build: ./CustomerREST
    container_name: microservicio-clientes
    depends_on:
      - postgres_customer
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres_customer:5432/mss_customer
      SPRING_DATASOURCE_USERNAME: customer
      SPRING_DATASOURCE_PASSWORD: customer123.
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    ports:
      - "8082:8082"
    networks:
      - backend

  account_service:  
    build: ./AccountREST
    container_name: microservicio-account
    depends_on:
      - postgres_account
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres_account:5432/mss_account
      SPRING_DATASOURCE_USERNAME: account
      SPRING_DATASOURCE_PASSWORD: account123.
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    ports:
      - "8083:8083"
    networks:
      - backend
      
networks:
  default:
    driver: bridge

volumes:
  pgdata_customer:
  pgdata_account:
```

## Variables de Entorno

A continuación se detallan las variables de entorno que puedes configurar para los servicios.

### Microservicio de Clientes

- **SPRING_DATASOURCE_URL**: La URL de la base de datos PostgreSQL para el microservicio de clientes.
  - Ejemplo: `jdbc:postgresql://postgres_customer:5432/mss_customer`
  
- **SPRING_DATASOURCE_USERNAME**: El nombre de usuario para acceder a la base de datos de clientes.
  - Ejemplo: `customer`

- **SPRING_DATASOURCE_PASSWORD**: La contraseña para acceder a la base de datos de clientes.
  - Ejemplo: `customer123.`

- **SPRING_JPA_HIBERNATE_DDL_AUTO**: Configuración de Hibernate para la base de datos.
  - Ejemplo: `update`

### Microservicio de Cuentas

- **SPRING_DATASOURCE_URL**: La URL de la base de datos PostgreSQL para el microservicio de cuentas.
  - Ejemplo: `jdbc:postgresql://postgres_account:5432/mss_account`
  
- **SPRING_DATASOURCE_USERNAME**: El nombre de usuario para acceder a la base de datos de cuentas.
  - Ejemplo: `account`

- **SPRING_DATASOURCE_PASSWORD**: La contraseña para acceder a la base de datos de cuentas.
  - Ejemplo: `account123.`

- **SPRING_JPA_HIBERNATE_DDL_AUTO**: Configuración de Hibernate para la base de datos.
  - Ejemplo: `update`

### Variables Comunes

- **CUSTOMER_SERVICE_URL**: La URL del microservicio de clientes desde el microservicio de cuentas (usada para interactuar entre los microservicios).
  - Ejemplo: `http://app_customer:8082/clientes`

- **ACCOUNT_SERVICE_URL**: La URL del microservicio de cuentas desde el microservicio de clientes.
  - Ejemplo: `http://account_service:8083/cuentas`


# Guía para Clonar el Repositorio y Levantar los Contenedores Docker

## 1. Clonar el Repositorio

Sigue estos pasos para clonar el repositorio:

1. Abre una terminal en tu computadora.
2. Navega al directorio donde deseas guardar el proyecto.
3. Clona el repositorio usando el siguiente comando (reemplaza `git@github.com:oskrg03/DevsuTest.git` con la URL del repositorio):

   ```bash
   git clone git@github.com:oskrg03/DevsuTest.git
   ```

4. Una vez clonado el repositorio, navega al directorio del proyecto con el siguiente comando:

   ```bash
   cd DevsuTest
   ```

## 2. Construir las Imágenes de Docker

Para construir las imágenes de Docker, sigue estos pasos:

1. Abre una terminal en el directorio donde tienes el `docker-compose.yml`.
2. Ejecuta el siguiente comando para construir las imágenes de Docker:

   ```bash
   docker-compose build
   ```

   Este comando descargará las imágenes necesarias y construirá los servicios definidos en el archivo `docker-compose.yml`.

## 3. Subir los Contenedores de Docker

Una vez que las imágenes hayan sido construidas, puedes iniciar los contenedores con el siguiente comando:

```bash
docker-compose up
```

Esto iniciará todos los servicios definidos en el `docker-compose.yml`.

## 4. Verificar que los Contenedores Están Corriendo

Para verificar que los contenedores están corriendo correctamente, usa el siguiente comando:

```bash
docker ps
```

Esto te mostrará todos los contenedores en ejecución. Asegúrate de que los contenedores `microservicio-clientes` y `microservicio-account` estén en la lista.

## 5. Verificar la Base de Datos

Si necesitas verificar que las bases de datos están correctamente configuradas, puedes conectarte a los contenedores de las bases de datos y comprobar que las tablas estén creadas. Usa los siguientes comandos:

Para la base de datos de clientes:

```bash
docker exec -it customer-db psql -U customer -d mss_customer
```

Para la base de datos de cuentas:

```bash
docker exec -it account-db psql -U account -d mss_account
```

  ## Enlaces de Postman

A continuación se encuentran los enlaces de Postman para las colecciones que contienen las pruebas de integración de los microservicios:

- **Colección 1: Coleccion de endpoints de los microservicios solicitados**
  - [Colección de postman](https://postman.co/workspace/My-Workspace~6d695315-a305-4bd4-8acd-928d515aa86e/collection/5664088-7f5d7c42-84ae-47e2-b23c-af185c17f000?action=share&creator=5664088&active-environment=5664088-6b7a8fe1-60d1-4800-a156-f5ef809a0d7f)

- **Colección 2: Crear Cliente, Crear Cuenta, y Realizar Movimientos, Obtener reporte de Cliente**
  - [Prueba de integración](https://postman.co/workspace/My-Workspace~6d695315-a305-4bd4-8acd-928d515aa86e/collection/5664088-90bcfbd4-129b-4573-8b67-8ab26a33bf21?action=share&creator=5664088&active-environment=5664088-6b7a8fe1-60d1-4800-a156-f5ef809a0d7f)

Se dejan en los archivos del proyecto ambas colecciones.

## Instrucciones para la Prueba de Integración

Las siguientes acciones forman parte de la prueba de integración completa:

1. **Crear Cliente**
   - Realiza una petición `POST` para crear un nuevo cliente con datos como nombre, género, edad, identificación, dirección, teléfono, estado y contraseña.
   
2. **Crear Cuenta**
   - Después de crear un cliente, se hace una petición `POST` para crear una cuenta asociada con el cliente utilizando el `clienteId`.
   
3. **Realizar Movimientos**
   - Luego, se realizan dos movimientos en la cuenta del cliente (por ejemplo, depósito y retiro), lo que implica la ejecución de solicitudes `POST` para registrar estos movimientos.
   
4. **Obtener Reporte del Cliente**
   - Finalmente, se obtiene el reporte de la cuenta del cliente mediante una petición `GET`, que devuelve un resumen de las transacciones realizadas.

