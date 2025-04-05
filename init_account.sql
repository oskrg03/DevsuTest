DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'postgres') THEN
        CREATE USER postgres WITH PASSWORD 'postgres';
        ALTER USER postgres WITH SUPERUSER;
    END IF;
END
$$;

-- Crear el esquema si no existe
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_namespace WHERE nspname = 'account'
    ) THEN
        EXECUTE 'CREATE SCHEMA account AUTHORIZATION account';
    END IF;
END $$;

CREATE TABLE cuenta (
    id SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL,
    saldo_actual DECIMAL(15,2) NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE movimiento (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    cuenta_id INTEGER NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(id)
);

CREATE OR REPLACE FUNCTION actualizar_fecha_actualizacion()
RETURNS TRIGGER AS $$
BEGIN
    NEW.fecha_actualizacion := CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger que ejecuta la función antes de cada UPDATE
CREATE TRIGGER trigger_actualizar_fecha_actualizacion
BEFORE UPDATE ON cuenta
FOR EACH ROW
EXECUTE FUNCTION actualizar_fecha_actualizacion();
