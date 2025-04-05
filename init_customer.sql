-- Conectarse a la base de datos correcta (esto es opcional si ya estás dentro de mss_customer)
-- \c mss_customer;

-- Crear el esquema si no existe
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_namespace WHERE nspname = 'customer'
    ) THEN
        EXECUTE 'CREATE SCHEMA customer AUTHORIZATION customer';
    END IF;
END $$;

-- Crear tabla persona
CREATE TABLE IF NOT EXISTS customer.persona (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    genero VARCHAR(20),
    edad INTEGER,
    identificacion VARCHAR(50),
    direccion VARCHAR(150),
    telefono VARCHAR(20)
);

-- Crear tabla cliente
CREATE TABLE IF NOT EXISTS customer.cliente (
    id INTEGER PRIMARY KEY, -- Mismo ID que persona (relación 1 a 1)
    cliente_id VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cliente_persona FOREIGN KEY (id) REFERENCES customer.persona(id)
);

-- Crear función para actualizar automáticamente fecha_actualizacion
CREATE OR REPLACE FUNCTION customer.actualizar_fecha_actualizacion()
RETURNS TRIGGER AS $$
BEGIN
   NEW.fecha_actualizacion = CURRENT_TIMESTAMP;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear trigger para actualizar automáticamente la fecha
DROP TRIGGER IF EXISTS trigger_actualizacion_cliente ON customer.cliente;

CREATE TRIGGER trigger_actualizacion_cliente
BEFORE UPDATE ON customer.cliente
FOR EACH ROW
EXECUTE FUNCTION customer.actualizar_fecha_actualizacion();
